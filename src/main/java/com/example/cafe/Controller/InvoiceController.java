package com.example.cafe.Controller;

import com.example.cafe.Entity.Invoice;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.InvoiceRepository;
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

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return new ResponseEntity<>(invoiceRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable int id) {
        return new ResponseEntity<>(invoiceRepository.findById(id).orElseThrow(() -> new CustomResourceNotFound("Invoice not found: "+id)), HttpStatus.OK);
    }

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
