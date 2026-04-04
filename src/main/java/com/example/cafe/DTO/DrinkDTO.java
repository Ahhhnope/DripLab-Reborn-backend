package com.example.cafe.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DrinkDTO {
    private String name;
    private String category;
    private BigDecimal basePrice;
    private String description;
    private String imageUrl;
    private Integer coffeeBeanId;
    private Integer milkId;
    private Integer heavyCreamId;
    private Integer iceCreamId;
    private Integer instructionId;
}