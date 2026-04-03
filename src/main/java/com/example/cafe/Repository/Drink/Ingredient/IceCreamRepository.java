package com.example.cafe.Repository.Drink.Ingredient;

import com.example.cafe.Entity.Drink.Ingredient.IceCream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IceCreamRepository extends JpaRepository<IceCream, Integer> {

}
