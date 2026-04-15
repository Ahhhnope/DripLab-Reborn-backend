package com.example.cafe.Entity;

import com.example.cafe.Entity.Order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "invoices")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer invoiceNumber;

    private LocalDateTime invoiceDate;



    private Float originalPrice;

    private Float discountAmount;

    private Float shippingFee;

    private Float taxAmount;

    private Float finalPrice;

    @Column(columnDefinition = "nvarchar(255)")
    private String paymentMethod;

    @Column(columnDefinition = "nvarchar(255)")
    private String receiptType;


    private LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
