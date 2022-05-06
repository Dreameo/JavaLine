package com.yfh.book.pojo;

import java.math.BigDecimal;

public class CartItem {
    private Integer id;
    private Book book; //  1:1 购物车项与图书项
    private Integer buyCount;
    private UserBean userBean;
    private BigDecimal xj; // 小计

    public CartItem() {
    }

    public CartItem(Integer id, Book book, Integer buyCount, UserBean userBean) {
        this.id = id;
        this.book = book;
        this.buyCount = buyCount;
        this.userBean = userBean;
    }

    public CartItem(Integer id) {
        this.id = id;
    }

    public CartItem(Integer cartItemId, Integer buyCount) {
        this.id = cartItemId;
        this.buyCount = buyCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public Double getXj() {
        BigDecimal buyCount = new BigDecimal(getBuyCount() + "");
        BigDecimal price = new BigDecimal(getBook().getPrice() + "");
        xj = buyCount.multiply(price);
        return xj.doubleValue();
    }
}
