package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import com.sena.inventorysystem.ProductManagement.Service.IProductService;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        try {
            ProductDto createdProduct = productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Producto creado exitosamente", createdProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al crear el producto: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);
            if(updatedProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Producto no encontrado con ID: " + id, null));
            }
            return ResponseEntity.ok(new ApiResponse(true, "Producto actualizado exitosamente", updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar el producto: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Producto eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar el producto: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ProductDto product = productService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto encontrado", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Producto no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProductDto> products = productService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Productos obtenidos exitosamente", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener productos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<?> findBySku(@PathVariable String sku) {
        try {
            ProductDto product = productService.findBySku(sku);
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto encontrado", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Producto no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        try {
            List<ProductDto> products = productService.findByName(name);
            return ResponseEntity.ok(new ApiResponse<>(true, "Productos encontrados", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar productos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> findByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        try {
            List<ProductDto> products = productService.findByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(new ApiResponse<>(true, "Productos encontrados en el rango de precios", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar productos por rango de precios: " + e.getMessage(), null));
        }
    }
}