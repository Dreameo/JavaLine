package com.yfh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
//    @RequestMapping(value = "/")
//    public String index() {
//        return "index";
//    }
//    @RequestMapping("/testInterceptor")
    @RequestMapping("/**/testInterceptor")
    public String testInterceptor() {
        return "success";
    }

    @RequestMapping("/testException")
    public String testException() {
        int i = 10 / 0;
        System.out.println(i);
        return "success";
    }
}
