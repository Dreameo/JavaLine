package com.yfh.book.service.impl;

import com.yfh.book.dao.CartItemDAO;
import com.yfh.book.pojo.Book;
import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.BookService;
import com.yfh.book.service.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl implements CartService {
    CartItemDAO cartItemDAO;
    BookService bookService;

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDAO.addCart(cartItem);
    }

    @Override
    public void updateCartItem(Integer cartItemId, Integer buyCount) {
        cartItemDAO.updateCart(cartItemId, buyCount);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.update(cartItem);
    }


    @Override
    public void updateOrAddCartItem(CartItem cartItem, Cart cart) {
        if(cart != null) {
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if(cartItemMap == null) {
                cartItemMap = new HashMap<>();
            }

            if(cartItemMap.containsKey(cartItem.getBook().getId())) {
                // 先获取购物车中购物项的数量，然后进行+1操作
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount() + 1);
                updateCartItem(cartItemTemp);

            } else {
                addCartItem(cartItem);
            }

        } else {
            addCartItem(cartItem);
        }
    }

    @Override
    public Cart getCart(UserBean userBean) {
        List<CartItem> cartItemList = cartItemDAO.getCartItemList(userBean);
        Cart cart = new Cart();

        if (cartItemList.size() > 0) {
            /**
             * 循环购物车项
             */
            Map<Integer, CartItem> cartItemMap = new HashMap<>();
            for (CartItem cartItem : cartItemList) {
                Book book = bookService.getBookById(cartItem.getBook().getId());
                cartItem.setBook(book);
                Double xj = cartItem.getXj();
                cartItemMap.put(cartItem.getBook().getId(), cartItem);
            }
            cart.setCartItemMap(cartItemMap);
        }

        return cart;
    }

    @Override
    public void delCart(CartItem cartItem) {
       cartItemDAO.delCartItem(cartItem);
    }
}
