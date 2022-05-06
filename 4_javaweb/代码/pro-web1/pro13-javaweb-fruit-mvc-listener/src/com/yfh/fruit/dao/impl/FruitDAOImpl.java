package com.yfh.fruit.dao.impl;


import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        String sql = "SELECT * FROM t_fruit WHERE fname LIKE ? OR remark LIKE ? LIMIT ?, 5";
        return super.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%", (pageNo - 1) * 5);
    }

    @Override
    public Fruit getFruitById(int fid) {
        String sql = "SELECT * FROM t_fruit WHERE fid = ?";
        return super.load(sql, fid);
    }

    @Override
    public int updateFruit(Fruit fruit) {
//        String sql = "UPDATE t_fruit SET fname = ?, price = ?, fcount = ?, remark = ? WHERE fid = ?";
        String sql = "UPDAT t_fruit SET fname = ?, price = ?, fcount = ?, remark = ? WHERE fid = ?"; // 故意报错，测试事务运行
        return super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
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

    @Override
    public int getCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM t_fruit WHERE fname LIKE ? OR remark LIKE ? ";
        return ((Long) super.executeComplexQuery(sql, "%" + keyword + "%", "%" + keyword + "%")[0]).intValue(); // 返回的是Long，之后在取int值
    }
}
