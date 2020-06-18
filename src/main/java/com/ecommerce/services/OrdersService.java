package com.ecommerce.services;

import com.ecommerce.domains.Orders;
import com.ecommerce.repositories.OrdersRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Orders findOrderById(Long id) {
        Optional<Orders> order = ordersRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! id: " +
                id + " Tipo" + Orders.class.getName()));

    }
}
