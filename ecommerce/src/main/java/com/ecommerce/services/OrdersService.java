package com.ecommerce.services;

import com.ecommerce.domains.BilletPayment;
import com.ecommerce.domains.OrderItem;
import com.ecommerce.domains.Orders;
import com.ecommerce.domains.enums.PaymentStatus;
import com.ecommerce.dto.OrdersDTO;
import com.ecommerce.repositories.OrderItemRepository;
import com.ecommerce.repositories.OrdersRepository;
import com.ecommerce.repositories.PaymentRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BilletService billetService;
    @Autowired
    private ProductService productService;

    public Orders findOrderById(Long id) {
        Optional<Orders> order = ordersRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! id: " +
                id + " Tipo" + Orders.class.getName()));
    }

    @Transactional
    public Orders insert(Orders order) {

        if (order.getPayment() instanceof BilletPayment) {
            BilletPayment billetPayment = (BilletPayment) order.getPayment();
            billetService.fillOutPaymentBillet(billetPayment, order.getInstant());
        }
        order = ordersRepository.save(order);
        paymentRepository.save(order.getPayment());
        for (OrderItem oi : order.getItems()) {
            oi.setDiscount(0.0);
            oi.getId().setProduct(productService.findById(oi.getProduct().getId()));
            oi.setPrice(oi.getProduct().getPrice());
            oi.getId().setOrder(order);
        }
        orderItemRepository.saveAll(order.getItems());
        System.out.println("Orders: " + order);
        return  order;
    }

    private Orders fromDTO(OrdersDTO dto) {
        Orders order = new Orders();
        order.setInstant(new Date());
        order.setUser(userService.findUserById(dto.getUser().getId()));
        order.setPayment(dto.getPayment());
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setOrder(order);
//        order.setItems(dto.getItems());

        return order;
    }

}
