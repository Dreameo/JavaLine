package com.yfh.qqzone.service.impl;

import com.yfh.qqzone.dao.UserBasicDAO;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.UserBasicService;

import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    UserBasicDAO userBasicDAO = null;

    /**
     *
     * @param loginId
     * @param pwd
     * @return
     */

    @Override
    public UserBasic login(String loginId, String pwd) {
        return userBasicDAO.getUserById(loginId, pwd);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        return userBasicDAO.getFriendList(userBasic);
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return userBasicDAO.getUserById(id);
    }
}
