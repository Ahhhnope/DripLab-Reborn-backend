package com.example.cafe.Entity.Order;
import com.example.cafe.Entity.Drink.Topping;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item_toppings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "topping_id", referencedColumnName = "id")
    private Topping topping;

    @Column(name = "base_price_at_purchase")
    private Float basePriceAtPurchase;
}
