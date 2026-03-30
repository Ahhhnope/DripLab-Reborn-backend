package com.example.cafe.Service.impl;

import com.example.cafe.Entity.*;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.*;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemToppingsRepository cartItemToppingsRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order createOrder(Integer userID, String note) {

        // Takes out all items in cart into a list
        Cart cart = cartRepository.findByUserId(userID);
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items == null || items.isEmpty()) {
            throw new CustomResourceNotFound("Cart is empty");
        }

        // calc (Takes the price of the list of cart items above and crunch it all)
        double originalPrice = 0;
        for (CartItem item : items) {

            double itemPrice = item.getDrink().getBasePrice() * item.getQuantity();
            List<CartItemTopping> toppings = cartItemToppingsRepository.findByCartItemId(item.getId());

            for (CartItemTopping cit : toppings) {
                itemPrice += cit.getTopping().getPrice() * item.getQuantity();
            }

            originalPrice += itemPrice;
        }

        //Tax evasion typeshit
        double taxAmount = originalPrice * 0.1;
        double shippingFee = 0;
        double discountAmount = 0;
        double finalPrice = originalPrice + taxAmount + shippingFee - discountAmount;

        // COMBINE IT ALL INTO ONE
        Order order = new Order();
        order.setOrderNumber((int)(System.currentTimeMillis() % 1000000));
        order.setOrderDate(LocalDate.now());
        order.setCreatedAt(LocalDate.now());
        order.setUpdatedAt(LocalDate.now());
        order.setNote(note);
        order.setStatus("Chưa giải quyết");
        order.setOriginalPrice((float) originalPrice);
        order.setTaxAmount((float) taxAmount);
        order.setShippingFee((float) shippingFee);
        order.setDiscountAmount((float) discountAmount);
        order.setFinalPrice((float) finalPrice);

        // Link customer if exists
        Customer customer = customerRepository.findByUserId(userID);
        if (customer != null) {
            order.setCustomer(customer);
            order.setCustomerName(customer.getFullName());
            order.setCustomerPhone(customer.getPhone());
            order.setCustomerAddress(customer.getDefaultAddress());
        }

        orderRepository.save(order);

        // Clear cart
        for (CartItem item : items) {
            cartItemToppingsRepository.deleteByCartItemId(item.getId());
        }
        cartItemRepository.deleteAll(items);

        return order;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomResourceNotFound("Order not found: " + orderId));
    }

    @Transactional
    @Override
    public Order updateOrderStatus(Integer orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        order.setUpdatedAt(LocalDate.now());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new CustomResourceNotFound("Order not found: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }


    //genuinely tweaking out rn
}
