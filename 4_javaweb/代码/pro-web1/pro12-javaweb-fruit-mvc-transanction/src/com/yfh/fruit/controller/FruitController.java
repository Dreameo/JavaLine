package com.yfh.fruit.controller;

import com.yfh.fruit.service.FruitService;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class FruitController {
    private FruitService fruitService = null;


    public String index(String oper, String keyword, Integer pageNo,HttpSession session) {
        if (pageNo == null)
            pageNo = 1;

        if (!StringUtil.isEmpty(oper) && "search".equals(oper)) { // 如果是搜索进来的，将关键字获取一下，进行搜索 pageNo=1
//            pageNo = 1;
            if (StringUtil.isEmpty(keyword))
                keyword = "";
            session.setAttribute("keyword", keyword);

        } else { // 从地址栏或者直接进入首页,也要查看是否为关键字查找到的,session中获取关键字
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null)
                keyword = (String) keywordObj;
            else
                keyword = "";
        }

        // 记录总数和页数
//        int totalCount = fruitDAO.getCount(keyword);
        int pageCount = fruitService.getPageCount(keyword);

        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
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
//        super.processTemplate("index", request, response);
        return "index";
    }


    protected String add(String fname, Integer price, Integer fcount, String remark) {
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitService.addFruit(fruit);
//        resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    protected String del(Integer fid) {
        fruitService.delFruit(fid);
//            resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    protected String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {

        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));
//        super.processTemplate("index", req, resp); // 这句话相当于服务器转发
//        resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    protected String edit(Integer fid, HttpServletRequest req) {
        Fruit fruit = fruitService.getFruitById(fid);
        req.setAttribute("fruit", fruit);
//            super.processTemplate("edit", req, resp);
        return "edit";

    }
}
