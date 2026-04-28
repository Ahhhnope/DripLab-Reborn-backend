package com.example.cafe.Service;

import com.example.cafe.DTO.CartItemRequest;
import com.example.cafe.Entity.Cart.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();

    Cart getCartByUserId(int userId);

    Cart addItem(Integer userId, CartItemRequest cartItemRequest);

    Cart updateItem(Integer cartItemId, CartItemRequest cartItemRequest);

    Cart updateQuantity(Integer cartItemId, Integer quantity);

    Cart removeItem(Integer cartItemId);

    void checkout(Integer userId);
}
