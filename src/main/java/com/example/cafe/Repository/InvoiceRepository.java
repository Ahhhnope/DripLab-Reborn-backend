package com.example.cafe.Repository;

import com.example.cafe.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findByOrderId(int orderId);
    Optional<Invoice> findByInvoiceNumber(Integer invoiceNumber);
}
