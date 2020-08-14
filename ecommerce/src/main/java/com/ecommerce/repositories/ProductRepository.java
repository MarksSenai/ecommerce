package com.ecommerce.repositories;

import java.util.List;

import com.ecommerce.domains.Category;
import com.ecommerce.domains.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT prod FROM Product prod INNER JOIN prod.categories cat WHERE prod.name LIKE %:name% AND cat IN :categories")
    Page<Product> search(@Param("name") String name, @Param("categories")  List<Category> categories, Pageable pageRequest);

}
