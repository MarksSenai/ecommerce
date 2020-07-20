package com.ecommerce.resources;

import com.ecommerce.domains.Category;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> findCategories() {
        List<Category> list = categoryService.findCategoriesList();
        List<CategoryDTO> categoryDTOS = list.stream().map(obj -> new CategoryDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> findCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.findCategoryById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.createCategory(category));
    }
}