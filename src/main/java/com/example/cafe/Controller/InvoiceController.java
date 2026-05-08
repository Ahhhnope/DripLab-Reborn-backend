package com.example.cafe.Controller;

import com.example.cafe.DTO.InvoiceDTO;
import com.example.cafe.Entity.Invoice;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.InvoiceRepository;
import com.example.cafe.Service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;  // ✅ thêm

    // ✅ Trả DTO thay vì entity
    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        return new ResponseEntity<>(invoiceService.getAllInvoices(), HttpStatus.OK);
    }

    // ✅ Trả DTO thay vì entity
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable int id) {
        return new ResponseEntity<>(invoiceService.getInvoiceById(id), HttpStatus.OK);
    }

    // 2 endpoint dưới ít dùng, giữ nguyên entity cũng được
    @GetMapping("/order/{id}")
    public ResponseEntity<Invoice> getInvoiceByOrderId(@PathVariable int id) {
        return new ResponseEntity<>(invoiceRepository.findByOrderId(id), HttpStatus.OK);
    }

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<Invoice> getByInvoiceNumber(@PathVariable int invoiceNumber) {
        return new ResponseEntity<>(
                invoiceRepository.findByInvoiceNumber(invoiceNumber)
                        .orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy hóa đơn: " + invoiceNumber)),
                HttpStatus.OK
        );
    }
}