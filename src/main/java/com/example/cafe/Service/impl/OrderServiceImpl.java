package com.example.cafe.Service.impl;

import com.example.cafe.Entity.*;
import com.example.cafe.Entity.Cart.Cart;
import com.example.cafe.Entity.Cart.CartItem;
import com.example.cafe.Entity.Cart.CartItemTopping;
import com.example.cafe.Entity.Drink.Ingredient.CoffeeBean;
import com.example.cafe.Entity.Drink.Ingredient.HeavyCream;
import com.example.cafe.Entity.Drink.Ingredient.IceCream;
import com.example.cafe.Entity.Drink.Ingredient.Milk;
import com.example.cafe.Entity.Order.Order;
import com.example.cafe.Entity.Order.OrderItem;
import com.example.cafe.Entity.Order.OrderItemTopping;
import com.example.cafe.Entity.Drink.Drink;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.*;
import com.example.cafe.Repository.Cart.CartItemRepository;
import com.example.cafe.Repository.Cart.CartItemToppingRepository;
import com.example.cafe.Repository.Cart.CartRepository;
import com.example.cafe.Repository.Drink.Ingredient.CoffeeBeanRepository;
import com.example.cafe.Repository.Drink.Ingredient.HeavyCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.IceCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.MilkRepository;
import com.example.cafe.Repository.Drink.InstructionRepository;
import com.example.cafe.Repository.Order.OrderItemRepository;
import com.example.cafe.Repository.Order.OrderItemToppingRepository;
import com.example.cafe.Repository.Order.OrderRepository;
import com.example.cafe.Repository.Drink.DrinkRepository;
import com.example.cafe.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final CoffeeBeanRepository coffeeBeanRepository;
    private final MilkRepository milkRepository;
    private final HeavyCreamRepository heavyCreamRepository;
    private final IceCreamRepository iceCreamRepository;
    private final InstructionRepository instructionRepository;


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

        // calc (Takes the price of the ingredients inside each cart items above and crunch ze numbers)
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
        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setNote(note);
        order.setStatus("Chưa giải quyết");
        order.setOriginalPrice(originalPrice);
        order.setTaxAmount((float) taxAmount);
        order.setShippingFee((float) shippingFee);
        order.setDiscountAmount((float) discountAmount);
        order.setFinalPrice((float) finalPrice);

        // Save customer on to ze order if there's any
        Customer customer = customerRepository.findByUserId(userID);
        if (customer != null) order.setCustomer(customer);

        // save that boi to get the ID for below
        Order savedOrder = orderRepository.save(order);



        // Save the items for when displaying orders
        for (CartItem cartItem : cartItems) {

            //check quantity for ingredients -> if nothing's wrong then deduct quantity
            Drink drink = cartItem.getDrink();
            int quantity = cartItem.getQuantity();

            if (drink.getCategory().equalsIgnoreCase("Cà phê")) {
                if (drink.getCoffeeBean().getQuantity() < quantity) throw new CustomResourceNotFound("Hết hạt cà phê: "+drink.getCoffeeBean().getName());

                CoffeeBean coffeeBean = drink.getCoffeeBean();
                coffeeBean.setQuantity(coffeeBean.getQuantity() - quantity);
                coffeeBeanRepository.save(coffeeBean);
            }
            if (drink.getMilk() != null) {
                if (drink.getMilk().getQuantity() < quantity) throw new CustomResourceNotFound("Hết sữa: "+drink.getMilk().getName());

                Milk milk = drink.getMilk();
                milk.setQuantity(milk.getQuantity() - quantity);
                milkRepository.save(milk);
            }
            if (drink.getHeavyCream() != null) {
                if (drink.getHeavyCream().getQuantity() < quantity) throw new CustomResourceNotFound("Hết kem béo: " + drink.getHeavyCream().getName());

                HeavyCream heavyCream = drink.getHeavyCream();
                heavyCream.setQuantity(heavyCream.getQuantity() - quantity);
                heavyCreamRepository.save(heavyCream);
            }
            if (drink.getIceCream() != null) {
                if (drink.getIceCream().getQuantity() < quantity) throw new CustomResourceNotFound("Hết kem lạnh: " + drink.getIceCream().getName());

                IceCream iceCream = drink.getIceCream();
                iceCream.setQuantity(iceCream.getQuantity() - quantity);
                iceCreamRepository.save(iceCream);
            }

            //that's alot of checking ;-;
            //anyway if it passes all lat then we ball


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setDrink(cartItem.getDrink());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBasePriceAtPurchase(cartItem.getDrink().getBasePrice());
            orderItem.setSize(cartItem.getSize());

            // same thing as savedOrder
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);


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
        for (CartItem cartItem : cartItems) {
            cartItemToppingsRepository.deleteByCartItemId(cartItem.getId());
        }
        cartItemRepository.deleteAll(cartItems);


        generateInvoice(savedOrder);
        return savedOrder;
    }

    @Transactional
    public Invoice generateInvoice(Order order) {
        Invoice invoice = new Invoice();

        invoice.setOrder(order);

        // use "random bullshit go" for that unique invoice number
        invoice.setInvoiceNumber((int)(System.currentTimeMillis() % 1000000));

        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setOriginalPrice(order.getOriginalPrice());
        invoice.setTaxAmount(order.getTaxAmount());
        invoice.setShippingFee(order.getShippingFee());
        invoice.setDiscountAmount(order.getDiscountAmount());
        invoice.setFinalPrice(order.getFinalPrice());

        // payment status
        invoice.setPaymentMethod("Tiền mặt"); // or momo idk

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
        order.setUpdatedAt(LocalDateTime.now());
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
