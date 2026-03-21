package com.example.cafe.Service;

import com.example.cafe.Entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();

    Cart getCartByUserId(int userId);

    Cart addItem(Integer userId, Integer drinkId, Integer quantity, List<Integer> toppingIDList);

    Cart updateItem(Integer cartItemId, Integer quantity);

    Cart removeItem(Integer cartItemId);

    void checkout(Integer userId);
}
