package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.Entity.Category;
import com.sena.inventorysystem.ProductManagement.Repository.CategoryRepository;
import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new BusinessException("Categoría con nombre " + category.getName() + " ya existe");
        }
        category.setCreatedBy("system");
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public CategoryDto update(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada con id: " + id));

        if (!existingCategory.getName().equals(category.getName()) && categoryRepository.existsByName(category.getName())) {
            throw new BusinessException("Categoría con nombre " + category.getName() + " ya existe");
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setUpdatedBy("system");

        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDto(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Categoría no encontrada con id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada con id: " + id));
        return convertToDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada con nombre: " + name));
        return convertToDto(category);
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCreatedBy(),
                category.getUpdatedBy()
        );
    }
}