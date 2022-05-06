package com.yfh.book.dao;

import com.yfh.book.pojo.OrderBean;
import com.yfh.book.pojo.UserBean;

import java.util.List;

public interface OrderDAO {

    /**
     * 1. 往订单表中添加一条数据
     * 2. 往订单项中添加购物车项（多条）
     * 3. 删除购物车
     * @param orderBean
     */
    void addOrder(OrderBean orderBean);

//    OrderBean getOrderByUser(UserBean userBean);

    List<OrderBean> getOrderListByUser(UserBean userBean);

    Integer getTotalBookCount(OrderBean orderBean);

}
