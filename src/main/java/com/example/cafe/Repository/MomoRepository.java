package com.example.cafe.Repository;

import com.example.cafe.Entity.MomoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MomoRepository extends JpaRepository<MomoUser, Integer> {
    Optional<MomoUser> findByPhone(String phone);
}
