package com.example.cafe.Repository;

import com.example.cafe.Entity.CartItemTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemToppingsRepository extends JpaRepository<CartItemTopping, Integer> {
    List<CartItemTopping> findByCartItemId(Integer id);
    void deleteByCartItemId(Integer cartItemId);
}
