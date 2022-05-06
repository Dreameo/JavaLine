package com.yfh.book.controller;

import com.google.gson.Gson;
import com.yfh.book.pojo.Book;
import com.yfh.book.pojo.Cart;
import com.yfh.book.pojo.CartItem;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.CartService;

import javax.servlet.http.HttpSession;

public class CartController {
    private CartService cartService;


    public String index(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        Cart cart = cartService.getCart(user);
        user.setCart(cart);
        session.setAttribute("user", user);
        return "cart/cart";
    }

    public String editCart(HttpSession session, Integer cartItemId, Integer buyCount) {
        cartService.updateCartItem(new CartItem(cartItemId, buyCount));
        return "redirect: cart.do";
    }


    /**
     * 将指定图书加入购物车，
     * 1. 如果当前用户的购物车已经存在这个图书，那么将这本图书的数量+1
     * 2. 否则在我们购物车中新增这样一本图书，数量为1
     * @param bookId
     * @return
     */
    public String addCart(Integer bookId, HttpSession session) {
        UserBean userBean = (UserBean) session.getAttribute("user");
        Book addBook = new Book(bookId);
        CartItem cartItem = new CartItem(0, addBook, 1, userBean);
        cartService.updateOrAddCartItem(cartItem, userBean.getCart());
        Cart cart = cartService.getCart(userBean);
        userBean.setCart(cart);
        session.setAttribute("user", userBean);
        return "redirect:book.do";
    }

    public String cartInfo(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        Cart cart = cartService.getCart(user);
//        user.setCart(cart);

        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart); // 将java对象转为json字符串
        return "json:" + cartJsonStr;
    }

}

//
//    @Test
//    public void test1() {
//        Integer[] arr = {4, 5, 5, 6, 3, 6};
//        Arrays.sort(arr, (a, b) -> {
////            System.out.println("a = " + a); // a:5
////            System.out.println("b = " + b);
//            return a - b;
//        });
//        /** a为后一个
//         * a = 5
//         * b = 4
//         * a = 5
//         * b = 5
//         * a = 6
//         * b = 5
//         * a = 3
//         */
//
//        for (Integer integer : arr) {
//            System.out.println("integer = " + integer);
//        }
//    }
