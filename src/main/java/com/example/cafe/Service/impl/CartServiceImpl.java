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

        //find the correct drink
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(() -> new CustomResourceNotFound("Drink not found: " + drinkId));

        //combine into a CartItem
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDrink(drink);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        // Add toppings if exist :o
        if (toppingIDList != null && !toppingIDList.isEmpty()) {
            for (Integer toppingId : toppingIDList) {
                toppingRepository.findById(toppingId).ifPresent(topping -> {
                    CartItemTopping cit = new CartItemTopping();
                    cit.setCartItem(cartItem);
                    cit.setTopping(topping);
                    cartItemToppingsRepository.save(cit);
                });
            }
        }

        // Return cart with item
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart updateItem(Integer cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomResourceNotFound("Cart item not found: " + cartItemId));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartRepository.findByUserId(cartItem.getCart().getUser().getId());
    }

    @Override
    public Cart removeItem(Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomResourceNotFound("Cart item not found: " + cartItemId));

        Integer userId = cartItem.getCart().getUser().getId();

        // Delete toppings first (FK constraint)
        cartItemToppingsRepository.deleteByCartItemId(cartItemId);
        cartItemRepository.deleteById(cartItemId);

        return cartRepository.findByUserId(userId);
    }

    @Override
    public void checkout(Integer userId) {

    }


}
