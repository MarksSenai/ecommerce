package com.ecommerce.services;

import com.ecommerce.domains.Category;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.services.exceptions.DataIntegrityException;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        return categoryRep.findAll(pageRequest);
    }

    public Category createCategory(Category category) {
        category.setId(null);
        return categoryRep.save(category);
    }

    public Category updaterCategory(Category category) {
        findCategoryById(category.getId());
        return categoryRep.save(category);
    }

    public void deleteById(Long id) {
        findCategoryById(id);
        try {
            categoryRep.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possua produtos cadastrados");
        }
    }

}