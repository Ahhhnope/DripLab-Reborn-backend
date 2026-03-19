package com.example.cafe.Service.impl;

import com.example.cafe.Entity.Cart;
import com.example.cafe.Entity.CartItem;
import com.example.cafe.Entity.CartItemTopping;
import com.example.cafe.Entity.Drink;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.*;
import com.example.cafe.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final DrinkRepository drinkRepository;
    @Autowired
    private ToppingRepository toppingRepository;
    @Autowired
    private CartItemToppingsRepository cartItemToppingsRepository;

    @Override
    public Cart getCartByUserId(int userID) {
        return cartRepository.findByUserId(userID);
    }

    @Override
    public Cart addItem(Integer userId, Integer drinkId, Integer quantity, List<Integer> toppingIDList) {
        //find the correct cart
        Cart cart = getCartByUserId(userId);

        Drink drink =


        return null;
    }

    @Override
    public Cart updateItem(Integer cartItemId, Integer quantity) {
        return null;
    }

    @Override
    public Cart removeItem(Integer cartItemId) {
        return null;
    }

    @Override
    public void checkout(Integer userId) {

    }


}
