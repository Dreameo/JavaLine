package com.yfh.savefield;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// request保存作用域
@WebServlet("/demo01")
public class Demo01Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", "李明");

//        resp.sendRedirect("demo02"); // 客户端转发，不同请求了

        req.getRequestDispatcher("demo02").forward(req, resp); // 服务器内部转发，还是一次请求，可以得到name的值
    }
}
