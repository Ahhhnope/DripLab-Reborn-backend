package com.example.cafe.Controller;

import com.example.cafe.Entity.PromoCode;
import com.example.cafe.Repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promo-codes")
@RequiredArgsConstructor
public class PromoCodeController {
    private final PromoCodeRepository repo;

    // Lấy tất cả voucher
    @GetMapping
    public List<PromoCode> getAll() {
        return repo.findAll();
    }

    // Admin thêm voucher mới
    @PostMapping("/add")
    public PromoCode add(@RequestBody PromoCode p) {
        return repo.save(p);
    }

    // Admin sửa voucher
    @PutMapping("/update/{id}")
    public PromoCode update(@PathVariable Integer id, @RequestBody PromoCode p) {
        p.setId(id);
        return repo.save(p);
    }

    // Admin xóa voucher
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // User dùng voucher → quantity giảm 1, nếu = 0 thì tắt
    @PutMapping("/use/{id}")
    public ResponseEntity<Void> use(@PathVariable Integer id) {
        repo.findById(id).ifPresent(p -> {
            if (p.getQuantity() != null && p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);
                // Null-safe cho usedCount
                p.setUsedCount(p.getUsedCount() == null ? 1 : p.getUsedCount() + 1);
            }
            if (p.getQuantity() != null && p.getQuantity() == 0) {
                p.setStatus(false);
            }
            repo.save(p);
        });
        return ResponseEntity.ok().build();
    }
}
