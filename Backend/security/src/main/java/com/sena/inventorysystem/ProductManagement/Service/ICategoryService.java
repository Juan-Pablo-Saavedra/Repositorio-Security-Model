package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Entity.Category;

import java.util.List;

public interface ICategoryService {

    CategoryDto create(Category category);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto update(Long id, Category category);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void delete(Long id);

    CategoryDto findById(Long id);

    List<CategoryDto> findAll();

    CategoryDto findByName(String name);
}