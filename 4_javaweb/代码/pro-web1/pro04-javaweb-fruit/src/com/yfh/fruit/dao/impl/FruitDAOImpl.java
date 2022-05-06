package com.yfh.fruit.dao.impl;


import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }
}
