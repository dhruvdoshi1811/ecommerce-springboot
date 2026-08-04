package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.*;
@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(Category category) {

        categoryRepository.save(category);
        return "Category added successfully";
    }
    public String deleteCategory(Long categoryId){
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        Category category=savedCategoryOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        categoryRepository.delete(category);
        return "Successfully Deleted";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        Category savedCategory=savedCategoryOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }

}
