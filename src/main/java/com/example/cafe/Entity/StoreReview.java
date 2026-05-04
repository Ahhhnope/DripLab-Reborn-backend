package com.example.cafe.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_reviews")
@Getter
@Setter
public class StoreReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "store_id", nullable = false)
    @JsonProperty("store_id")
    private Integer storeId;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Integer userId;

    @Column(nullable = false, columnDefinition = "NVARCHAR(5)")
    private String initials;

    @Column(name = "reviewer_name", nullable = false, columnDefinition = "NVARCHAR(100)")
    @JsonProperty("reviewer_name")
    private String reviewerName;

    @Column(nullable = false)
    private Integer stars;

    @Column(name = "review_text", nullable = false, columnDefinition = "NVARCHAR(1000)")
    @JsonProperty("review_text")
    private String reviewText;

    @Column(name = "review_date")
    @JsonProperty("review_date")
    private LocalDateTime reviewDate;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
