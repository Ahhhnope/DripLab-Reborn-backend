package com.example.cafe.Controller;


import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    //new thing ?!?!?
    //didn't even know this existed lmao
    //apparently it's mainly used for updating just a part in a row instead of replacing the entire row
    @PatchMapping("/update/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(id, status), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@RequestParam Integer userId, @RequestParam(required = false) String note) {
        return new ResponseEntity<>(orderService.createOrder(userId, note), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Order> checkoutOrder(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.createOrder(id, "Order from POS"), HttpStatus.OK);
    }

}
