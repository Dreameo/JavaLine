package com.yfh.book.dao.impl;

import com.yfh.book.dao.CartItemDAO;
import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.UserBean;
import com.yfh.myssm.basedao.BaseDAO;

import java.util.List;
import java.util.Map;

public class CartItemDAOImpl extends BaseDAO<CartItem> implements CartItemDAO {
    @Override
    public void updateCart(Integer cartItemId, Integer buyCount) {
        super.executeUpdate("UPDATE t_cart_item SET buyCount = ? WHERE id = ?", buyCount, cartItemId);
    }
    @Override
    public void update(CartItem cartItem) {
        super.executeUpdate("UPDATE t_cart_item SET buyCount = ? WHERE id = ?", cartItem.getBuyCount(), cartItem.getId());
    }


    @Override
    public void addCart(CartItem cartItem) {
        super.executeUpdate("INSERT INTO t_cart_item(book, buyCount, userBean) VALUES(?, ?, ?)", cartItem.getBook().getId(), cartItem.getBuyCount(), cartItem.getUserBean().getId());
    }

    @Override
    public List<CartItem> getCartItemList(UserBean userBean) {
        return super.executeQuery("SELECT * FROM t_cart_item WHERE userBean = ?", userBean.getId());
    }

    @Override
    public void delCartItem(CartItem cartItem) {
        super.executeUpdate("DELETE FROM t_cart_item WHERE id = ?", cartItem.getId());
    }


}
