package com.yfh.fruit.service;

import com.yfh.fruit.pojo.Fruit;

import java.util.List;

public interface FruitService {
    // 获取制定页面的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);

    // 添加库存信息
    void addFruit(Fruit fruit);

    // 根据id查看库存信息
    Fruit getFruitById(Integer id);
    // 删除特定库存记录
    void delFruit(Integer id);

    // 获取总页数
    Integer getPageCount(String keyword);

    // 更新水果信息
    void updateFruit(Fruit fruit);
}
