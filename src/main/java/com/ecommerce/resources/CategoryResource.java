package com.ecommerce.resources;

import com.ecommerce.domains.Category;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/category")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        category = categoryService.createCategory(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> findCategories() {
        List<Category> list = categoryService.findCategoriesList();
        List<CategoryDTO> categoryDTOList = list.stream().map(obj ->
                new CategoryDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDTOList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> findCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.findCategoryById(id));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable Long id) {
        category.setId(id);
        categoryService.updaterCategory(category);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}