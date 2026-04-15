package com.example.cafe.Controller;

import com.example.cafe.DTO.PromoCodeDTO;
import com.example.cafe.Service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promo-codes")
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoService service;

    // GET /api/promo-codes
    @GetMapping
    public ResponseEntity<List<PromoCodeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/promo-codes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeDTO> getById(@PathVariable Long id) {
        return service.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/promo-codes/active/{code}
    @GetMapping("/active/{code}")
    public ResponseEntity<PromoCodeDTO> getByCode(@PathVariable String code) {
        return service.getAll().stream()
                .filter(p -> code.equals(p.getCode())
                        && Boolean.TRUE.equals(p.getStatus())
                        && p.getStartDate() != null
                        && p.getEndDate() != null
                        && LocalDateTime.now().isAfter(p.getStartDate())
                        && LocalDateTime.now().isBefore(p.getEndDate()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/promo-codes/add
    @PostMapping("/add")
    public ResponseEntity<PromoCodeDTO> add(@RequestBody PromoCodeDTO dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    // PUT /api/promo-codes/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<PromoCodeDTO> update(@PathVariable Long id,
                                               @RequestBody PromoCodeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE /api/promo-codes/remove/{id}
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/promo-codes/{promoCodeId}/save?userId=3
    @PostMapping("/{promoCodeId}/save")
    public ResponseEntity<Map<String, String>> saveForUser(
            @PathVariable Long promoCodeId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(
                Map.of("message", service.savePromoForUser(userId, promoCodeId))
        );
    }

    // GET /api/promo-codes/my-promos?userId=3
    @GetMapping("/my-promos")
    public ResponseEntity<List<PromoCodeDTO>> getMyPromos(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getUserSavedPromos(userId));
    }

    // POST /api/promo-codes/validate
    // Body: { "userId":3, "code":"VOUCHER10", "orderTotal":150000 }
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(
            @RequestBody Map<String, Object> body) {
        Long userId      = Long.valueOf(body.get("userId").toString());
        String code      = body.get("code").toString();
        BigDecimal total = new BigDecimal(body.get("orderTotal").toString());

        PromoService.ValidateResult result = service.validateAndApply(userId, code, total);

        return ResponseEntity.ok(Map.of(
                "discount",   result.discount(),
                "finalPrice", result.finalPrice(),
                "message",    "Áp mã thành công!"
        ));
    }

    // Global exception handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleError(RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
    }
}