package com.example.cafe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Integer invoiceId;
    private Integer orderId;
    private String createdAt;
    private String paymentMethod;
    private String receiveType;
    private Float originalPrice;
    private Float discountAmount;
    private Float shippingFee;
    private Float finalPrice;
    private String status;
    private String note;

    private CustomerDTO customer;
    private List<OrderItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDTO {
        private Integer id;
        private String name;
        private String phone;
        private String email;
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private String name;
        private Integer qty;
        private Float price;       // tổng = unitPrice × qty
        private Float unitPrice;   // đơn giá 1 ly (basePriceAtPurchase)
        private String size;
        private Integer sugar;
        private Integer ice;
        private List<ToppingDTO> toppings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToppingDTO {
        private String name;
        private Float price;
    }
}