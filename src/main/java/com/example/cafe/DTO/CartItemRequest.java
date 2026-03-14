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
    private List<Integer> toppings;
}
