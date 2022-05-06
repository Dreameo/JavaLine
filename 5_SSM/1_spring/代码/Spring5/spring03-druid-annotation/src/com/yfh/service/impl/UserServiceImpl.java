package com.yfh.service.impl;

import com.yfh.dao.UserDAO;
import com.yfh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 定义dao类型的属性, 不需要加set方法，在属性上添加注解即可
    @Autowired // 根据类型进行注入
    private UserDAO userDAO;

    @Override
    public void add() {
        System.out.println("userserviceimpl.. add");
        userDAO.add();
    }
}
