package com.example.cafe.Repository;

import com.example.cafe.Entity.CartItemTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemToppingsRepository extends JpaRepository<CartItemTopping, Integer> {
    void deleteByCartItemId(Integer cartItemId);
}
