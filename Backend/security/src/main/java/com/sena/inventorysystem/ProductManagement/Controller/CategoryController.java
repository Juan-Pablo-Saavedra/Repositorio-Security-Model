package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.Entity.Category;
import com.sena.inventorysystem.ProductManagement.Service.CategoryService;
import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Gestión de Categorías", description = "API para gestión de categorías de productos")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody Category category) {
        CategoryDto createdCategory = categoryService.create(category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría específica por su ID")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Obtiene la lista de todas las categorías")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Obtener categoría por nombre", description = "Obtiene una categoría por su nombre")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        CategoryDto category = categoryService.findByName(name);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza la información de una categoría")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        CategoryDto updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del sistema")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}