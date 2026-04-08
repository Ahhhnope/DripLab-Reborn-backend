package com.example.cafe.Controller;

import com.example.cafe.Entity.PromoCode;
import com.example.cafe.Repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promo-codes")
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoCodeRepository promoCodeRepository;

    // GET /api/promo-codes — Lấy tất cả voucher
    @GetMapping
    public ResponseEntity<List<PromoCode>> getAll() {
        return ResponseEntity.ok(promoCodeRepository.findAll());
    }

    // GET /api/promo-codes/{id} — Lấy 1 voucher theo id
    @GetMapping("/{id}")
    public ResponseEntity<PromoCode> getById(@PathVariable Integer id) {
        return promoCodeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active/{code}")
    public ResponseEntity<PromoCode> getByCode(@PathVariable String code) {
        return promoCodeRepository.findAll().stream().filter(pc ->
                code.equals(pc.getCode())
                && pc.getStatus()
                && LocalDateTime.now().isAfter(pc.getStartDate())
                && LocalDateTime.now().isBefore(pc.getEndDate())
        ).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // POST /api/promo-codes/add — Thêm voucher mới
    @PostMapping("/add")
    public ResponseEntity<PromoCode> add(@RequestBody PromoCode promoCode) {
        if (promoCode.getCreatedAt() == null) {
            promoCode.setCreatedAt(LocalDateTime.now());
        }
        PromoCode saved = promoCodeRepository.save(promoCode);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/promo-codes/update/{id} — Cập nhật voucher
    @PutMapping("/update/{id}")
    public ResponseEntity<PromoCode> update(@PathVariable Integer id,
                                            @RequestBody PromoCode body) {
        return promoCodeRepository.findById(id).map(existing -> {
            existing.setCode(body.getCode());
            existing.setName(body.getName());
            existing.setDescription(body.getDescription());
            existing.setCategory(body.getCategory());
            existing.setValue(body.getValue());
            existing.setStartDate(body.getStartDate());
            existing.setEndDate(body.getEndDate());
            existing.setStatus(body.getStatus());
            return ResponseEntity.ok(promoCodeRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/promo-codes/remove/{id} — Xóa voucher
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!promoCodeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        promoCodeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}