package com.example.cafe.Repository;

import com.example.cafe.Entity.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeTableRepository extends JpaRepository<CafeTable, Integer> {
}
