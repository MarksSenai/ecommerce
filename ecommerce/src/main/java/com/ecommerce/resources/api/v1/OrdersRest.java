package com.ecommerce.resources.api.v1;

import java.net.URI;

import javax.validation.Valid;

import com.ecommerce.domains.Orders;
import com.ecommerce.dto.OrdersDTO;
import com.ecommerce.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(RestPath.BASE_PATH_V1 +  "/orders")
public class OrdersRest {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orders> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ordersService.findOrderById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Orders order) {
        ordersService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).build();
        return null;
    }
}