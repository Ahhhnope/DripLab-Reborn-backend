package com.example.cafe.Controller;

import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Entity.User;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // ADMIN only - see all orders
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // Any logged-in user - see only their own orders
    @GetMapping("/my")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(orderService.getMyOrders(currentUser.getId()));
    }

    // ADMIN or the owning user can view a specific order
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int id, @RequestBody String status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(id, status), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String note) {
        return new ResponseEntity<>(orderService.createOrder(currentUser.getId(), note), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // POS checkout - ADMIN only
    @PostMapping("/checkout/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> checkoutOrder(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.createOrder(id, "Order from POS"), HttpStatus.OK);
    }
}
