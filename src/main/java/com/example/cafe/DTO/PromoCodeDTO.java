package com.example.cafe.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromoCodeDTO {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String category;
    private Float value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;
    private LocalDateTime createdAt;
}
