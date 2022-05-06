package com.yfh.ssm;

import com.yfh.ssm.pojo.Books;
import com.yfh.ssm.service.BookService;
import com.yfh.ssm.service.impl.BookServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTEst {

    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        Books book = bookService.getBookById(1);
        System.out.println(book);

    }
}
