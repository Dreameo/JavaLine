package com.yfh.fruit.servlets;


import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp); // 逻辑类似与get请求，搜索结果同样需要分页
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        // 分页展示
        int pageNo = 1;
        String keyword = null; // 默认关键字为空
        String oper = request.getParameter("oper");


        if (!StringUtil.isEmpty(oper) && "search".equals(oper)) { // 如果是搜索进来的，将关键字获取一下，进行搜索 pageNo=1
//            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword))
                keyword = "";
            session.setAttribute("keyword", keyword);

        } else { // 从地址栏或者直接进入首页,也要查看是否为关键字查找到的,session中获取关键字
            String pageNoStr = request.getParameter("pageNo");
            if (!StringUtil.isEmpty(pageNoStr))
                pageNo = Integer.parseInt(pageNoStr);

            Object keywordObj = session.getAttribute("keyword");
            System.out.println("keywordObj = " + keywordObj);
            if (keywordObj != null)
                keyword = (String) keywordObj;
            else
                keyword = "";
            System.out.println("keyword = " + keyword);
        }

        // 记录总数和页数
        int totalCount = fruitDAO.getCount(keyword);
        int pageCount = (totalCount + 5 - 1) / 5;

        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        System.out.println(fruitList);

        //保存到session作用域

        session.setAttribute("pageNo", pageNo);
        session.setAttribute("pageCount", pageCount);

        session.setAttribute("fruitList", fruitList);
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index", request, response);
    }
}
