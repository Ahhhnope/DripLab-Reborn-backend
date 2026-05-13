package com.example.cafe.Service.impl;

import com.example.cafe.DTO.CartItemRequest;
import com.example.cafe.DTO.CheckoutDTO;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.Cart.CartItem;
import com.example.cafe.Entity.Cart.CartItemTopping;
import com.example.cafe.Entity.Drink.Drink;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Cart.CartItemRepository;
import com.example.cafe.Repository.Cart.CartItemToppingRepository;
import com.example.cafe.Repository.Cart.CartRepository;
import com.example.cafe.Repository.Drink.DrinkRepository;
import com.example.cafe.Repository.Drink.ToppingRepository;
import com.example.cafe.Repository.SizeRepository;
import com.example.cafe.Service.CartService;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;
    private final SizeRepository sizeRepository;
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
    public Cart addItem(Integer userId, CartItemRequest cartItemRequest) {
        Integer drinkId = cartItemRequest.getDrinkId();
        Integer sizeId = cartItemRequest.getSizeId();
        Integer quantity = cartItemRequest.getQuantity();
        Integer ice = cartItemRequest.getIce();
        Integer sugar = cartItemRequest.getSugar();
        List<Integer> toppingIDList = cartItemRequest.getToppings();

        System.out.println("Processing add for User: " + userId + " Drink: " + drinkId);
        //find the correct cart
        Cart cart = getCartByUserId(userId);

        //find the correct drink
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(() -> new CustomResourceNotFound("Drink not found: " + drinkId));

        //check if the drink is "active"
        if (!drink.getActive()) {
            throw new CustomResourceNotFound("Đồ uống này hiện không có trong thực đơn");
        }

        //check if an item like that already exist in the cart
        List<CartItem> existingCartItems = cartItemRepository.findByCartId(cart.getId());

        CartItem existingCartItem = existingCartItems.stream()
                .filter(item -> item.getDrink().getId().equals(drinkId))
                .filter(item -> item.getSize().getId().equals(sizeId))
                .filter(item -> item.getIce().equals(ice))
                .filter(item -> item.getSugar().equals(sugar))
                .filter(item -> isSameToppings(item.getId(), toppingIDList))
                .findFirst().orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {

            //combine into a CartItem
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setDrink(drink);
            cartItem.setSize(sizeRepository.findById(sizeId).orElseThrow(() -> new CustomResourceNotFound("Size not found: " + sizeId)));
            cartItem.setIce(ice);
            cartItem.setSugar(sugar);
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
        }

        // Return cart with item
        return cartRepository.findByUserId(userId);
    }

    private boolean isSameToppings(Integer cartItemId, List<Integer> requestToppingIds) {
        List<Integer> existingToppings = cartItemToppingsRepository.findAll().stream()
                .filter(cit -> cit.getCartItem().getId().equals(cartItemId)) // Get the cart item from the cartItemId
                .map(cit -> cit.getTopping().getId()) // Get the toppings of the above cart item
                .sorted().toList();

        List<Integer> sortedToppingIds = null;
        if (requestToppingIds != null) {
            sortedToppingIds = requestToppingIds.stream().sorted().toList();
        }
        return sortedToppingIds.equals(existingToppings);
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
    public Cart updateQuantity(Integer cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomResourceNotFound("Item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item); // Update just the quantity column

        return item.getCart();
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
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }
//        Order order = orderService.createOrder(userId, "Online Order", "Tiền mặt");
        //WIP
    }

    @Override
    @Transactional
    public Order checkoutSelected(Integer userId, CheckoutDTO checkoutDTO) {
        List<Integer> cartItemIds = checkoutDTO.getCartItemIds();

        if (cartItemIds == null || cartItemIds.isEmpty()) {
            throw new RuntimeException("Vui lòng chọn ít nhất một sản phẩm");
        }

        Cart cart = cartRepository.findByUserId(userId);

        List<CartItem> selectedItems = cartItemRepository.findAllById(cartItemIds)
                .stream()
                .filter(item -> item.getCart().getId().equals(cart.getId()))
                .toList();

        if (selectedItems.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm hợp lệ");
        }

        return orderService.createOrderFromSelectedItems(
                userId,
                selectedItems,
                checkoutDTO.getNote(),
                checkoutDTO.getPaymentMethod(),
                checkoutDTO.getCustomerName(),    // ✅ thêm
                checkoutDTO.getCustomerPhone(),   // ✅ thêm
                checkoutDTO.getDeliveryAddress()  // ✅ thêm
        );
    }

}
