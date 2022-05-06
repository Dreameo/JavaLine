package com.yfh.myssm.listener;

import com.yfh.myssm.ioc.BeanFactory;
import com.yfh.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 */

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("监听器启动》》》listener init...");
        // 监听器一启动，我们就创建ioc容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        ServletContext application = sce.getServletContext();
        application.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
