package com.yfh.dao;

import com.yfh.qqzone.pojo.Fruit;

import java.util.List;

public abstract interface FruitDAO {
    // 1. 获取全部水果列表
     List<Fruit> getFruitList();

}
