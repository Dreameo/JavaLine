package com.yfh.service.impl;

import com.yfh.dao.UserDAO;
import com.yfh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // 所有的方法都添加事务，如果添加到方法上，那就是那个方法才有事务管理
/**
 * 声明式事务管理：可以配置事务相关参数：//@Transactional(propagation = Propagation.REQUIRED,isolation=) 默认传播行为
 */

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public void accountMoney() {
        // lucy - 100
        userDAO.reduceMoney();

        int i = 10/0; // 模拟异常

        // mary + 100
        userDAO.addMoney();
    }
}
