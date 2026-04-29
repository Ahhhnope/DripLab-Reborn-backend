package com.example.cafe.Controller;

import com.example.cafe.DTO.CartItemRequest;
import com.example.cafe.DTO.CheckoutDTO;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.Cart.CartItem;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Repository.Cart.CartItemRepository;
import com.example.cafe.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Cart> getCart(@PathVariable Integer userID) {
        return new ResponseEntity<>(cartService.getCartByUserId(userID), HttpStatus.OK);
    }

    //============================ Cart item stuffs ===========================================
    @GetMapping("/items/{id}")
    public ResponseEntity<List<CartItem>> getAllItemInCart(@PathVariable Integer id) {
        return new ResponseEntity<>(cartItemRepository.findByCartId(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCartItem(@RequestBody CartItemRequest cartItemReq) {
        Cart cart = cartService.addItem(cartItemReq.getUserId(), cartItemReq);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/items/{id}/quantity")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Integer id, @RequestBody CartItemRequest req) {
        // We only need the quantity from the DTO here
        return new ResponseEntity<>(cartService.updateQuantity(id, req.getQuantity()), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Integer id, @RequestBody CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(cartService.updateItem(id, cartItemRequest), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.removeItem(id), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/checkout-selected")
    public ResponseEntity<?> checkoutSelected(
            @PathVariable Integer userId,
            @RequestBody CheckoutDTO checkoutDTO) {
        try {
            Order order = cartService.checkoutSelected(userId, checkoutDTO);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
