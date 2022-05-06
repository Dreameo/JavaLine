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

    public String register(String uname, String pwd, String email, String kaptcha, HttpSession session) {
        Object kaptcha_session_key = session.getAttribute("KAPTCHA_SESSION_KEY");
        if(kaptcha_session_key == null || !kaptcha.equals(kaptcha)) {
            return "user/regist";
        } else {
            if (kaptcha_session_key == kaptcha) {
                userService.register(new UserBean(uname, pwd, email, 0));
                return "user/login";
            }
        }
        return "user/login";
    }

    public String chUname(String uname) {
        UserBean user = userService.getUserByName(uname);
        if(user != null) {
//            return "ajax:1";
            return "json:{'uname':1}";
        } else {
            return "json:{'uname':0}";
//            return "ajax:0";
        }
    }
}
