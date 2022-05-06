package com.yfh.book.controller;

import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.CartService;
import com.yfh.book.service.UserService;

import javax.servlet.http.HttpSession;

public class UserController {
    UserService userService;
    CartService cartService;

    /**
     * 登录操作
     * @return
     */
    public String login(String uname, String pwd, HttpSession session) {
        UserBean user = userService.login(uname, pwd);
        if (user != null) {
            Cart cart = cartService.getCart(user);
            user.setCart(cart);
            session.setAttribute("user", user);
            return "redirect:book.do";
        }
        return "user/login";
    }
}
