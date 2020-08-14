package com.ecommerce.resources;

import com.ecommerce.domains.Orders;
import com.ecommerce.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrdersResource {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orders> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ordersService.findOrderById(id));
    }
}