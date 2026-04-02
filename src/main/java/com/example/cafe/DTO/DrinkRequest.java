package com.example.cafe.DTO;

import lombok.Data;

@Data
public class DrinkRequest {
    private String name;
    private Float basePrice;
    private Integer instructionId;
    private Integer coffeeBeanId;
    private Integer milkId;
    private Integer heavyCreamId;
    private Integer iceCreamId;
}
