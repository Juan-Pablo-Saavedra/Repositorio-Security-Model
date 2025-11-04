package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Category;

public class CategoryFactory {

    public static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public static Category createCategoryWithAudit(String name, String description, String createdBy) {
        Category category = createCategory(name, description);
        category.setCreatedBy(createdBy);
        return category;
    }
}