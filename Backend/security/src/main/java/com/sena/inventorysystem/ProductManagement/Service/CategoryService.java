package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Entity.Category;
import com.sena.inventorysystem.ProductManagement.Repository.CategoryRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new BusinessException("Categoría con nombre " + category.getName() + " ya existe");
        }
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id: " + id));

        if (!existingCategory.getName().equals(category.getName()) && categoryRepository.existsByName(category.getName())) {
            throw new ValidationException("Categoría con nombre " + category.getName() + " ya existe");
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDto(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Categoría no encontrada con id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id: " + id));
        return convertToDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con nombre: " + name));
        return convertToDto(category);
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}