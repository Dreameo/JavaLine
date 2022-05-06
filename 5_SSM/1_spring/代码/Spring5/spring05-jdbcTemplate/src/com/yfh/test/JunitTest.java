package com.yfh.test;

import com.yfh.pojo.Book;
import com.yfh.service.BookService;
import com.yfh.service.impl.BookServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class JunitTest {

    @Test
    public void testAdd() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        Book book = new Book();
        book.setUserId("2");
        book.setUserName("书名呀111");
        book.setuStatus("a");

        bookService.addBook(book);
    }

    @Test
    public void testUpdate() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        Book book = new Book();
        book.setUserId("1");
        book.setUserName("改改了的");
        book.setuStatus("b");

        bookService.updateBook(book);
    }

    @Test
    public void testDel() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        String id = "1";

        bookService.deleteBook(id);
    }

    @Test
    public void testQueryCount() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);


        System.out.println("bookService.findCount() = " + bookService.findCount());
    }

    @Test
    public void testQueryBook() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);
        System.out.println("bookService.findBookById(\"1\") = " + bookService.findBookById("1"));
    }

    @Test
    public void testQueryList() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);

        System.out.println("bookService.findBookList() = " + bookService.findBookList());
    }

    @Test
    public void testInsertList() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BookService bookService = context.getBean("bookServiceImpl", BookServiceImpl.class);
        List<Object[]> list = new ArrayList<>();
        Object[] o1 = {"4", "java语言", "a"};
        Object[] o2 = {"5", "c语言", "a"};
        Object[] o3 = {"6", "PHPyuyan1", "c"};
        list.add(o1);
        list.add(o2);
        list.add(o3);
        bookService.addBatchBook(list);



        System.out.println("bookService.findBookList() = " + bookService.findBookList());
    }
}
