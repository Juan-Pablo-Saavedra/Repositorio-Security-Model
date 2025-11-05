package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Entity.Category;

import java.util.List;

public interface ICategoryService {

    CategoryDto create(Category category);

    CategoryDto update(Long id, Category category);

    void delete(Long id);

    CategoryDto findById(Long id);

    List<CategoryDto> findAll();

    CategoryDto findByName(String name);
}