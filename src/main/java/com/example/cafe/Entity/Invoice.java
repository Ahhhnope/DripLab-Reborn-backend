package com.example.cafe.Entity;

import com.example.cafe.Entity.Order.Order;
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

    @Column(name = "invoice_number")
    private Integer invoiceNumber;

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;



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

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "receipt_type")
    private String receiptType;


    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
