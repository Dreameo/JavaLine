package com.yfh.fruit.dao;


import com.yfh.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //获取所有的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);

    /**
     * 根据id获取水果信息
     */
    Fruit getFruitById(int fid);

    /**
     * 更新水果信息
     */
    int updateFruit(Fruit fruit);

    /**
     * 删除水果信息
     */

    int deleteFruit(Integer fid);

    /**
     * 添加一条水果信息
     *
     */

    int addFruit(Fruit fruit);

    /**
     * 获取水果的总记录数
     */

    int getCount(String keyword);

}
