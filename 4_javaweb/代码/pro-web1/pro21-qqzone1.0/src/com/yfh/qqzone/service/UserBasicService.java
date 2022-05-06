package com.yfh.qqzone.service;

import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicService {
    // 登录方法
    UserBasic login(String loginId, String pwd);

    // 获取好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);
}
