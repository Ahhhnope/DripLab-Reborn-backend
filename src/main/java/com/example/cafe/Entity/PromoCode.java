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

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private String category;          // "PHẦN TRĂM" | "TRỪ TIỀN"

    private BigDecimal value;

    private Integer quantity;

    @Column(name = "min_order_value", nullable = false,
            columnDefinition = "DECIMAL(18,2) DEFAULT 0")
    private BigDecimal minOrderValue = BigDecimal.ZERO;

    /**
     * "trên web"   → hiện ở trang lưu mã (UserVoucher)
     * "đổi thưởng" → hiện ở trang đổi điểm (UserPoints)
     */
    @Column(name = "display_location", nullable = false,
            columnDefinition = "NVARCHAR(20) DEFAULT N'trên web'")
    private String displayLocation = "trên web";

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private Boolean status;


}