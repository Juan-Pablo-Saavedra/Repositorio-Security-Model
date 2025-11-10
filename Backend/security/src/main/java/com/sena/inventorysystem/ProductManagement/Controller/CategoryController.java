package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Service.ICategoryService;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto createdCategory = categoryService.createCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Categoría creada exitosamente", createdCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al crear la categoría: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
            if(updatedCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Categoría no encontrada con ID: " + id, null));
            }
            return ResponseEntity.ok(new ApiResponse(true, "Categoría actualizada exitosamente", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar la categoría: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Categoría eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar la categoría: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            CategoryDto category = categoryService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría encontrada", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Categoría no encontrada: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<CategoryDto> categories = categoryService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Categorías obtenidas exitosamente", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener categorías: " + e.getMessage(), null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            CategoryDto category = categoryService.findByName(name);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría encontrada", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Categoría no encontrada: " + e.getMessage(), null));
        }
    }
}