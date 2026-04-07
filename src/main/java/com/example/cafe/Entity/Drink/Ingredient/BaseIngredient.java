package com.example.cafe.Entity.Drink.Ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private Float price;

    private Integer quantity;

    private LocalDate createdAt = LocalDate.now();
}
