package com.yfh.book.service;

import com.yfh.book.pojo.UserBean;

public interface UserService {
    // 登录业务
    UserBean login(String uname, String pwd);

    void register(UserBean userBean);

    UserBean getUserByName(String uname);
}
