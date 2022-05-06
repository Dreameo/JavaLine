package com.yfh.dao.impl;

import com.yfh.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    // lucy转账100给mary
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addMoney() {
        String sql = "UPDATE t_account SET money = money + ? WHERE username = ?";
        jdbcTemplate.update(sql, 100, "mary");
    }

    @Override
    public void reduceMoney() {
        String sql = "UPDATE t_account SET money = money - ? WHERE username = ?";
        jdbcTemplate.update(sql, 100, "lucy");
    }
}
