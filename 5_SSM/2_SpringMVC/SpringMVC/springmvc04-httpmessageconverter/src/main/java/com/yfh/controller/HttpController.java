package com.yfh.controller;

import com.yfh.bean.User;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class HttpController {

    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println("requestBody = " + requestBody);
        return "success";
    }

    @RequestMapping("/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        // 报文信息
        System.out.println("requestEntity = " + requestEntity);
        System.out.println("--------------------------------------");
        // 请求头
        System.out.println("requestEntity.getHeaders() = " + requestEntity.getHeaders());
        System.out.println("--------------------------------------");
        // 请求体
        System.out.println("requestEntity.getBody() = " + requestEntity.getBody());
        return "success";
    }

    @RequestMapping("/testResponse")
    public String testResponse(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print("HEllo response");
        return null;
    }

    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody() {
        return "success12341234";
    }

    @RequestMapping("/testResponseBodyUser")
    @ResponseBody
    public User testResponseBodyUser() {
        return new User(1, "tom", "password", 20, "男");

    }

    @RequestMapping("/testAxios")
    @ResponseBody
    public String testAxios(String username, String password) {
        System.out.println("password = " + password);
        return "hello Axios";
    }
}
