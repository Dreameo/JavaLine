package com.yfh.book.service.impl;

import com.yfh.book.dao.OrderDAO;
import com.yfh.book.dao.OrderItemDAO;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.OrderBean;
import com.yfh.book.pojo.OrderItem;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.CartService;
import com.yfh.book.service.OrderItemService;
import com.yfh.book.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private OrderItemService orderItemService;
    private CartService cartService;

    @Override
    public void addOrderBean(OrderBean orderBean) {
        orderDAO.addOrder(orderBean); // orderBean中有值
        // orderBean中OrderItemList为空，我们要从用户购物车中Item中构造
        Map<Integer, CartItem> cartItemMap = orderBean.getOrderUser().getCart().getCartItemMap();
        for (CartItem cartItem : cartItemMap.values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setId(cartItem.getId());
            orderItem.setBuyCount(cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            orderItemService.addOrderItem(orderItem);
        }

        for (CartItem cartItem : cartItemMap.values()) {
            cartService.delCart(cartItem);
        }

    }

    @Override
    public List<OrderBean> getOrderListByUser(UserBean userBean) {
        // userBean 但是没有订单综述
        List<OrderBean> orderList = orderDAO.getOrderListByUser(userBean);
        // 用户的订单列表
        // 遍历订单列表，每个订单购买书本的总数
        for (OrderBean orderBean : orderList) {
            orderBean.setOrderUser(userBean);
            Integer totalBookCount = orderDAO.getTotalBookCount(orderBean);
            orderBean.setBookTotalCount(totalBookCount);
        }
        return orderList;
    }

}
