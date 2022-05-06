package com.yfh.book.dao.impl;

import com.yfh.book.dao.OrderDAO;
import com.yfh.book.pojo.OrderBean;
import com.yfh.book.pojo.UserBean;
import com.yfh.myssm.basedao.BaseDAO;

import java.math.BigDecimal;
import java.util.List;

public class OrderDAOImpl extends BaseDAO<OrderBean> implements OrderDAO {
    @Override
    public void addOrder(OrderBean orderBean) {
        int orderId = executeUpdate("INSERT INTO t_order VALUES(0, ?,?,?,?,?)", orderBean.getOrderNo(), orderBean.getOrderDate()
        , orderBean.getOrderUser().getId(), orderBean.getOrderMoney(), orderBean.getOrderStatus());
        orderBean.setId(orderId);
    }

    @Override
    public List<OrderBean> getOrderListByUser(UserBean userBean) {
        return super.executeQuery("SELECT * FROM t_order WHERE orderUser = ?", userBean.getId());
    }

    @Override
    public Integer getTotalBookCount(OrderBean orderBean) {
        String sql = "SELECT SUM( buyCount ) FROM (SELECT t1.*, t2.buyCount, t2.orderBean FROM t_order t1 INNER JOIN t_order_item t2 ON t1.id = t2.orderBean WHERE t1.orderUser = ?) t3 WHERE orderBean = ? GROUP BY orderBean";
        Object[] objects = super.executeComplexQuery(sql, orderBean.getOrderUser().getId(), orderBean.getId());
        return ((BigDecimal)objects[0]).intValue();
    }


}
