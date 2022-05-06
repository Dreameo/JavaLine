package com.yfh.book.service;

import com.yfh.book.pojo.OrderBean;
import com.yfh.book.pojo.UserBean;

import java.util.List;

public interface OrderService {

    /**
     * 结账功能
     */

    void addOrderBean(OrderBean orderBean);

    List<OrderBean> getOrderListByUser(UserBean userBean);

}
