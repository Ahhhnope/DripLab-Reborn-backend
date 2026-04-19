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

    // ════════════════════════════════════════════════════════
    //  ADMIN — CRUD
    // ════════════════════════════════════════════════════════

    /** GET /promo-codes — Lấy tất cả (dùng cho trang admin) */
    @GetMapping
    public ResponseEntity<List<PromoCodeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /** POST /promo-codes/add */
    @PostMapping("/add")
    public ResponseEntity<PromoCodeDTO> add(@RequestBody PromoCodeDTO dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    /** PUT /promo-codes/update/{id} */
    @PutMapping("/update/{id}")
    public ResponseEntity<PromoCodeDTO> update(@PathVariable Long id,
                                               @RequestBody PromoCodeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** DELETE /promo-codes/remove/{id} */
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ════════════════════════════════════════════════════════
    //  USER — Lấy mã theo display_location
    // ════════════════════════════════════════════════════════

    /**
     * GET /promo-codes/web
     * → Trả về mã có display_location = "trên web"
     *   (dùng cho trang UserVoucher — khách lưu mã)
     */
    @GetMapping("/web")
    public ResponseEntity<List<PromoCodeDTO>> getWebPromos() {
        return ResponseEntity.ok(service.getWebPromos());
    }

    /**
     * GET /promo-codes/rewards
     * → Trả về mã có display_location = "đổi thưởng"
     *   (dùng cho trang UserPoints — đổi điểm lấy voucher)
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<PromoCodeDTO>> getRewardPromos() {
        return ResponseEntity.ok(service.getRewardPromos());
    }

    // ════════════════════════════════════════════════════════
    //  USER — Lưu / Đổi mã vào kho
    // ════════════════════════════════════════════════════════

    /**
     * POST /promo-codes/{promoCodeId}/save?userId=3
     * → Khách bấm "Lưu mã" hoặc "Đổi ngay"
     *   Trừ quantity, ghi vào user_promo_codes
     */
    @PostMapping("/{promoCodeId}/save")
    public ResponseEntity<Map<String, String>> saveForUser(
            @PathVariable Long promoCodeId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(
                Map.of("message", service.savePromoForUser(userId, promoCodeId))
        );
    }

    /**
     * GET /promo-codes/my-promos?userId=3
     * → Kho mã đã lưu của user (chưa dùng)
     */
    @GetMapping("/my-promos")
    public ResponseEntity<List<PromoCodeDTO>> getMyPromos(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getUserSavedPromos(userId));
    }

    // ════════════════════════════════════════════════════════
    //  CHECKOUT — Validate + áp mã
    // ════════════════════════════════════════════════════════

    /**
     * POST /promo-codes/validate
     * Body: { "userId": 3, "code": "VOUCHER10", "orderTotal": 150000 }
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(
            @RequestBody Map<String, Object> body) {

        Long       userId = Long.valueOf(body.get("userId").toString());
        String     code   = body.get("code").toString();
        BigDecimal total  = new BigDecimal(body.get("orderTotal").toString());

        PromoService.ValidateResult result =
                service.validateAndApply(userId, code, total);

        return ResponseEntity.ok(Map.of(
                "discount",   result.discount(),
                "finalPrice", result.finalPrice(),
                "message",    "Áp mã thành công!"
        ));
    }

    // ════════════════════════════════════════════════════════
    //  Global exception handler
    // ════════════════════════════════════════════════════════

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleError(RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
    }
}