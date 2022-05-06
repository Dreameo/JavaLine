package com.yfh.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileUpAndDownController {

    @RequestMapping("/testDown")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext(); // 整个工程
        //获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/img/1.jpg"); // 获取服务器布署的真实路径
        System.out.println("realPath = " + realPath);
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];  // is.availabe()当前输入流对应的字节数
        //将流读到字节数组中
        is.read(bytes); // 所有字节放入
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders(); // 其实就是一个Map
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg"); // 除了1.jpg可以改，其他都固定
        //
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象 ，bytes就是相应体，我们要的内容，header相应头
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

    @RequestMapping("/testUp")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
//        System.out.println("photo.getName() = " + photo.getName());
//        System.out.println("photo.getOriginalFilename() = " + photo.getOriginalFilename());
        String filename = photo.getOriginalFilename(); // 获取图片的源文件名
        System.out.println("filename = " + filename);
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("photo"); // 服务器路径下photo目录下,有可能不存在
        File file = new File(realPath); // 动态地创建，如果没有就创建，如果有就不创建
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = realPath + File.separator + filename;
        photo.transferTo(new File(finalPath));

        return "success";
    }
}
