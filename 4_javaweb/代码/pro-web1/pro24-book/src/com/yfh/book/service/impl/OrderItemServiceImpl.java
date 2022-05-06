package com.yfh.book.service.impl;

import com.yfh.book.dao.OrderItemDAO;
import com.yfh.book.pojo.OrderItem;
import com.yfh.book.service.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {
    private  OrderItemDAO orderItemDAO;
    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }
}
