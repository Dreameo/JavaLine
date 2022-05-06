package com.yfh.fruit.servlets;

import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/del.do")
public class DeleteServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (!StringUtil.isEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            int count = fruitDAO.deleteFruit(fid);
            resp.sendRedirect("index");
        }
    }
}
