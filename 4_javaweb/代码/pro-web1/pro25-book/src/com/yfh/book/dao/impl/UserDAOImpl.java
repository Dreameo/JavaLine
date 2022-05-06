package com.yfh.book.dao.impl;

import com.yfh.book.dao.UserDAO;
import com.yfh.book.pojo.UserBean;
import com.yfh.myssm.basedao.BaseDAO;

public class UserDAOImpl extends BaseDAO<UserBean> implements UserDAO {
    @Override
    public UserBean getUserByName(String uname, String pwd) {
        return super.load("SELECT * FROM t_user WHERE uname like ? AND pwd like ?", uname, pwd);
    }

    @Override
    public void addUser(UserBean userBean) {
        super.executeUpdate("INSERT INTO t_user VALUES(0, ?, ?, ?, 0)", userBean.getUname(), userBean.getPwd(), userBean.getEmail());
    }

    @Override
    public UserBean getUserByName(String uanme) {
        return super.load("SELECT * FROM t_user WHERE uname = ?", uanme);
    }
}
