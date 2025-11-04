package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Product;
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

    public static Product createProductWithAudit(String name, String description, BigDecimal price, String sku, String createdBy) {
        Product product = createProduct(name, description, price, sku);
        product.setCreatedBy(createdBy);
        return product;
    }
}