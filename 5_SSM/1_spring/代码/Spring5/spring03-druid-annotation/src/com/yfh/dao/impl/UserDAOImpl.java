package com.yfh.dao.impl;

import com.yfh.dao.UserDAO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Override
    public void add() {
        System.out.println("add....userdaoImpl");
    }
}
