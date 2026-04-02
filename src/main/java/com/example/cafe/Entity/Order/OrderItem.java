package com.example.cafe.Entity.Order;

import com.example.cafe.Entity.Drink.Drink;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    private Drink drink;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "base_price_at_purchase")
    private Float basePriceAtPurchase;
}
