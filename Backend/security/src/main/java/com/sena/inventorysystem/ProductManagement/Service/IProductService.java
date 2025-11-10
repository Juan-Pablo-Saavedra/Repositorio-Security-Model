package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import com.sena.inventorysystem.ProductManagement.Entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    ProductDto create(Product product);

    ProductDto createProduct(ProductDto productDto);

    ProductDto update(Long id, Product product);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void delete(Long id);

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    ProductDto findBySku(String sku);

    List<ProductDto> findByName(String name);

    List<ProductDto> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}