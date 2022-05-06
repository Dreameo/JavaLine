package com.yfh.test;

import com.yfh.config.SpringConfig;
import com.yfh.service.UserService;
import com.yfh.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class testFullAnnotation {
    @Test
    public void test1() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userServiceImpl = context.getBean("userServiceImpl", UserServiceImpl.class);
        userServiceImpl.add();
        System.out.println("userServiceImpl = " + userServiceImpl);

    }


}
