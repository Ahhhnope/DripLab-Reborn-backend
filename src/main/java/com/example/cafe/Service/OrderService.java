package com.example.cafe.Service;

import com.example.cafe.Entity.Order.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Integer userID, String note);
    Order getOrderById(Integer orderID);
    Order updateOrderStatus(Integer orderId, String status);
    void deleteOrder(Integer orderId);
}
