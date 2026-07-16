package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.*;
@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categories=new ArrayList<>();
    private Long nextId=1l;
    private Set<Long> ids=new HashSet<>();
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
        return "Category added successfully";
    }
    public String deleteCategory(Long categoryId){
        Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if(category==null) return "Category not found.";
        categories.remove(category);
        return "Successfully Deleted";
    }

}
