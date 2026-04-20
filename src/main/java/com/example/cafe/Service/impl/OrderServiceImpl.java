package com.example.cafe.Service.impl;

import com.example.cafe.DTO.OrderUpdateDTO;
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
    private final UserRepository userRepository;


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order createOrder(Integer userID, String note, String paymentMethod) {
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
            if (cartItem.getSize() != null) {
                drinkPrice += cartItem.getSize().getPrice();
            }
            List<CartItemTopping> toppings = cartItemToppingsRepository.findByCartItemId(cartItem.getId());

            // total drink price + total topping price
            for (CartItemTopping cit : toppings) {
                drinkPrice += cit.getTopping().getPrice();
            }

            originalPrice += (drinkPrice * cartItem.getQuantity());
        }

        // Tax evasion 100
        double taxAmount = 0;
        double shippingFee = 0; // for now its just a POS system so no shipping fee ;-;
        double discountAmount = 0; // no discount system yet ;-;

        if ("Online Order".equalsIgnoreCase(note)) {
            shippingFee = (originalPrice < 100000) ? 20000 : 0;
        }


        double finalPrice = originalPrice + taxAmount + shippingFee - discountAmount;

        // Put on all the infinity stones (damn that's a lot ;-;)
        Order order = new Order();
        order.setOrderNumber((int)(System.currentTimeMillis() % 1000000)); // random stuff ;-;
        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setPaymentMethod(paymentMethod);
        order.setNote(note);
        order.setStatus("Chờ xác nhận");
        order.setOriginalPrice(originalPrice);
        order.setTaxAmount((float) taxAmount);
        order.setShippingFee((float) shippingFee);
        order.setDiscountAmount((float) discountAmount);
        order.setFinalPrice((float) finalPrice);

        if ("Online Order".equalsIgnoreCase(note)) {
            order.setStatus("Chờ xác nhận");
            order.setType("Online");
        } else {
            order.setStatus("Đã giao");
            order.setType("POS");
        }


        // Save customer on to ze order if there's any
//        Customer customer = customerRepository.findByUserId(userID);
//        if (customer != null) order.setCustomer(customer);

        //Save user on ze order if there's any
        User user = userRepository.findById(userID).orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy user id: "+userID));
        if (user != null) order.setUser(user);


        // save that boi to get the ID for below
        Order savedOrder = orderRepository.save(order);



        // Save the items for when displaying orders
        for (CartItem cartItem : cartItems) {

            //check quantity for ingredients -> if nothing's wrong then deduct quantity
            Drink drink = cartItem.getDrink();
            int quantity = cartItem.getQuantity();

            //Apperently we dont need to deduct ingredients for normal ordering system ;-;
//            if (drink.getCategory().equalsIgnoreCase("Cà phê")) {
//                if (drink.getCoffeeBean().getQuantity() < quantity) throw new CustomResourceNotFound("Hết hạt cà phê: "+drink.getCoffeeBean().getName());
//
//                CoffeeBean coffeeBean = drink.getCoffeeBean();
//                coffeeBean.setQuantity(coffeeBean.getQuantity() - quantity);
//                coffeeBeanRepository.save(coffeeBean);
//            }
//            if (drink.getMilk() != null) {
//                if (drink.getMilk().getQuantity() < quantity) throw new CustomResourceNotFound("Hết sữa: "+drink.getMilk().getName());
//
//                Milk milk = drink.getMilk();
//                milk.setQuantity(milk.getQuantity() - quantity);
//                milkRepository.save(milk);
//            }
//            if (drink.getHeavyCream() != null) {
//                if (drink.getHeavyCream().getQuantity() < quantity) throw new CustomResourceNotFound("Hết kem béo: " + drink.getHeavyCream().getName());
//
//                HeavyCream heavyCream = drink.getHeavyCream();
//                heavyCream.setQuantity(heavyCream.getQuantity() - quantity);
//                heavyCreamRepository.save(heavyCream);
//            }
//            if (drink.getIceCream() != null) {
//                if (drink.getIceCream().getQuantity() < quantity) throw new CustomResourceNotFound("Hết kem lạnh: " + drink.getIceCream().getName());
//
//                IceCream iceCream = drink.getIceCream();
//                iceCream.setQuantity(iceCream.getQuantity() - quantity);
//                iceCreamRepository.save(iceCream);
//            }

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


        if ("Đã giao".equals(savedOrder.getStatus())) {
            generateInvoice(savedOrder);
        }

        return savedOrder;
    }

    @Transactional
    public Invoice generateInvoice(Order order) {
        Invoice invoice = new Invoice();

        invoice.setOrder(order);

        // use "random bullshit go" for that unique invoice number
        // Using the ID ensures it's actually unique
        // Just use the Order ID or a safe offset
        invoice.setInvoiceNumber(order.getId() + 1000);

        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setOriginalPrice(order.getOriginalPrice());
        invoice.setTaxAmount(order.getTaxAmount());
        invoice.setShippingFee(order.getShippingFee());
        invoice.setDiscountAmount(order.getDiscountAmount());
        invoice.setFinalPrice(order.getFinalPrice());

        // payment status
        invoice.setPaymentMethod(order.getPaymentMethod()); // or momo idk
        invoice.setReceiptType(order.getType());

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

    @Transactional
    @Override
    public void updateOrder(Integer id, OrderUpdateDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String oldStatus = order.getStatus();
        String newStatus = dto.getStatus();
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());

        // Trigger invoice generation when order is marked as Delivered
        if (!"Đã giao".equals(oldStatus) && "Đã giao".equals(newStatus)) {
            // Double check to prevent duplicate invoices
            boolean alreadyHasInvoice = invoiceRepository.existsByOrderId(id);
            if (!alreadyHasInvoice) {
                generateInvoice(order);
            }
        }

        orderRepository.save(order);
    }



    @Transactional
    @Override
    public void deleteOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomResourceNotFound("Order not found"));

        if (order.getInvoice() != null) {
            invoiceRepository.delete(order.getInvoice());
        }

        orderRepository.delete(order);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders;
    }

    @Override
    public List<Order> findActiveOrdersByUserId(Integer userId) {
        List<Order> activeOrders = orderRepository.findActiveOrders(userId);
        activeOrders.sort((a, b) -> b.getId().compareTo(a.getId()));
        return activeOrders;
    }

    @Override
    public List<Order> findOrderHistoryByUserId(Integer userId) {
        List<String> historyStatuses = List.of("Đã giao", "Đã huỷ");
        return orderRepository.findByUser_IdAndStatusInOrderByOrderDateDesc(userId, historyStatuses);
    }


    //genuinely tweaking out rn
}
