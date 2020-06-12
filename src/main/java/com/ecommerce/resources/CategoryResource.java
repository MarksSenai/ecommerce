package com.ecommerce.resources;

import com.ecommerce.domains.Category;
import com.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categorieService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> findCategories() {
        return ResponseEntity.ok().body(categorieService.findCategoriesList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> findCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categorieService.findCategoryById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok().body(categorieService.createCategory(category));
    }
}