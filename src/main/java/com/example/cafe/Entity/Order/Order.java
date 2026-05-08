package com.example.cafe.Entity.Order;

import com.example.cafe.Entity.Customer;
import com.example.cafe.Entity.Invoice;
import com.example.cafe.Entity.User;
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

    @Column(columnDefinition = "nvarchar(255)")
    private String paymentMethod;


    private Float originalPrice;

    private Float discountAmount;

    private Float shippingFee;

    private Float taxAmount;

    private Float finalPrice;


    private String tableNumbers;

    @Column(columnDefinition = "nvarchar(255)")
    private String note;

    @Column(columnDefinition = "nvarchar(255)")
    private String status;

    @Column(columnDefinition = "nvarchar(50)")
    private String type;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Invoice invoice;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.paymentMethod == null) {
            this.paymentMethod = "Tiền mặt";
        }
    }
}
