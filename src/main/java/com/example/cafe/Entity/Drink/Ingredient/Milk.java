package com.example.cafe.Entity.Drink.Ingredient;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "milks")
@Data
public class Milk extends BaseIngredient{
}
