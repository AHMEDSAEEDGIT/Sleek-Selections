package com.ecommerce.sleekselects.repository;

import com.ecommerce.sleekselects.model.Category;
import com.ecommerce.sleekselects.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
