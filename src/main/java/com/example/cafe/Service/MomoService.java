package com.example.cafe.Service;

import com.example.cafe.DTO.MomoRequestDTO;
import com.example.cafe.DTO.MomoResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MomoService {
    @PersistenceContext
    private EntityManager em;

    public MomoResponseDTO processAtmPayment(MomoRequestDTO req) {
        log.info("[MoMo ATM] orderId={} | amount={} | card={}****",
                req.getOrderId(), req.getAmount(),
                req.getCardNumber() != null
                        ? req.getCardNumber().substring(0, Math.min(4, req.getCardNumber().length()))
                        : "??");

        // 1. Validate số thẻ
        if (req.getCardNumber() == null || req.getCardNumber().isBlank()) {
            return MomoResponseDTO.fail(req.getOrderId(), "INVALID_INPUT", "Số thẻ không hợp lệ.");
        }

        // 2. Tìm thẻ trong DB
        List<Object[]> rows = em.createNativeQuery(
                "SELECT card_status, holder_name, expiry_date " +
                        "FROM test_atm_cards WHERE card_number = ?1"
        ).setParameter(1, req.getCardNumber()).getResultList();

        if (rows.isEmpty()) {
            log.warn("[MoMo ATM] Card not found: {}", req.getCardNumber());
            return MomoResponseDTO.fail(req.getOrderId(), "CARD_NOT_FOUND",
                    "Thẻ không tồn tại trong hệ thống. Vui lòng kiểm tra lại số thẻ.");
        }

        Object[] row        = rows.get(0);
        String cardStatus   = String.valueOf(row[0]).trim().toUpperCase();
        String dbHolder     = String.valueOf(row[1]).trim();
        String dbExpiry     = String.valueOf(row[2]).trim();  // MM/YY

        // 3. Kiểm tra tên chủ thẻ (ignore case, ignore khoảng trắng thừa)
        String inputHolder = req.getCardHolder() == null ? "" : req.getCardHolder().trim();
        if (!dbHolder.equalsIgnoreCase(inputHolder)) {
            log.warn("[MoMo ATM] Holder mismatch — expected='{}', got='{}'", dbHolder, inputHolder);
            return MomoResponseDTO.fail(req.getOrderId(), "HOLDER_MISMATCH",
                    "Tên chủ thẻ không khớp. Vui lòng kiểm tra lại.");
        }

        // 4. Kiểm tra ngày hết hạn
        String inputExpiry = req.getCardExpiry() == null ? "" : req.getCardExpiry().trim();
        if (!dbExpiry.equals(inputExpiry)) {
            log.warn("[MoMo ATM] Expiry mismatch — expected='{}', got='{}'", dbExpiry, inputExpiry);
            return MomoResponseDTO.fail(req.getOrderId(), "EXPIRED",
                    "Thẻ đã hết hạn hoặc ngày nhập không đúng.");
        }

        // 5. Xử lý theo trạng thái thẻ
        switch (cardStatus) {
            case "ACTIVE": {
                String txId = "TX" + UUID.randomUUID()
                        .toString().replace("-", "").substring(0, 12).toUpperCase();
                log.info("[MoMo ATM] SUCCESS — orderId={} | txId={}", req.getOrderId(), txId);
                return MomoResponseDTO.ok(req.getOrderId(), txId, req.getAmount());
            }
            case "BLOCKED":
                return MomoResponseDTO.fail(req.getOrderId(), "CARD_BLOCKED",
                        "Thẻ bị khoá. Vui lòng liên hệ ngân hàng phát hành.");

            case "INSUFFICIENT":
                return MomoResponseDTO.fail(req.getOrderId(), "INSUFFICIENT_FUNDS",
                        "Nguồn tiền không đủ để thực hiện thanh toán.");

            case "LIMIT":
                return MomoResponseDTO.fail(req.getOrderId(), "LIMIT_EXCEEDED",
                        "Thẻ đã đạt hạn mức giao dịch trong ngày.");

            default:
                log.error("[MoMo ATM] Unknown card status='{}' for card={}", cardStatus, req.getCardNumber());
                return MomoResponseDTO.fail(req.getOrderId(), "UNKNOWN",
                        "Đã xảy ra lỗi không xác định. Vui lòng thử lại sau.");
        }
    }
}
