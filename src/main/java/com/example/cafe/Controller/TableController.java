package com.example.cafe.Controller;

import com.example.cafe.Entity.CafeTable;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.CafeTableRepository;
import com.example.cafe.Repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {
    private final CafeTableRepository cafeTableRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<CafeTable>> getTables() {
        return new ResponseEntity<>(cafeTableRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/reserve/{tableID}")
    public ResponseEntity<CafeTable> reserveTable(@PathVariable("tableID") int tableID) {
        CafeTable tableFound = cafeTableRepository.findById(tableID).orElseThrow(() -> new CustomResourceNotFound("không tìm thấy bàn với id: " + tableID));
//        Order orderFound = orderRepository.findById(orderId).orElseThrow(() -> new CustomResourceNotFound("không tìm thấy order: "+orderId));

        tableFound.setStatus("Đang sử dụng");
//        tableFound.setCurrentOrder(orderFound);

        CafeTable saved = cafeTableRepository.save(tableFound);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping("/release/{tableId}")
    public ResponseEntity<CafeTable> releaseTable(@PathVariable("tableId") int tableID) {
        CafeTable tableFound = cafeTableRepository.findById(tableID).orElseThrow(() -> new CustomResourceNotFound("không tìm thấy bàn với id: " + tableID));
        tableFound.setStatus("Còn trống");
        tableFound.setCurrentOrder(null);

        CafeTable saved = cafeTableRepository.save(tableFound);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
}
