package com.example.cafe.DTO;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer id;
    private String fullName;
    private Double loyaltyPoint;
    private String defaultAddress;
    private String phone;
    private String createdAt;
}
