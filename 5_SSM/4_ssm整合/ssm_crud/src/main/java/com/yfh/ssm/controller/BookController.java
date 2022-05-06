package com.yfh.ssm.controller;

import com.yfh.ssm.pojo.Books;
import com.yfh.ssm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        System.out.println("Controller 查找了信息");
        // 调用service
        Books book = bookService.getBookById(1);

        System.out.println(book);
        model.addAttribute("book", book);
        return "book/search";
    }
}
