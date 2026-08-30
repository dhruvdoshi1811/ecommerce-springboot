package com.ecommerce.project.service;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;


import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories();
    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public String deleteCategory(Long categoryId);
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Long categoryId);
}
