package com.yfh.fruit.service.impl;

import com.yfh.fruit.service.FruitService;
import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.pojo.Fruit;
import com.yfh.myssm.basedao.ConnUtil;

import java.util.List;

public class FruitServiceImpl implements FruitService {
    private FruitDAO fruitDAO = null;

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        System.out.println("ConnUtil.getConnection() = " + ConnUtil.getConnection());
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit); // 1.添加水果
        /**
         * 模拟事务出错回滚
         * 正常有事务的过程是：只要这三个步骤中有一个操作有错误，就回滚
         */
        Fruit fruit1 = fruitDAO.getFruitById(2); // 2. 查询水果
        fruit1.setFcount(899); //
        fruitDAO.updateFruit(fruit); // 3. 更新水果


    }

    @Override
    public Fruit getFruitById(Integer id) {
        return fruitDAO.getFruitById(id);
    }

    @Override
    public void delFruit(Integer id) {
        fruitDAO.deleteFruit(id);
    }

    @Override
    public Integer getPageCount(String keyword) {
        System.out.println("ConnUtil.getConnection() = " + ConnUtil.getConnection());
        int count = fruitDAO.getCount(keyword);
//        fruitDAO.getCount(keyword);
        int pageCount = (count + 5 - 1) /5;
        return pageCount;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }


}
