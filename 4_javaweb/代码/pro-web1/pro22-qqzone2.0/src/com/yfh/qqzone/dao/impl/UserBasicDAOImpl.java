package com.yfh.qqzone.dao.impl;

import com.yfh.myssm.basedao.BaseDAO;
import com.yfh.qqzone.dao.UserBasicDAO;
import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {
    @Override
    public UserBasic getUserById(Integer uid) {
        return super.load("SELECT * FROM t_user_basic WHERE id = ?", uid);
    }

    @Override
    public UserBasic getUserById(String loginId, String pwd) {
        String sql = "SELECT * FROM t_user_basic WHERE loginId = ? AND pwd = ?";
        return super.load(sql, loginId, pwd);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        String sql = "SELECT u2.* FROM t_user_basic u1 INNER JOIN t_friend ON\n" +
                "u1.id = uid INNER JOIN t_user_basic u2 ON u2.id = fid WHERE u1.id = ?";
        return super.executeQuery(sql, userBasic.getId());
    }
}
