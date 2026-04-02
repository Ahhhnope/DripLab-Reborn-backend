package com.example.cafe.Entity.Drink;

import com.example.cafe.Entity.Drink.Ingredient.CoffeeBean;
import com.example.cafe.Entity.Drink.Ingredient.HeavyCream;
import com.example.cafe.Entity.Drink.Ingredient.IceCream;
import com.example.cafe.Entity.Drink.Ingredient.Milk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drinks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "base_price")
    private Float basePrice;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    //damn, that's a lot of keys...
    @ManyToOne
    @JoinColumn(name = "coffee_bean_id", referencedColumnName = "id")
    private CoffeeBean coffeeBean;

    @ManyToOne
    @JoinColumn(name = "milk_id", referencedColumnName = "id")
    private Milk milk;

    @ManyToOne
    @JoinColumn(name = "heavy_cream_id", referencedColumnName = "id")
    private HeavyCream heavyCream;

    @ManyToOne
    @JoinColumn(name = "ice_cream_id", referencedColumnName = "id")
    private IceCream iceCream;

    @ManyToOne
    @JoinColumn(name = "instruction_id", referencedColumnName = "id")
    private Instruction instruction;

    //deleting with so many foreign key is a pain in the ass so...
    //just don't delete it ;-;
    @Column(name = "active")
    private boolean active = true;
}
