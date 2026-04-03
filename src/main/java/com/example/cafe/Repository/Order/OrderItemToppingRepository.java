package com.example.cafe.Repository.Order;

import com.example.cafe.Entity.Order.OrderItemTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemToppingRepository extends JpaRepository<OrderItemTopping, Integer> {
}
