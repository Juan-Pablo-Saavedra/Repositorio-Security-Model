package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.Service.ProductService;
import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Gestión de Productos", description = "API para gestión de productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product) {
        ProductDto createdProduct = productService.create(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtiene la lista de todos los productos")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Obtener producto por SKU", description = "Obtiene un producto por su SKU")
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String sku) {
        ProductDto product = productService.findBySku(sku);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que contengan el nombre especificado")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String name) {
        List<ProductDto> products = productService.findByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Buscar productos por rango de precio", description = "Busca productos dentro de un rango de precios")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<ProductDto> products = productService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        ProductDto updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}