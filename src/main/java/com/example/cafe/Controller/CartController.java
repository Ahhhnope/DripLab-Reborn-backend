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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private final CartService cartService;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final CartItemRepository cartItemRepository;

    @GetMapping("/{userID}")
    public ResponseEntity<Cart> getCart(@PathVariable Integer userID) {
        return new ResponseEntity<>(cartService.getCartByUserId(userID), HttpStatus.OK);
    }


    //add stuffs to cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addCartItem(@RequestBody CartItemRequest cartItemReq) {
        Cart cart = cartService.addItem(cartItemReq.getUserId(), cartItemReq.getDrinkId(), cartItemReq.getQuantity(), cartItemReq.getToppings());
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }
}
