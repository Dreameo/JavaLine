package com.yfh.book.pojo;

import java.util.Date;
import java.util.List;

public class OrderBean {
    private Integer id;
    private String orderNo;
    private Date orderDate;
    private UserBean orderUser; // M : 1
    private Double orderMoney;
    private Integer orderStatus;
    private Integer bookTotalCount;

    private List<OrderItem> orderItemList; // 1: M

    public OrderBean() {
    }

    public OrderBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public UserBean getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(UserBean oderUser) {
        this.orderUser = oderUser;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Integer getBookTotalCount() {
        return bookTotalCount;
    }

    public void setBookTotalCount(Integer bookTotalCount) {
        this.bookTotalCount = bookTotalCount;
    }
}
