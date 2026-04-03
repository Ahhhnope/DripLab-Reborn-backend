package com.example.cafe.Repository.Drink;

import com.example.cafe.Entity.Drink.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {
    List<Drink> findAllByActiveTrue();
}
