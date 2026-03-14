package com.example.cafe.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    @JsonBackReference
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "topping_id", referencedColumnName = "id")
    private Topping topping;
}
