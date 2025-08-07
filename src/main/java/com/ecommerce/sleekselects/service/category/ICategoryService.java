package com.ecommerce.sleekselects.service.category;

import com.ecommerce.sleekselects.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category );
    Category updateCategory(Category category ,Long id);
    void deleteCategoryById(Long id);

}
