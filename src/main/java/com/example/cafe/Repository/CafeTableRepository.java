package com.example.cafe.Repository;

import com.example.cafe.Entity.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CafeTableRepository extends JpaRepository<CafeTable, Integer> {
    List<CafeTable> findAllByCurrentOrder_Id(int id);
}
