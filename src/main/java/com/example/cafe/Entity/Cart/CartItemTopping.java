package com.example.cafe.Entity.Cart;


import com.example.cafe.Entity.Drink.Topping;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item_toppings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    @JsonBackReference
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topping_id", referencedColumnName = "id")
    private Topping topping;
}
