package com.ecommerce.resources.api.v1;

import com.ecommerce.domains.Orders;
import com.ecommerce.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.BASE_PATH_V1 +  "/orders")
public class OrdersRest {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orders> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ordersService.findOrderById(id));
    }
}