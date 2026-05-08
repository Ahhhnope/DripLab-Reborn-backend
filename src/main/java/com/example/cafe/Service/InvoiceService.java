package com.example.cafe.Service;

import com.example.cafe.DTO.InvoiceDTO;
import com.example.cafe.Entity.Invoice;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Entity.Order.OrderItem;
import com.example.cafe.Entity.Order.OrderItemTopping;
import com.example.cafe.Repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + id));
        return toDTO(invoice);
    }

    private InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        Order order = invoice.getOrder();

        // --- Invoice fields ---
        dto.setInvoiceId(invoice.getId());
        dto.setOrderId(order != null ? order.getId() : null);
        dto.setCreatedAt(invoice.getInvoiceDate() != null
                ? invoice.getInvoiceDate().toString() : null);
        dto.setPaymentMethod(invoice.getPaymentMethod());
        dto.setReceiveType(invoice.getReceiptType());
        dto.setOriginalPrice(invoice.getOriginalPrice());
        dto.setDiscountAmount(invoice.getDiscountAmount());
        dto.setShippingFee(invoice.getShippingFee());
        dto.setFinalPrice(invoice.getFinalPrice());

        // --- note + status từ Order ---
        if (order != null) {
            dto.setNote(order.getNote());
            dto.setStatus(order.getStatus());
        }

        // --- Customer ---
        if (invoice.getCustomer() != null) {
            var c = invoice.getCustomer();
            dto.setCustomer(new InvoiceDTO.CustomerDTO(
                    c.getId(),
                    c.getFullName(),
                    c.getPhone(),
                    c.getUser().getEmail(),
                    c.getUser().getDefaultAddress()
            ));
        } else if (order != null && order.getUser() != null) {
            // fallback: lấy từ User nếu không có Customer
            var u = order.getUser();
            dto.setCustomer(new InvoiceDTO.CustomerDTO(
                    u.getId(),
                    u.getFullName(),   // đổi theo field thực tế của User entity
                    u.getPhone(),
                    u.getEmail(),
                    null
            ));
        }

        // --- Items ---
        if (order != null && order.getItems() != null) {
            List<InvoiceDTO.OrderItemDTO> items = order.getItems()
                    .stream()
                    .map(this::toItemDTO)
                    .collect(Collectors.toList());
            dto.setItems(items);
        }

        return dto;
    }

    private InvoiceDTO.OrderItemDTO toItemDTO(OrderItem item) {
        InvoiceDTO.OrderItemDTO dto = new InvoiceDTO.OrderItemDTO();

        dto.setName(item.getDrink() != null ? item.getDrink().getName() : "Món không tên");
        dto.setQty(item.getQuantity());
        dto.setUnitPrice(item.getBasePriceAtPurchase());
        dto.setPrice(item.getBasePriceAtPurchase() != null && item.getQuantity() != null
                ? item.getBasePriceAtPurchase() * item.getQuantity() : 0f);
        dto.setSize(item.getSize() != null ? item.getSize().getName() : null);
        dto.setSugar(item.getSugar());
        dto.setIce(item.getIce());

        // --- Toppings ---
        if (item.getOrderItemToppings() != null) {
            List<InvoiceDTO.ToppingDTO> toppings = item.getOrderItemToppings()
                    .stream()
                    .map(oit -> new InvoiceDTO.ToppingDTO(
                            oit.getTopping() != null ? oit.getTopping().getName() : "",
                            oit.getBasePriceAtPurchase()
                    ))
                    .collect(Collectors.toList());
            dto.setToppings(toppings);
        }

        return dto;
    }
}