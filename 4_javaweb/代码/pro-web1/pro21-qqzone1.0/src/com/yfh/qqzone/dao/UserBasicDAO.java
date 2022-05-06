package com.yfh.qqzone.dao;

import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDAO {
    // 1. 根据id获取User
    UserBasic getUserById(Integer uid);

    // 2. 根据账号和密码获取特定用户
    UserBasic getUserById(String loginId, String pwd);

    // 3. 获取指定用户的好友列表

    List<UserBasic> getFriendList(UserBasic userBasic);



}
