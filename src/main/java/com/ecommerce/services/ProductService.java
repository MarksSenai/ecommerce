package com.ecommerce.services;

import com.ecommerce.domains.Product;
import com.ecommerce.resources.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


}
