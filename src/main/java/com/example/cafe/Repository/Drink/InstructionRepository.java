package com.example.cafe.Repository.Drink;

import com.example.cafe.Entity.Drink.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
}
