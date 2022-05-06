package com.yfh.fruit.service.impl;

import com.yfh.fruit.service.FruitService;
import com.yfh.fruit.dao.FruitDAO;
import com.yfh.fruit.pojo.Fruit;

import java.util.List;

public class FruitServiceImpl implements FruitService {
    private FruitDAO fruitDAO = null;

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
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
