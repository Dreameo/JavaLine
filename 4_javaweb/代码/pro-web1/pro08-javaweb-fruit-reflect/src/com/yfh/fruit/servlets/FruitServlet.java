package com.yfh.fruit.servlets;

import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.dao.impl.FruitDAOImpl;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.myspringmvc.ViewBaseServlet;
import com.yfh.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String operation = req.getParameter("operation");
        System.out.println(operation);
        if (StringUtil.isEmpty(operation)) {
            operation = "index";
        }
        // 通过反射来调用方法
        Method[] methods = this.getClass().getDeclaredMethods();
//       getClass().getDeclaredMethod(operation,HttpServletRequest.class, HttpServletResponse.class);
        // 如果有有某个方法名与operation相同，则调用
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(operation)) {
                try {
                    methods[i].invoke(this, req, resp);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("输入参数错误");
//
//        // 根据传入参数来选择Servlet操作
//        switch (operation) {
//            case "index" :
//                index(req, resp);
//                break;
//            case "add":
//                add(req, resp);
//                break;
//            case "del":
//                del(req, resp);
//                break;
//            case "edit":
//                edit(req, resp);
//                break;
//            case "update":
//                update(req, resp);
//                break;
//            default:
//
//
//        }
    }

    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("addServlet");
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        int count = fruitDAO.addFruit(fruit);
        resp.sendRedirect("fruit.do");
        if (count > 0)
            System.out.println("成功");
        else
            System.out.println("失败");
    }

    protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (!StringUtil.isEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            int count = fruitDAO.deleteFruit(fid);
            resp.sendRedirect("fruit.do");
        }
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer fid = Integer.parseInt(req.getParameter("fid"));
        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount, remark));
//        super.processTemplate("index", req, resp); // 这句话相当于服务器转发
        resp.sendRedirect("fruit.do");
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("编辑页面");
        String fidStr = req.getParameter("fid");
        System.out.println("fidStr = " + fidStr);

        if (!StringUtil.isEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
    }
}
