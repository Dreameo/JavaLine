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

    @Override
    public Fruit getFruitById(int fid) {
        String sql = "SELECT * FROM t_fruit WHERE fid = ?";
        return super.load(sql, fid);
    }

    @Override
    public int updateFruit(Fruit fruit) {
        String sql = "UPDATE t_fruit SET fname = ?, price = ?, fcount = ?, remark = ? WHERE fid = ?";
        return super.executeUpdate(sql,fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public int deleteFruit(Integer fid) {
        String sql = "DELETE FROM t_fruit WHERE fid = ?";
        return super.executeUpdate(sql, fid);
    }

    @Override
    public int addFruit(Fruit fruit) {
        String sql = "INSERT INTO t_fruit(fname,price,fcount,remark) VALUES(?,?,?,?)";
        return super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }
}
