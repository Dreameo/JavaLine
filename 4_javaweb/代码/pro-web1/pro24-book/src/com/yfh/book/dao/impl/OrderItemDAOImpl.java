package com.yfh.book.dao.impl;

import com.yfh.book.dao.OrderItemDAO;
import com.yfh.book.pojo.OrderItem;
import com.yfh.myssm.basedao.BaseDAO;

public class OrderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {
    @Override
    public void addOrderItem(OrderItem orderItem) {
        super.executeUpdate("INSERT INTO t_order_item VALUES(0, ?, ?, ?)", orderItem.getBook().getId(), orderItem.getBuyCount(), orderItem.getOrderBean().getId());
    }
}
