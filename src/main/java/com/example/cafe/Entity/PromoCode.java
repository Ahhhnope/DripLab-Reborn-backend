package com.example.cafe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String code;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(columnDefinition = "nvarchar(255)")
    private String category;   // "PHẦN TRĂM" hoặc "TRỪ TIỀN"

    private Float value;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean status;

    private LocalDateTime createdAt = LocalDateTime.now();
}