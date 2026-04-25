package com.example.cafe.Repository;

import com.example.cafe.Entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiersRepository extends JpaRepository<Tier, Integer> {
}
