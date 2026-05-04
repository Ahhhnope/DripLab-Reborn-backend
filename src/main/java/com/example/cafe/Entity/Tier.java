package com.example.cafe.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@CafeTable(name = "tiers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "nvarchar(50)")
    private String name;

    private Integer minPoint;

    @OneToMany(mappedBy = "tier", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<User> users;
}
