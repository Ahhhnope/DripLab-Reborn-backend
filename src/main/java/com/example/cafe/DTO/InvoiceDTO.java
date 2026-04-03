package com.example.cafe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Integer invoiceId;    // Maps to invoice.invoice_id in Vue
    private Integer orderId;      // Maps to invoice.order_id
    private String date;          // Formatted string
    private String paymentMethod;
    private String receiveType;
    private Double finalPrice;

    // Nested object for the "Customer Info" box in your Vue sidebar
    private CustomerDTO customer;

    @Data
    public static class CustomerDTO {
        private Integer id;
        private String name;
        private String phone;
        private String email;
        private String address;
    }
}
