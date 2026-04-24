package com.example.cafe.Repository;

import com.example.cafe.Entity.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreReviewRepository extends JpaRepository<StoreReview,Integer> {
    List<StoreReview> findByStoreIdOrderByReviewDateDesc(Integer storeId);

    @Query("SELECT AVG(r.stars) FROM StoreReview r WHERE r.storeId = :storeId")
    Double findAvgRatingByStoreId(@Param("storeId") Integer storeId);

    Long countByStoreId(Integer storeId);
}
