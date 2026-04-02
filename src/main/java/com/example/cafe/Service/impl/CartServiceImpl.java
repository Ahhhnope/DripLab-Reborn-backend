package com.example.cafe.Service.impl;

import com.example.cafe.DTO.CartItemRequest;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.Cart.CartItem;
import com.example.cafe.Entity.Cart.CartItemTopping;
import com.example.cafe.Entity.Drink.Drink;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Cart.CartItemRepository;
import com.example.cafe.Repository.Cart.CartItemToppingRepository;
import com.example.cafe.Repository.Cart.CartRepository;
import com.example.cafe.Repository.Drink.DrinkRepository;
import com.example.cafe.Repository.Drink.ToppingRepository;
import com.example.cafe.Service.CartService;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;
    private final CartItemToppingRepository cartItemToppingsRepository;
    private final OrderService orderService;


    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

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

        //check if the drink is "active"
        if (!drink.isActive()) {
            throw new CustomResourceNotFound("Đồ uống này hiện không có trong thực đơn");
        }

        //combine into a CartItem
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDrink(drink);
        cartItem.setQuantity(quantity);

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // Add toppings if exist :o
        if (toppingIDList != null) {
            for (Integer toppingId : toppingIDList) {
                toppingRepository.findById(toppingId).ifPresent(topping -> {
                    CartItemTopping cit = new CartItemTopping();
                    cit.setCartItem(savedCartItem);
                    cit.setTopping(topping);
                    cartItemToppingsRepository.save(cit);
                });
            }
        }

        // Return cart with item
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Cart updateItem(Integer cartItemId, CartItemRequest cartItemRequest) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new CustomResourceNotFound("CartItem not found: " + cartItemId));
        cartItem.setQuantity(cartItemRequest.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // "update" toppings (more like replacing it lmao)

        cartItemToppingsRepository.deleteByCartItemId((cartItemId)); // <-- require a @Transactional lmao

        List<Integer> newToppingIDList = cartItemRequest.getToppings();
        if (newToppingIDList != null) {
            for (Integer toppingID : newToppingIDList) {
                toppingRepository.findById(toppingID).ifPresent(t -> {
                    CartItemTopping cit = new CartItemTopping();
                    cit.setCartItem(savedCartItem);
                    cit.setTopping(t);
                    cartItemToppingsRepository.save(cit);
                });
            }
        }

        return cartRepository.findByUserId(cartItem.getCart().getUser().getId());
    }

    @Transactional
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
        orderService.createOrder(userId, "Order from POS");
    }


}
