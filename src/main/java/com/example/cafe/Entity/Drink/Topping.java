package com.example.cafe.Entity.Drink;


import com.example.cafe.Entity.Drink.Ingredient.BaseIngredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "toppings")
@Data
public class Topping extends BaseIngredient {
}
