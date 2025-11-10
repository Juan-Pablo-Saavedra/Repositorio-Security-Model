package com.sena.inventorysystem.UserManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para la creación y actualización de usuarios")
public class UserDto {

    @Schema(description = "Nombre de usuario único", example = "johndoe", required = true)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "john.doe@example.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Schema(description = "Nombre del usuario", example = "John", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder 50 caracteres")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Doe", required = true)
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no debe exceder 50 caracteres")
    private String lastName;

    @Schema(description = "Número de teléfono del usuario", example = "+1234567890", required = false)
    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    private String phone;

    @Schema(description = "Dirección del usuario", example = "123 Main St, City, Country", required = false)
    private String address;

    // Constructors
    public UserDto() {}

    public UserDto(String username, String email, String password, String firstName, String lastName,
                  String phone, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}