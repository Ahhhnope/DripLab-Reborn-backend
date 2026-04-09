package com.example.cafe.DTO;


import lombok.Data;

@Data
public class OrderUpdateDTO {
    private String status;
    private String note;
    private String paymentMethod; // optional
}