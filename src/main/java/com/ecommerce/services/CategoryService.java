package com.ecommerce.services;

import com.ecommerce.domains.Category;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRep;

    public Category findCategoryById(Long id) {
        Optional<Category> category = categoryRep.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! id: " +
                id + ", Tipo: " + Category.class.getName()));
    }

    public List<Category> findCategoriesList() {
        return categoryRep.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRep.save(category);
    }

    public Category updateCategory(Category category) {
        findCategoryById(category.getId());
        return categoryRep.save(category);
    }

    public Category fromDTO(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getName());
    }

}
