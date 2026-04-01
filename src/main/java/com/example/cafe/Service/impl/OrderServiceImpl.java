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
    private final OrderItemRepository orderItemRepository;
    private final OrderItemToppingRepository orderItemToppingsRepository;
    private final InvoiceRepository invoiceRepository;

    private final CartRepository cartRepository;
    private final CartItemToppingRepository cartItemToppingsRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final DrinkRepository drinkRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order createOrder(Integer userID, String note) {
        //Boi this is long asf

        // Takes out all items in cart into a list
        Cart cart = cartRepository.findByUserId(userID);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new CustomResourceNotFound("Le cart is empty bruv");
        }

        // Check if we have enough drinks in stock
        for (CartItem cartItem : cartItems) {
            if (cartItem.getDrink().getQuantity() < cartItem.getQuantity()) {
                throw new CustomResourceNotFound("Insufficient stock for: " + cartItem.getDrink().getName() + " (Available: " + cartItem.getDrink().getQuantity() + ")");
            }
        }


        // calc (Takes the price of the list of cart items above and crunch it all)
        float originalPrice = 0;
        for (CartItem cartItem : cartItems) {
            float drinkPrice = cartItem.getDrink().getBasePrice();
            List<CartItemTopping> toppings = cartItemToppingsRepository.findByCartItemId(cartItem.getId());

            // total drink price + total topping price
            for (CartItemTopping cit : toppings) {
                drinkPrice += cit.getTopping().getPrice();
            }

            originalPrice += (drinkPrice * cartItem.getQuantity());
        }

        // Tax evasion 100
        double taxAmount = originalPrice * 0.1; // nuuuuu you can't avoid tax :)
        double shippingFee = 0; // for now its just a POS system so no shipping fee ;-;
        double discountAmount = 0; // no discount system yet ;-;

        double finalPrice = originalPrice + taxAmount + shippingFee - discountAmount;

        // Put on all the infinity stones (damn that's a lot ;-;)
        Order order = new Order();
        order.setOrderNumber((int)(System.currentTimeMillis() % 1000000)); // random stuff ;-;
        order.setOrderDate(LocalDate.now());
        order.setCreatedAt(LocalDate.now());
        order.setUpdatedAt(LocalDate.now());
        order.setNote(note);
        order.setStatus("Chưa giải quyết");
        order.setOriginalPrice(originalPrice);
        order.setTaxAmount((float) taxAmount);
        order.setShippingFee((float) shippingFee);
        order.setDiscountAmount((float) discountAmount);
        order.setFinalPrice((float) finalPrice);

        // Save customer on to ze order if there's any
        Customer customer = customerRepository.findByUserId(userID);
        if (customer != null) {
            order.setCustomer(customer);
            order.setCustomerName(customer.getFullName());
            order.setCustomerPhone(customer.getPhone());
            order.setCustomerAddress(customer.getDefaultAddress());
        }

        // save that boi to get the ID for below
        Order savedOrder = orderRepository.save(order);



        // Save the items for when displaying orders
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setDrink(cartItem.getDrink());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBasePriceAtPurchase(cartItem.getDrink().getBasePrice());

            // same thing as savedOrder
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            // Update quantity of drinks
            Drink tempDrink = cartItem.getDrink();
            tempDrink.setQuantity(tempDrink.getQuantity() - cartItem.getQuantity());
            drinkRepository.save(tempDrink);

            // Save le toppings to orderItemToppings table
            List<CartItemTopping> toppings = cartItemToppingsRepository.findByCartItemId(cartItem.getId());
            for (CartItemTopping cit : toppings) {
                OrderItemTopping orderItemTopping = new OrderItemTopping();
                orderItemTopping.setOrderItem(savedOrderItem);
                orderItemTopping.setTopping(cit.getTopping());
                orderItemTopping.setBasePriceAtPurchase(cit.getTopping().getPrice());

                orderItemToppingsRepository.save(orderItemTopping);
            }
        }

        // Clear all current cart_items from cart
        cartItemRepository.deleteAll(cartItems);
        for (CartItem cartItem : cartItems) {
            cartItemToppingsRepository.deleteByCartItemId(cartItem.getId());
        }

        generateInvoice(savedOrder);
        return savedOrder;
    }

    @Transactional
    public Invoice generateInvoice(Order order) {
        Invoice invoice = new Invoice();

        invoice.setOrder(order);

        // use "random bullshit go" for that unique invoice number
        invoice.setInvoiceNumber((int)(System.currentTimeMillis() % 1000000));

        invoice.setInvoiceDate(LocalDate.now());
        invoice.setOriginalPrice(order.getOriginalPrice());
        invoice.setTaxAmount(order.getTaxAmount());
        invoice.setShippingFee(order.getShippingFee());
        invoice.setDiscountAmount(order.getDiscountAmount());
        invoice.setFinalPrice(order.getFinalPrice());

        // customer stuff
        invoice.setCustomerName(order.getCustomerName());
        invoice.setCustomerPhone(order.getCustomerPhone());
        invoice.setCustomerAddress(order.getCustomerAddress());

        // payment status
        invoice.setPaymentMethod("Tiền mặt"); // or momo idk
        invoice.setPaymentStatus("Đã trả");

        return invoiceRepository.save(invoice);
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
