package com.example.cafe.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(nullable = false, columnDefinition = "NVARCHAR(100)")
    private String code;

    @Column(nullable = false, columnDefinition = "NVARCHAR(300)")
    private String address;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(300)")
    @JsonProperty("image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(name = "open_time", columnDefinition = "NVARCHAR(5)")
    @JsonProperty("open_time")
    private String openTime;

    @Column(name = "close_time", columnDefinition = "NVARCHAR(5)")
    @JsonProperty("close_time")
    private String closeTime;

    @Column(name = "maps_url", columnDefinition = "NVARCHAR(500)")
    @JsonProperty("maps_url")
    private String mapsUrl;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String amenities;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;


}
