package com.example.cafe.Repository;

import com.example.cafe.Entity.UserPromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPromoCodeRepository
        extends JpaRepository<UserPromoCode, Long> {

    // Kiểm tra user đã lưu mã này chưa
    boolean existsByUserIdAndPromoCodeId(Long userId, Long promoCodeId);

    // Kho mã chưa dùng của user (dùng ở trang "Voucher của tôi")
    List<UserPromoCode> findByUserIdAndIsUsedFalse(Long userId);

    // Tìm 1 bản ghi cụ thể để đánh dấu đã dùng khi checkout
    Optional<UserPromoCode> findByUserIdAndPromoCodeId(Long userId, Long promoCodeId);
}
