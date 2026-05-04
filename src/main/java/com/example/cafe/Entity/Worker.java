package com.example.cafe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@CafeTable(name = "workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;

    @Column(columnDefinition = "nvarchar(255)")
    private String account;

    @Column(columnDefinition = "nvarchar(255)")
    private String password;

    private LocalDate createdAt = LocalDate.now();
}
