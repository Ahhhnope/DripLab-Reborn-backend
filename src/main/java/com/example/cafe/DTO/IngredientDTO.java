package com.example.cafe.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IngredientDTO {
    private Integer id;
    private String name;
    private Float price;
    private Integer quantity;
    private LocalDate createdAt = LocalDate.now();
}
