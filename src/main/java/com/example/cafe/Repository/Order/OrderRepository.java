package com.example.cafe.Repository.Order;

import com.example.cafe.Entity.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT SUM(o.finalPrice) FROM Order o WHERE o.status != 'Đã huỷ'")
    Double getActualRevenue();

    @Query("SELECT COUNT(o) FROM Order o")
    Long getTotalOrdersCount();

    @Query("SELECT COUNT(DISTINCT o.customer.id) FROM Order o")
    Long getTotalUniqueCustomers();

    @Query(value = "SELECT CAST(order_date AS DATE) as day, SUM(final_price) " +
            "FROM orders " +
            "WHERE order_date >= DATEADD(day, -30, GETDATE()) " +
            "AND status != N'Đã huỷ' " +
            "GROUP BY CAST(order_date AS DATE) " +
            "ORDER BY day ASC", nativeQuery = true)
    List<Object[]> getRevenueByDay();


}
