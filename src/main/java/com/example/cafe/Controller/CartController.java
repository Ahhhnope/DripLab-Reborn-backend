package com.example.cafe.Controller;

import com.example.cafe.DTO.CartItemRequest;
import com.example.cafe.Entity.Cart;
import com.example.cafe.Repository.CartItemRepository;
import com.example.cafe.Repository.CartRepository;
import com.example.cafe.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Cart> getCart(@PathVariable Integer userID) {
        return new ResponseEntity<>(cartService.getCartByUserId(userID), HttpStatus.OK);
    }

    //add stuffs to cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addCartItem(@RequestBody CartItemRequest cartItemReq) {
        Cart cart = cartService.addItem(cartItemReq.getUserId(), cartItemReq.getDrinkId(), cartItemReq.getQuantity(), cartItemReq.getToppings());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Integer id, @RequestParam Integer quantity) {
        return new ResponseEntity<>(cartService.updateItem(id, quantity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.removeItem(id), HttpStatus.OK);
    }
}
