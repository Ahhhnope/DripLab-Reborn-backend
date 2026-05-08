package com.example.cafe.DTO;

import lombok.Data;

import java.util.List;

@Data
public class  CheckoutDTO {
    private List<Integer> cartItemIds;
    private String note;
    private String paymentMethod;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
}
