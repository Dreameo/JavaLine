package com.yfh.fruit.servlets;

import com.yfh.myssm.myspringmvc.ViewBaseServlet;
import com.yfh.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 中央控制器，核心控制器，接受所有的请求，根据不同的请求定位到不同到不同的Servlet（FruitServlet/UserServlet）中去(也可叫FruitController)的不同方法中（增删改查）
 */
@WebServlet("*.do") // 拦截所有以.do结尾的请求
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String, Object> beanMap = new HashMap<>();

    public DispatcherServlet() {

    }


    public void init() {
        System.out.println("init-config");
        try {
            // 构造方法中读取配置文件
            InputStream is = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            // 1. 创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 2. 创建DocumentBuilder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // 3. 创建Document对象
            Document document = documentBuilder.parse(is);

            // 4. 获取所有的bean节点
            NodeList beanList = document.getElementsByTagName("bean");

            for (int i = 0; i < beanList.getLength(); i++) {
                Node beanNode = beanList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    // 判断是否为元素节点
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String beanClass = beanElement.getAttribute("class");
                    System.out.println("beanClass = " + beanClass);
                    Class controllerClass = Class.forName(beanClass);
                    Object controllerBean = controllerClass.newInstance();



                    Method method = controllerBean.getClass().getDeclaredMethod("setServletContext",ServletContext.class);
                    method.setAccessible(true);
                    method.invoke(controllerBean, this.getServletContext());
                    beanMap.put(beanId, controllerBean);
                }

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 设置编码格式
        req.setCharacterEncoding("UTF-8");
        // /hello.do  (字符串截取)->  hello  --> HelloController
        System.out.println("req.getServletPath() = " + req.getServletPath()); // /hello.do
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndex);
        System.out.println(servletPath);


        Object controllerBean = beanMap.get(servletPath);

        String operation = req.getParameter("operation");
        System.out.println(operation);
        if (StringUtil.isEmpty(operation)) {
            operation = "index";
        }

//        try {
//            // 根据特定方法名称调用
//            Method method = controllerBean.getClass().getDeclaredMethod(operation, HttpServletRequest.class, HttpServletResponse.class);
//            if (method != null) {
//                method.setAccessible(true);
//                method.invoke(controllerBean, req, resp);
//            }
//            else
//                throw new RuntimeException("输入参数错误");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        // 通过反射来调用方法
        Method[] methods = controllerBean.getClass().getDeclaredMethods();
//       getClass().getDeclaredMethod(operation,HttpServletRequest.class, HttpServletResponse.class);
        // 如果有有某个方法名与operation相同，则调用
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(operation)) {
                try {
                    methods[i].setAccessible(true);
                    methods[i].invoke(controllerBean, req, resp);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("输入参数错误");

    }
}
