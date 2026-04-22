package com.example.cafe.Controller;


import com.example.cafe.DTO.OrderUpdateDTO;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        return new ResponseEntity<>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<List<Order>> getActiveOrdersByUserId(@PathVariable Integer userId) {
        List<Order> activeOrders = orderService.findActiveOrdersByUserId(userId);
        return new ResponseEntity<>(activeOrders, HttpStatus.OK);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Order>> getOrderHistoryByUserId(@PathVariable Integer userId) {
        // Logic: find orders where status is 'Đã giao' or 'Đã huỷ'
        List<Order> history = orderService.findOrderHistoryByUserId(userId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    //new thing ?!?!?
    //didn't even know this existed lmao
    //apparently it's mainly used for updating just a part in a row instead of replacing the entire row
    //update for admin
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id, @RequestBody OrderUpdateDTO dto) {
        orderService.updateOrder(id, dto);
        return ResponseEntity.ok().build();
    }

    //cancel order for user
    @PutMapping("/user/cancel/{id}")
    public ResponseEntity<?> userUpdateOrder(@PathVariable Integer id, @RequestBody OrderUpdateDTO dto) {
        orderService.cancelOrderForUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestParam Integer userId, @RequestParam(required = false) String note, @RequestParam(defaultValue = "Tiền mặt") String paymentMethod) {
        return new ResponseEntity<>(orderService.createOrder(userId, note, paymentMethod), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Order> checkoutOrder(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        String note = payload.getOrDefault("note", "Online Order");
        String method = payload.getOrDefault("paymentMethod", "Tiền mặt");
        return new ResponseEntity<>(orderService.createOrder(id, note, method), HttpStatus.OK);
    }

}
