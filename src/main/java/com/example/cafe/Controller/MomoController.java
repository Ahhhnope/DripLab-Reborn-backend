package com.example.cafe.Controller;

import com.example.cafe.DTO.MomoRequestDTO;
import com.example.cafe.DTO.MomoResponseDTO;
import com.example.cafe.Service.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/momo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MomoController {
    private final MomoService momoService;

    @PostMapping("/atm-pay")
    public ResponseEntity<MomoResponseDTO> atmPay(@RequestBody MomoRequestDTO request) {
        MomoResponseDTO result = momoService.processAtmPayment(request);
        return ResponseEntity.ok(result);
    }
}
