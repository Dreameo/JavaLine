package com.yfh.fruit.servlets;

import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        System.out.println("编辑页面");
        String fidStr = req.getParameter("fid");
        System.out.println("fidStr = " + fidStr);

        if (!StringUtil.isEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
        super.doGet(req, resp);
    }
}
