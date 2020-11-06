package com.ecommerce.services;

import com.ecommerce.domains.BilletPayment;
import com.ecommerce.domains.OrderItem;
import com.ecommerce.domains.OrderItemPK;
import com.ecommerce.domains.Orders;
import com.ecommerce.domains.Product;
import com.ecommerce.domains.enums.PaymentStatus;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.dto.OrdersDTO;
import com.ecommerce.repositories.OrderItemRepository;
import com.ecommerce.repositories.OrdersRepository;
import com.ecommerce.repositories.PaymentRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import com.ecommerce.services.interfaces.EmailService;

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
    @Autowired
    private EmailService emailService;

    public Orders findOrderById(Long id) {
        Optional<Orders> order = ordersRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! id: " +
                id + " Tipo" + Orders.class.getName()));
    }

    @Transactional
    public Orders insert(OrdersDTO dto) {
        Orders order = fromDTO(dto);

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
        emailService.sendOrderConfirmationHtmlEmail(order);
        return  order;
    }

    private Orders fromDTO(OrdersDTO dto) {
        Orders order = new Orders();
        order.setInstant(new Date());
        order.setUser(userService.findUserById(dto.getUser().getId()));
        order.setPayment(dto.getPayment());
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setOrder(order);

        Set<OrderItem> items = new HashSet<>();
        for (OrderItemDTO list : dto.getItems()) {
            OrderItem oi = new OrderItem();
            OrderItemPK pk = new OrderItemPK();
            Product product = new Product();
            product.setId(list.getId());
            pk.setProduct(product);
            oi.setId(pk);
            oi.setQuantity(list.getQuantity());
            items.add(oi);
        }
        order.setItems(items);
        return order;
    }

}
