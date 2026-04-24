package com.example.cafe.Entity;

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
    private Integer storeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false)
    private String initials;

    @Column(name = "reviewer_name", nullable = false)
    private String reviewerName;

    @Column(nullable = false)
    private Integer stars;

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
