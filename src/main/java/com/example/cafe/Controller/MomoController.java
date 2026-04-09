package com.example.cafe.Controller;

import com.example.cafe.Entity.MomoUser;
import com.example.cafe.Repository.MomoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/momo")
public class MomoController {
    private final MomoRepository momoRepository;

    @GetMapping("/lookup/{phone}")
    public ResponseEntity<MomoUser> lookUpUser(@PathVariable String phone) {
        return momoRepository.findByPhone(phone).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
