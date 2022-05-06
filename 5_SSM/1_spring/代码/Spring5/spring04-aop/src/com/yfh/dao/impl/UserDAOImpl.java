package com.yfh.dao.impl;

import com.yfh.dao.UserDAO;

/**
 * 被代理类
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public int add(int a, int b) {
        System.out.println("add方法执行了");
        return a + b;
    }

    @Override
    public String update(String id) {
        System.out.println("update执行了");
        return id;
    }
}
