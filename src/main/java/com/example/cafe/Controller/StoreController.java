package com.example.cafe.Controller;

import com.example.cafe.Entity.Store;
import com.example.cafe.Entity.StoreReview;
import com.example.cafe.Repository.StoreRepository;
import com.example.cafe.Repository.StoreReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreRepository storeRepository;
    private final StoreReviewRepository storeReviewRepository;

    // GET /api/stores — lấy tất cả cửa hàng
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return new ResponseEntity<>(storeRepository.findAll(), HttpStatus.OK);
    }

    // GET /api/stores/{id} — lấy thông tin 1 cửa hàng
    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable Integer id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy cửa hàng.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(store.get(), HttpStatus.OK);
    }

    // GET /api/stores/{id}/reviews — lấy reviews + rating tổng hợp
    @GetMapping("/{id}/reviews")
    public ResponseEntity<Map<String, Object>> getStoreReviews(@PathVariable Integer id) {
        List<StoreReview> reviews = storeReviewRepository.findByStoreIdOrderByReviewDateDesc(id);
        Double avgRating = storeReviewRepository.findAvgRatingByStoreId(id);
        Long total = storeReviewRepository.countByStoreId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("avg_rating", avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0);
        response.put("total", total);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST /api/stores/{id}/reviews — thêm review mới
    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> addReview(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        if (!storeRepository.existsById(id)) {
            return new ResponseEntity<>("Không tìm thấy cửa hàng.", HttpStatus.NOT_FOUND);
        }

        StoreReview review = new StoreReview();
        review.setStoreId(id);
        review.setStars((Integer) body.get("stars"));
        review.setReviewText((String) body.get("review_text"));
        review.setInitials("KH");
        review.setReviewerName("Khách hàng");
        review.setReviewDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());

        storeReviewRepository.save(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
}
