package com.example.cafe.Entity;

import com.example.cafe.Entity.Order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CafeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tableNumber;

    @Column(columnDefinition = "nvarchar(40)")
    private String status;

    @ManyToOne
    @JoinColumn(name = "current_order_id")
    private Order currentOrder;
}
