package com.example.cafe.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "maps_url")
    private String mapsUrl;

    @Column(name = "amenities")
    private String amenities;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
