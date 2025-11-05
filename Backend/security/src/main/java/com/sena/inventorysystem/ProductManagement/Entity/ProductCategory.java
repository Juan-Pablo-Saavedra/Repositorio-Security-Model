package com.sena.inventorysystem.ProductManagement.Entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    @EmbeddedId
    private ProductCategoryId id;

    // Constructors
    public ProductCategory() {}

    public ProductCategory(Product product, Category category) {
        this.id = new ProductCategoryId(product.getId(), category.getId());
    }

    // Getters and Setters
    public ProductCategoryId getId() { return id; }
    public void setId(ProductCategoryId id) { this.id = id; }

    @Embeddable
    public static class ProductCategoryId implements Serializable {
        private Long productId;
        private Long categoryId;

        public ProductCategoryId() {}

        public ProductCategoryId(Long productId, Long categoryId) {
            this.productId = productId;
            this.categoryId = categoryId;
        }

        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductCategoryId that = (ProductCategoryId) o;
            return productId.equals(that.productId) && categoryId.equals(that.categoryId);
        }

        @Override
        public int hashCode() {
            return productId.hashCode() + categoryId.hashCode();
        }
    }
}