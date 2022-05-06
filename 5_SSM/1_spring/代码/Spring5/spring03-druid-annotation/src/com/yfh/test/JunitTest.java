package com.yfh.test;

import com.yfh.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {
    @Test
    public void testUserService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        System.out.println("userService = " + userService);
        userService.add();
    }
}
