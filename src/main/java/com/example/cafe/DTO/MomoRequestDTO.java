package com.example.cafe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoRequestDTO {
    /** Mã đơn hàng do frontend sinh (MOMO + timestamp + random) */
    private String orderId;

    /** Số tiền cần thanh toán (VND) */
    private Long amount;

    /** Số thẻ ATM (chỉ chữ số, không dấu cách) */
    private String cardNumber;

    /** Ngày hết hạn thẻ — định dạng MM/YY */
    private String cardExpiry;

    /** Tên chủ thẻ (IN HOA, không dấu) */
    private String cardHolder;

    /** Số điện thoại liên kết thẻ (tuỳ chọn, có thể null) */
    private String cardPhone;

    /** Mô tả đơn hàng */
    private String orderInfo;
}
