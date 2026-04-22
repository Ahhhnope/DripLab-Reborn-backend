package com.example.cafe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MomoResponseDTO {
    /** true = thành công */
    private boolean success;

    /** Mã đơn hàng */
    private String orderId;

    /** Mã giao dịch nội bộ (TX + UUID ngắn) */
    private String transactionId;

    /** Số tiền đã thanh toán */
    private Long amount;

    /** Thông báo hiển thị cho user */
    private String message;

    /**
     * Mã lỗi khi thất bại:
     *   CARD_NOT_FOUND       — Không tìm thấy thẻ
     *   HOLDER_MISMATCH      — Tên không khớp
     *   EXPIRED              — Ngày hết hạn sai/đã hết
     *   CARD_BLOCKED         — Thẻ bị khoá
     *   INSUFFICIENT_FUNDS   — Không đủ tiền
     *   LIMIT_EXCEEDED       — Đã đạt hạn mức
     */
    private String errorCode;

    /* ── Factory methods ── */

    public static MomoResponseDTO ok(String orderId, String txId, Long amount) {
        return MomoResponseDTO.builder()
                .success(true)
                .orderId(orderId)
                .transactionId(txId)
                .amount(amount)
                .message("Thanh toán thành công")
                .build();
    }

    public static MomoResponseDTO fail(String orderId, String code, String msg) {
        return MomoResponseDTO.builder()
                .success(false)
                .orderId(orderId)
                .errorCode(code)
                .message(msg)
                .build();
    }
}
