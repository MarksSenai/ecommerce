package com.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.domains.Category;
import com.ecommerce.domains.Product;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product findById(Long id) {
        Optional<Product> order = productRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! id: " +
                id + " Tipo" + Product.class.getName()));
    }

    public Page<Product> search(String name, List<Long> ids,
            Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
        return productRepository.search(name, categories, pageRequest);
    }

}
