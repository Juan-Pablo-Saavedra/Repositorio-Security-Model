package com.sena.inventorysystem.ProductManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para la creación y actualización de proveedores")
public class SupplierDto {

    @Schema(description = "Nombre del proveedor", example = "TechCorp S.A.", required = true)
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    private String name;

    @Schema(description = "Correo electrónico de contacto del proveedor", example = "contacto@techcorp.com", required = true)
    @NotBlank(message = "El correo electrónico de contacto es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String contactEmail;

    @Schema(description = "Número de teléfono de contacto del proveedor", example = "+1234567890", required = false)
    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    private String contactPhone;

    @Schema(description = "Dirección del proveedor", example = "Av. Principal 123, Ciudad, País", required = false)
    @Size(max = 500, message = "La dirección no debe exceder 500 caracteres")
    private String address;

    // Constructors
    public SupplierDto() {}

    public SupplierDto(String name, String contactEmail, String contactPhone, String address) {
        this.name = name;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.address = address;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}