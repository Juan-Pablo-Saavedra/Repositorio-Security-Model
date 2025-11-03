package com.sena.inventorysystem.ProductManagement.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    // Constructors
    public ProductDto() {}

    public ProductDto(Long id, String name, String description, BigDecimal price, String sku,
                     LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}