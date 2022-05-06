package com.yfh.test;

import com.yfh.beans.BookArgs;
import com.yfh.beans.BookSet;
import com.yfh.beans.Employee;
import com.yfh.beans.User;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.print.Book;

public class JunitTest {

    @Test
    public void test1() {
        // 1. 加载beans配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 2. 获取bean
        User user = context.getBean("user", User.class);

        System.out.println("user = " + user);
        user.add();

    }

    @Test
    public void testBookSet() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BookSet bookSet = (BookSet) context.getBean("bookSet");

        /**
         * 输出结果 bookSet.getName() = 易经经
         * 说明已经将值注入到了属性中
         */
        System.out.println("bookSet.getName() = " + bookSet.getName()); // bookSet.getName() = 易经经


    }

    @Test
    public void testBookArgs() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BookArgs bookArgs = (BookArgs) context.getBean("bookArgs");

        /**
         * 结果为：bookArgs.out() = 这里name的值为：鲁滨逊漂流记， author的值为：不知道
         * 属性值注入完成
         */
        System.out.println("bookArgs.out() = " + bookArgs.out());

    }

    @Test
    public void testInnerBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
        Employee employee = (Employee) context.getBean("emp");
        System.out.println(employee);

    }
}
