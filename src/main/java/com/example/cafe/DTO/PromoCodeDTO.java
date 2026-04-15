package com.example.cafe.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromoCodeDTO {
    private Long id;
    private String code;
    private String name;
    private String category;
    private BigDecimal value;
    private Integer quantity;
    private BigDecimal minOrderValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;
}