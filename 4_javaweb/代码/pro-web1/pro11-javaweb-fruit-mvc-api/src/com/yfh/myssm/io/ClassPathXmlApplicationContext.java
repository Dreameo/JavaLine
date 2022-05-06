package com.yfh.myssm.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicationContext() {
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
                    String beanId = beanElement.getAttribute("id"); // bean中的id
                    String beanClass = beanElement.getAttribute("class");  // bean中的class
                    Class beanClazz = Class.forName(beanClass);
                    Object beanObj = beanClazz.newInstance(); // bean的对象
//                    Method method = controllerBean.getClass().getDeclaredMethod("setServletContext",ServletContext.class);
//                    method.setAccessible(true);
//                    method.invoke(controllerBean, this.getServletContext());
                    beanMap.put(beanId, beanObj);   //到目前为止，此处需要注意的是，bean和bean之间的依赖关系还没有设置
                }

            }


            for (int i = 0; i < beanList.getLength(); i++) {
                Node beanNode = beanList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE) { // <bean> <property name, ref > </property> </bean>
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList childNodes = beanElement.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if(childNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(childNode.getNodeName())) {
                            Element childNodeElement = (Element) childNode;
                            String childNodeName = childNodeElement.getAttribute("name");
                            String childNodeRef = childNodeElement.getAttribute("ref");
                            //1) 找到propertyRef对应的实例

                            Object refObj = beanMap.get(childNodeRef);

                            //2) 将refObj设置到当前bean对应的实例的property属性上去
                            Object beanObj = beanMap.get(beanId);
                            Field propertyField = beanObj.getClass().getDeclaredField(childNodeName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj, refObj);
                        }
                    }
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
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String classId) {
        return beanMap.get(classId);
    }
}
