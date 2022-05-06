package com.yfh.z_book.filters;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"*.do", "*.html"}, initParams = {
        @WebInitParam(name = "whiteList", value = "/pro24/page.do?operation=page&page=user/login,/pro24/user.do?null")
})
public class SessionFilter implements Filter {
    List<String> whiteList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String whiteListStr = filterConfig.getInitParameter("whiteList");
        String[] split = whiteListStr.split(",");
        whiteList = Arrays.asList(split);
    }
//    http://localhost:8080/pro24/page.do?operation=page&page=user/login
//                         /pro24/page.do?operation=page&page=user/login
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());

        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String str = uri + "?" + queryString;
        System.out.println("str = " + str);

        if (whiteList.contains(str)) {
            filterChain.doFilter(request, response);
            System.out.println("cccc");
        } else {
            System.out.println("dddd");
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("page.do?operation=page&page=user/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
