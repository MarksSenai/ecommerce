package com.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domains.Product;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.resources.utils.URL;
import com.ecommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value="name", defaultValue = "") String name,
            @RequestParam(value="categories", defaultValue = "") String categories,
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value="direction", defaultValue = "ASC") String direction) {
        String nameDecoded = URL.decodeParams(name);
        List<Long> ids = URL.decodeIntList(categories);
        Page<Product> list = productService.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProductDTO> productDTOList = list.map(obj -> new ProductDTO(obj));
        return ResponseEntity.ok().body(productDTOList);
    }
}
