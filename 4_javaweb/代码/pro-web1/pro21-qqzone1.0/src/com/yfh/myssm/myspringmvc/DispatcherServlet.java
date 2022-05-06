package com.yfh.myssm.myspringmvc;

import com.yfh.myssm.ioc.BeanFactory;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 中央控制器，核心控制器，接受所有的请求，根据不同的请求定位到不同到不同的Servlet（FruitServlet/UserServlet）中去(也可叫FruitController)的不同方法中（增删改查）
 */
@WebServlet("*.do") // 拦截所有以.do结尾的请求
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory ;

    public DispatcherServlet() {

    }


    public void init() throws ServletException {
        System.out.println("config-init");
        super.init();

//        beanFactory = new ClassPathXmlApplicationContext();
        ServletContext application = getServletContext();
        Object obj = application.getAttribute("beanFactory");
        if (obj != null) {
            beanFactory = (BeanFactory) obj;
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. 设置编码格式
        // /hello.do  (字符串截取)->  hello  --> HelloController
        System.out.println("req.getServletPath() = " + req.getServletPath()); // /hello.do
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndex);

//        Object controllerBean = beanMap.get(servletPath);
        Object controllerBean = beanFactory.getBean(servletPath); // FruitController

        System.out.println("controllerBean = " + controllerBean);

        String operation = req.getParameter("operation");  // the value of operation --> login/ index/

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

        Method[] methods = controllerBean.getClass().getDeclaredMethods(); // 方法
//       getClass().getDeclaredMethod(operation,HttpServletRequest.class, HttpServletResponse.class);
        // 如果有有某个方法名与operation相同，则调用
        // 1. 统一获取参数
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(operation)) {
                // 获取当前方法的参数
                Parameter[] parameters = methods[i].getParameters(); // 获取参数

                // 存放各个参数
                Object[] parameterValues = new Object[parameters.length];

                for (int i1 = 0; i1 < parameters.length; i1++) {
                    Parameter parameter = parameters[i1];
                    String parameterName = parameter.getName();
                    if ("req".equals(parameterName)) {
                        parameterValues[i1] = req;
                    } else if("resp".equals(parameterName)) {
                        parameterValues[i1] = resp;
                    } else if("session".equals(parameterName)) {
                        parameterValues[i1] = req.getSession();
                    } else {
                        String parameterValue = req.getParameter(parameterName);
                        String typeName = parameters[i1].getType().getName();
                        Object parameterObj = parameterValue;
                        if (parameterObj != null) {
                            if ("java.lang.Integer".equals(typeName)) {
                                parameterObj = Integer.parseInt(parameterValue);
                            }
                        }
                        parameterValues[i1] = parameterObj;
                    }
                }
                try {
                    // 2. 函数调用
                    methods[i].setAccessible(true);
                    Object returnObj = methods[i].invoke(controllerBean, parameterValues);

                    // 3. 视图处理
                    String methodReturnStr = (String) returnObj;
                    if (methodReturnStr.startsWith("redirect:")) { // 比如redirect: fruit.do
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, req, resp); // 比如返回index， edit
                    }
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
