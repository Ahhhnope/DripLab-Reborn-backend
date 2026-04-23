package com.example.cafe.Controller;

import com.example.cafe.Entity.Drink.Size;
import com.example.cafe.Repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sizes")
public class SizeController {
    private final SizeRepository sizeRepository;

    @GetMapping
    public ResponseEntity<List<Size>> getAllSizes() {
        return ResponseEntity.ok(sizeRepository.findAll());
    }
}
