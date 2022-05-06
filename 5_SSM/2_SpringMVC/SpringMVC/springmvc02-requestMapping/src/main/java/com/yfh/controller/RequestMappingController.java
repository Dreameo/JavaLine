package com.yfh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class RequestMappingController {

    @RequestMapping("/testRequestMapping")
    public String success() {
        return "success";
    }


    public String testModelMap(ModelMap modelMap) {
        modelMap.addAttribute("hello", "hello ModelMap");
        ModelAndView modelAndView = new ModelAndView();
        return "success";
    }
}
