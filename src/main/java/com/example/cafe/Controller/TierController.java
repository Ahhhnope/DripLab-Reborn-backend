package com.example.cafe.Controller;

import com.example.cafe.Entity.Tier;
import com.example.cafe.Repository.TiersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
@RequiredArgsConstructor
public class TierController {
    private final TiersRepository tiersRepository;

    @GetMapping
    public ResponseEntity<List<Tier>> getAllTiers() {
        return new ResponseEntity<>(tiersRepository.findAll(), HttpStatus.OK);
    }
}
