package com.yfh.book.service.impl;

import com.yfh.book.dao.UserDAO;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.UserService;

public class UserServiceImpl implements UserService {
    UserDAO userDAO;
    @Override
    public UserBean login(String uname, String pwd) {
        return userDAO.getUserByName(uname, pwd);
    }
}
