package com.yfh.fruit.servlets;

import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        Integer fid = Integer.parseInt(req.getParameter("fid"));
        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount,remark));
//        super.processTemplate("index", req, resp); // 这句话相当于服务器转发
        resp.sendRedirect("index");

    }
}
