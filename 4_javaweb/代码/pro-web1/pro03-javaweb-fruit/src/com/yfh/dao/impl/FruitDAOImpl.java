package com.yfh.dao.impl;

import com.yfh.dao.FruitDAO;
import com.yfh.dao.base.BaseDAO;
import com.yfh.qqzone.pojo.Fruit;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO{
    @Override
    public List<Fruit> getFruitList() {
        String sql = "SELECT * FROM t_fruit";
        return executeQuery(sql);
    }
}
