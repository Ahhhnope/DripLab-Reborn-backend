package com.example.cafe.Repository;

import com.example.cafe.Entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    Optional<PromoCode> findByCode(String code);

    // Lấy mã theo display_location (dùng cho endpoint /web và /rewards)
    List<PromoCode> findByDisplayLocation(String displayLocation);

    @Modifying
    @Query("""
        UPDATE PromoCode p
        SET p.quantity = p.quantity - 1
        WHERE p.id = :id AND p.quantity > 0
    """)
    int decrementQuantity(@Param("id") Long id);
}
