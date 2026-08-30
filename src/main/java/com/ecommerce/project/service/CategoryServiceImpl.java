package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.CategoryMapper;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.*;
@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty()) throw new APIException("No category created.");
        List<CategoryDTO> categoryDTOList=categoryMapper.toDtoList(categories);
        return new CategoryResponse(categoryDTOList);

    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=categoryMapper.toEntity(categoryDTO);
        Category savedCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null) throw new APIException("Category with the name "+category.getCategoryName()+" exists.");
        Category createdCategory=categoryRepository.save(category);
        return categoryMapper.toDto(createdCategory);
    }
    @Override
    public CategoryDTO deleteCategory(Long categoryId){
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        //Category category=savedCategoryOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        Category category=savedCategoryOptional.orElseThrow(()->new ResourceNotFoundException("Category","categoryID",categoryId));


        categoryRepository.delete(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        Category savedCategory=savedCategoryOptional.orElseThrow(()->new ResourceNotFoundException("Category","categoryID",categoryId));
        categoryMapper.updateEntityFromDto(categoryDTO,savedCategory);
        categoryRepository.save(savedCategory);
        return categoryMapper.toDto(savedCategory);
    }

}
