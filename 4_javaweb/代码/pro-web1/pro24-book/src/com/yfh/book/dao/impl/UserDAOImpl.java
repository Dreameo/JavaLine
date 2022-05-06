package com.yfh.book.dao.impl;

import com.yfh.book.dao.UserDAO;
import com.yfh.book.pojo.UserBean;
import com.yfh.myssm.basedao.BaseDAO;

public class UserDAOImpl extends BaseDAO<UserBean> implements UserDAO {
    @Override
    public UserBean getUserByName(String uname, String pwd) {
        return super.load("SELECT * FROM t_user WHERE uname like ? AND pwd like ?", uname, pwd);
    }
}
