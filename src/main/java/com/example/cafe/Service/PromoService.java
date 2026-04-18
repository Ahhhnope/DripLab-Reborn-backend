package com.example.cafe.Service;

import com.example.cafe.DTO.PromoCodeDTO;
import com.example.cafe.Entity.PromoCode;
import com.example.cafe.Entity.UserPromoCode;
import com.example.cafe.Repository.PromoCodeRepository;
import com.example.cafe.Repository.UserPromoCodeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromoService {

    private final PromoCodeRepository     promoRepo;
    private final UserPromoCodeRepository userPromoRepo;

    // ════════════════════════════════════════════════════════
    //  ADMIN — CRUD
    // ════════════════════════════════════════════════════════

    @Transactional(readOnly = true)
    public List<PromoCodeDTO> getAll() {
        return promoRepo.findAll().stream().map(this::toDTO).toList();
    }

    public PromoCodeDTO add(PromoCodeDTO dto) {
        PromoCode p = new PromoCode();
        p.setCode(dto.getCode().toUpperCase().trim());
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setValue(dto.getValue());
        p.setQuantity(dto.getQuantity());
        p.setMinOrderValue(dto.getMinOrderValue() != null
                ? dto.getMinOrderValue() : BigDecimal.ZERO);
        p.setDisplayLocation(dto.getDisplayLocation() != null
                ? dto.getDisplayLocation() : "trên web");
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(true);
        return toDTO(promoRepo.save(p));
    }

    public PromoCodeDTO update(Long id, PromoCodeDTO dto) {
        PromoCode p = promoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher id=" + id));

        p.setCode(dto.getCode().toUpperCase().trim());
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setValue(dto.getValue());
        p.setQuantity(dto.getQuantity());
        p.setMinOrderValue(dto.getMinOrderValue() != null
                ? dto.getMinOrderValue() : BigDecimal.ZERO);
        p.setDisplayLocation(dto.getDisplayLocation() != null
                ? dto.getDisplayLocation() : "trên web");
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(dto.getStatus());
        return toDTO(promoRepo.save(p));
    }

    public void delete(Long id) {
        promoRepo.deleteById(id);
    }

    // ════════════════════════════════════════════════════════
    //  USER — Lấy mã theo display_location
    // ════════════════════════════════════════════════════════

    /**
     * Trả về mã cho trang "Lưu mã" của user (display_location = 'trên web')
     * Chỉ lấy mã còn hiệu lực, còn số lượng
     */
    @Transactional(readOnly = true)
    public List<PromoCodeDTO> getWebPromos() {
        return promoRepo.findByDisplayLocation("trên web").stream()
                .filter(this::isActive)
                .map(this::toDTO)
                .toList();
    }

    /**
     * Trả về mã cho trang "Đổi thưởng" của user (display_location = 'đổi thưởng')
     * Chỉ lấy mã còn hiệu lực, còn số lượng
     */
    @Transactional(readOnly = true)
    public List<PromoCodeDTO> getRewardPromos() {
        return promoRepo.findByDisplayLocation("đổi thưởng").stream()
                .filter(this::isActive)
                .map(this::toDTO)
                .toList();
    }

    // ════════════════════════════════════════════════════════
    //  USER — Lưu / Đổi mã vào kho
    // ════════════════════════════════════════════════════════

    /**
     * Khách bấm "Lưu mã" (trên web) hoặc "Đổi ngay" (đổi thưởng)
     * → ghi vào user_promo_codes, trừ quantity
     */
    public String savePromoForUser(Long userId, Long promoCodeId) {
        PromoCode promo = promoRepo.findById(promoCodeId)
                .orElseThrow(() -> new RuntimeException("Mã không tồn tại"));

        if (Boolean.FALSE.equals(promo.getStatus()) || promo.getQuantity() <= 0)
            throw new RuntimeException("Mã đã hết hoặc không còn hoạt động");

        if (promo.getEndDate() != null && promo.getEndDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Mã đã hết hạn sử dụng");

        if (userPromoRepo.existsByUserIdAndPromoCodeId(userId, promoCodeId))
            throw new RuntimeException("Bạn đã lưu mã này rồi");

        // Trừ số lượng atomic
        int affected = promoRepo.decrementQuantity(promoCodeId);
        if (affected == 0)
            throw new RuntimeException("Mã vừa hết số lượng, vui lòng thử mã khác");

        UserPromoCode upc = new UserPromoCode();
        upc.setUserId(userId);
        upc.setPromoCode(promo);
        upc.setSavedAt(LocalDateTime.now());
        upc.setIsUsed(false);
        userPromoRepo.save(upc);

        return "Lưu mã thành công!";
    }

    // ════════════════════════════════════════════════════════
    //  USER — Kho mã đã lưu
    // ════════════════════════════════════════════════════════

    @Transactional(readOnly = true)
    public List<PromoCodeDTO> getUserSavedPromos(Long userId) {
        return userPromoRepo.findByUserIdAndIsUsedFalse(userId)
                .stream()
                .map(upc -> toDTO(upc.getPromoCode()))
                .toList();
    }

    // ════════════════════════════════════════════════════════
    //  CHECKOUT — Validate + đánh dấu đã dùng
    // ════════════════════════════════════════════════════════

    public record ValidateResult(BigDecimal discount, BigDecimal finalPrice) {}

    public ValidateResult validateAndApply(Long userId, String code, BigDecimal orderTotal) {
        PromoCode promo = promoRepo.findByCode(code.toUpperCase().trim())
                .orElseThrow(() -> new RuntimeException("Mã không tồn tại"));

        UserPromoCode upc = userPromoRepo
                .findByUserIdAndPromoCodeId(userId, promo.getId())
                .orElseThrow(() -> new RuntimeException("Bạn chưa lưu mã này vào kho"));

        if (Boolean.TRUE.equals(upc.getIsUsed()))
            throw new RuntimeException("Mã đã được sử dụng trước đó");

        if (promo.getEndDate() != null && promo.getEndDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Mã đã hết hạn sử dụng");

        if (promo.getMinOrderValue() != null
                && orderTotal.compareTo(promo.getMinOrderValue()) < 0) {
            throw new RuntimeException(
                    "Đơn hàng tối thiểu "
                            + String.format("%,.0f", promo.getMinOrderValue())
                            + "đ mới được dùng mã này"
            );
        }

        // Đánh dấu đã dùng
        upc.setIsUsed(true);
        upc.setUsedAt(LocalDateTime.now());
        userPromoRepo.save(upc);

        // Tính giảm giá
        BigDecimal discount = "PHẦN TRĂM".equals(promo.getCategory())
                ? orderTotal.multiply(promo.getValue()).divide(BigDecimal.valueOf(100))
                : promo.getValue();

        BigDecimal finalPrice = orderTotal.subtract(discount).max(BigDecimal.ZERO);
        return new ValidateResult(discount, finalPrice);
    }

    // ════════════════════════════════════════════════════════
    //  Helper
    // ════════════════════════════════════════════════════════

    private boolean isActive(PromoCode p) {
        if (Boolean.FALSE.equals(p.getStatus())) return false;
        if (p.getQuantity() == null || p.getQuantity() <= 0) return false;
        if (p.getEndDate() != null && p.getEndDate().isBefore(LocalDateTime.now())) return false;
        return true;
    }

    private PromoCodeDTO toDTO(PromoCode p) {
        return new PromoCodeDTO(
                p.getId(),
                p.getCode(),
                p.getName(),
                p.getCategory(),
                p.getValue(),
                p.getQuantity(),
                p.getMinOrderValue(),
                p.getDisplayLocation(),
                p.getStartDate(),
                p.getEndDate(),
                p.getStatus()
        );
    }
}