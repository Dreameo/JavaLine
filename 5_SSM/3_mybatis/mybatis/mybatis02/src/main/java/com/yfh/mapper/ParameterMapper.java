package com.yfh.mapper;

import com.yfh.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ParameterMapper {
    /**
     *  获取所有用户
     */
    List<User> getAllUser();

    /**
     * 添加用户信息
     *
     */
    int insertUser(User user);

    /**
     * 根据name来获取用户
     */

    User getUserByName(String name);

    /**
     * 登录验证
     */
    User checkLogin(String username, String password);

    /**
     * 登录验证Map
     */
    User checkLoginByMap(Map<String, Object> map);

    /**
     * 登录验证@Param
     */
    User checkLoginByParam(@Param("username") String username, @Param("password") String password);
}
