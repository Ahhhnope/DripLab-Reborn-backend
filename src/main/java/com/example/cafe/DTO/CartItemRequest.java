package com.example.cafe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Integer userId;
    private Integer drinkId;
    private Integer quantity;
    private Integer sizeId;
    private Integer ice;
    private Integer sugar;
    private List<Integer> toppings;

    private String base;
    private Integer beanId;
    private Integer milkId;
    private Boolean isCustom;

    //ze payload
}
