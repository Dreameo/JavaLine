package com.yfh.servlets;

import com.yfh.dao.FruitDAO;
import com.yfh.dao.impl.FruitDAOImpl;
import com.yfh.qqzone.pojo.Fruit;
import com.yfh.springmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();

        // 保存到作用域
        HttpSession session = req.getSession();
        session.setAttribute("fruitList", fruitList);
        // 此处的视图名称为：index
        // 那么thymeleaf会将这个 逻辑视图名称  对应到  物理视图名称  上去
        // 逻辑视图名称：index
        // 物理视图名称： view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是  /       index     .html
        super.processTemplate("index", req, resp);
    }
}
