package com.yfh.book.service;

import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.UserBean;

public interface CartService {
    void addCartItem(CartItem cartItem);

    void updateCartItem(Integer cartItemId, Integer buyCount);

    void updateCartItem(CartItem cartItem);

    void updateOrAddCartItem(CartItem cartItem, Cart cart);

    Cart getCart(UserBean userBean);

    void delCart(CartItem cartItem);
}
