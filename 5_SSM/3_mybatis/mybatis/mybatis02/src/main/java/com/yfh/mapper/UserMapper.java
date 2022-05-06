package com.yfh.mapper;

import com.yfh.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * 增加用户
     */
    int addUser();

    /**
     * 查询用户
     */
    List<User> getAllUser();
}
