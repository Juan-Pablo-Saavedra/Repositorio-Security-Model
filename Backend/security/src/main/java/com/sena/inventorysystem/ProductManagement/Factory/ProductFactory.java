package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;

import java.math.BigDecimal;

public class ProductFactory {

    public static Product createProduct(String name, String description, BigDecimal price, String sku) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSku(sku);
        return product;
    }

    public static Product createProductFromDto(ProductDto dto) {
        return createProduct(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getSku());
    }

    public static ProductDto createDtoFromProduct(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku()
        );
    }
}