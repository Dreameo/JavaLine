package com.yfh.book.controller;

import com.yfh.book.pojo.OrderBean;
import com.yfh.book.pojo.UserBean;
import com.yfh.book.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController {
    private OrderService orderService;
    public String index(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        List<OrderBean> orderList = orderService.getOrderListByUser(user);
        user.setOrderBeanList(orderList);
        session.setAttribute("user", user);
        return "order/order";
    }


    public String checkout(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        OrderBean orderBean = new OrderBean() ;
        Date now = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String nowStr = sdf.format(now);
//        System.out.println(now.getTime());

        orderBean.setOrderNo(UUID.randomUUID().toString() );
        orderBean.setOrderDate(now);
        orderBean.setOrderUser(user);


        orderBean.setOrderMoney(user.getCart().getSumMoney());
        orderBean.setOrderStatus(0);

        orderService.addOrderBean(orderBean);
        user.setCart(null);

        return "redirect:book.do" ;
    }

    /**
     * 订单发货
     * @param orderId
     * @return
     */
    public String sendOrder(Integer orderId){

        return "redirect:order.do";
    }
}
