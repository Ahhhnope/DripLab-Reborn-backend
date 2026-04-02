package com.example.cafe.Entity.Drink.Ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "milks")
@Data
public class IceCream extends BaseIngredient{
}
