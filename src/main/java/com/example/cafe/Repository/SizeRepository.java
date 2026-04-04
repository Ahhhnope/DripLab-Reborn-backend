package com.example.cafe.Repository;

import com.example.cafe.Entity.Drink.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}
