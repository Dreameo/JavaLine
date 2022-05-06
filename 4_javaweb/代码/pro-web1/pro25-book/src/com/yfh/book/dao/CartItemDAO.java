package com.yfh.book.dao;

import com.yfh.book.pojo.Book;
import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.UserBean;

import java.util.List;

public interface CartItemDAO {
    void update(CartItem cartItem);

    void updateCart(Integer cartItemId, Integer buyCount);

    void addCart(CartItem cartItem);

    List<CartItem> getCartItemList(UserBean userBean);

    void delCartItem(CartItem cartItem);
}
