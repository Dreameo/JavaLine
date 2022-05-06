package com.yfh.book.dao;

import com.yfh.book.pojo.UserBean;

import java.util.List;

public interface UserDAO {
    // 根据账号和密码查询用户
    UserBean getUserByName(String uname, String pwd);

    void addUser(UserBean userBean);

    UserBean getUserByName(String uanme);


}
