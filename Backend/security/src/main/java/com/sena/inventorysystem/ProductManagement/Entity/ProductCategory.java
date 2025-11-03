package com.sena.inventorysystem.ProductManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructors
    public ProductCategory() {}

    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    // Getters and Setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}