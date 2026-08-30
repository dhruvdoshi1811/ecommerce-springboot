package com.ecommerce.project.mapper;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    Category toEntity(CategoryDTO categoryDTO);
    List<CategoryDTO> toDtoList(List<Category> categories);

    @Mapping(target = "categoryId", ignore = true)
    void updateEntityFromDto(CategoryDTO categoryDTO, @MappingTarget Category category);


}
