package com.sena.inventorysystem.ProductManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "DTO para la creación y actualización de productos")
public class ProductDto {

    @Schema(description = "Nombre del producto", example = "iPhone 15 Pro", required = true)
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    private String name;

    @Schema(description = "Descripción del producto", example = "Smartphone de última generación con chip A17", required = false)
    @Size(max = 1000, message = "La descripción no debe exceder 1000 caracteres")
    private String description;

    @Schema(description = "Precio del producto", example = "999.99", required = true)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @Schema(description = "Código SKU único del producto", example = "IPH15PRO-128GB", required = true)
    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 50, message = "El SKU no debe exceder 50 caracteres")
    private String sku;

    // Constructors
    public ProductDto() {}

    public ProductDto(String name, String description, BigDecimal price, String sku) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}