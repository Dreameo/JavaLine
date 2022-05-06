package com.yfh.fruit.servlets;

import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("addServlet");
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        int count = fruitDAO.addFruit(fruit);
        resp.sendRedirect("index");
        if (count > 0)
            System.out.println("成功");
        else
            System.out.println("失败");


    }
}
