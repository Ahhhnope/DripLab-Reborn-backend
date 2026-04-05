package com.example.cafe.Service;

import com.example.cafe.Entity.Order.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getMyOrders(Integer userId);
    Order createOrder(Integer userID, String note);
    Order getOrderById(Integer orderID);
    Order updateOrderStatus(Integer orderId, String status);
    void deleteOrder(Integer orderId);
}
