package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.CategoryMapper;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);
        List<Category> categories=categoryPage.getContent();
        if(categories.isEmpty()) throw new APIException("No category created.");
        List<CategoryDTO> categoryDTOList=categoryMapper.toDtoList(categories);
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        categoryResponse.setPageSize(pageSize);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;

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
