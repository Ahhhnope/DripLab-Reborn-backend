package com.example.cafe.Entity.Order;

import com.example.cafe.Entity.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer orderNumber;

    private LocalDateTime orderDate;

    @Column(columnDefinition = "nvarchar(255)")
    private String shippingAddress;


    private Float originalPrice;

    private Float discountAmount;

    private Float shippingFee;

    private Float taxAmount;

    private Float finalPrice;



    @Column(columnDefinition = "nvarchar(255)")
    private String note;

    @Column(columnDefinition = "nvarchar(255)")
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;
}
