package com.example.cafe.Repository;

import com.example.cafe.Entity.MomoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomoUserRepository extends JpaRepository<MomoUser, Integer> {
}
