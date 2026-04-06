package com.example.cafe.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "momo_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;

    @Column(columnDefinition = "nvarchar(10)")
    private String phone;
}
