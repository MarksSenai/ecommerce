package com.ecommerce.resources;

import com.ecommerce.domains.Category;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.fromDTO(categoryDTO);
        category = categoryService.createCategory(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Category> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        Category category = categoryService.fromDTO(categoryDTO);
        categoryDTO.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.noContent().build();
    }
}