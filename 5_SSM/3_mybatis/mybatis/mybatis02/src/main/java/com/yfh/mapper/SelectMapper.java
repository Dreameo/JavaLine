package com.yfh.mapper;

import com.yfh.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SelectMapper {
    /**
     * 根据id获取用户信息
     */
    User getUserById(@Param("id") Integer id);

    /**
     * Select to Map
     */
    List<Map<String, Object>> getAllUserToMap();
}
