package com.sena.inventorysystem.OrderManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para la creación y actualización de clientes")
public class ClientDto {

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", required = true)
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 200, message = "El nombre no debe exceder 200 caracteres")
    private String name;

    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@example.com", required = false)
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Schema(description = "Número de teléfono del cliente", example = "+1234567890", required = false)
    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    private String phone;

    @Schema(description = "Dirección del cliente", example = "Calle 123, Ciudad, País", required = false)
    private String address;

    // Constructors
    public ClientDto() {}

    public ClientDto(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}