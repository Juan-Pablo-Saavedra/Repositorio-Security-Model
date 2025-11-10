package com.sena.inventorysystem.ProductManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para la creación y actualización de categorías")
public class CategoryDto {

    @Schema(description = "Nombre único de la categoría", example = "Electrónicos", required = true)
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    private String name;

    @Schema(description = "Descripción de la categoría", example = "Productos electrónicos y gadgets", required = false)
    private String description;

    // Constructors
    public CategoryDto() {}

    public CategoryDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}