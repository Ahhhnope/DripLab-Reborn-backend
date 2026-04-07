package com.example.cafe.Controller;

import com.example.cafe.Entity.PromoCode;
import com.example.cafe.Repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promo-codes")
@CrossOrigin(origins = "*")
public class PromoCodeController {

    @Autowired
    private PromoCodeRepository promoCodeRepository;

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
            existing.setQuantity(body.getQuantity());
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

    // PUT /api/promo-codes/use/{id} — Giảm quantity khi user copy mã
    @PutMapping("/use/{id}")
    public ResponseEntity<PromoCode> use(@PathVariable Integer id) {
        return promoCodeRepository.findById(id).map(v -> {
            if (v.getQuantity() != null && v.getQuantity() > 0) {
                v.setQuantity(v.getQuantity() - 1);
                // Nếu hết lượt thì tắt status
                if (v.getQuantity() == 0) {
                    v.setStatus(false);
                }
                promoCodeRepository.save(v);
            }
            return ResponseEntity.ok(v);
        }).orElse(ResponseEntity.notFound().build());
    }
}