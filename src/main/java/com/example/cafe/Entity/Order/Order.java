package com.example.cafe.Entity.Order;

import com.example.cafe.Entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "orders")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "original_price")
    private Float originalPrice;

    @Column(name = "discount_amount")
    private Float discountAmount;

    @Column(name = "shipping_fee")
    private Float shippingFee;

    @Column(name = "tax_amount")
    private Float taxAmount;

    @Column(name = "final_price")
    private Float finalPrice;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

}
