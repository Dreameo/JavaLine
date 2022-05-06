package com.yfh.book.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private Map<Integer, CartItem> cartItemMap; // 一个书的id对应一个购物车项
    private Integer goodsCount;
    private Double sumMoney;
    private Integer bookTotalCount;

    public Cart() {
    }

    public Cart(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Integer getGoodsCount() {
        if (cartItemMap != null && cartItemMap.size() > 0)
            return cartItemMap.size();
        return 0;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getSumMoney() {
        sumMoney = 0.0;
        if(cartItemMap != null && cartItemMap.size() > 0) {
            Set<Map.Entry<Integer, CartItem>> entries = cartItemMap.entrySet();
            for (Map.Entry<Integer, CartItem> entry : entries) {
                Integer buyCount = entry.getValue().getBuyCount();
                Double price = entry.getValue().getBook().getPrice();
                sumMoney += buyCount * price;
            }
        }

        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Integer getBookTotalCount() {
        bookTotalCount = 0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            for (CartItem value : cartItemMap.values()) {
               bookTotalCount += value.getBuyCount();
            }
        }
        return bookTotalCount;
    }

    public void setBookTotalCount(Integer bookTotalCount) {
        this.bookTotalCount = bookTotalCount;
    }
}
