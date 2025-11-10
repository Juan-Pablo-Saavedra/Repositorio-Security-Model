package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Category;
import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;

public class CategoryFactory {

    public static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public static Category createCategoryFromDto(CategoryDto dto) {
        return createCategory(dto.getName(), dto.getDescription());
    }

    public static CategoryDto createDtoFromCategory(Category category) {
        return new CategoryDto(
                category.getName(),
                category.getDescription()
        );
    }
}