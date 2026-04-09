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

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String category;

    private Float basePrice;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "image_url", columnDefinition = "nvarchar(255)")
    private String imageUrl;

    //deleting with so many foreign key is a pain in the ass so...
    //just don't delete it ;-;
    private Boolean active = true;

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
}
