package com.example.cafe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String code;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(columnDefinition = "nvarchar(255)")
    private String category;

    @Column(name = "min_order_value", nullable = false)

    private BigDecimal minOrderValue = BigDecimal.ZERO;

    private BigDecimal value;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean status;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Integer quantity;


}