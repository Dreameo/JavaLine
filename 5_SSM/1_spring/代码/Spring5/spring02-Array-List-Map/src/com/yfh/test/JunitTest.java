package com.yfh.test;

import com.yfh.beans.Order;
import com.yfh.beans.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student stu = (Student) context.getBean("stu");
        System.out.println("stu = " + stu);
    }

    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");
        Order order = (Order) context.getBean("order");
        System.out.println("第四步：获取bean对象" + order);
        // 手动销毁
        ((ClassPathXmlApplicationContext)context).close();
    }
}
