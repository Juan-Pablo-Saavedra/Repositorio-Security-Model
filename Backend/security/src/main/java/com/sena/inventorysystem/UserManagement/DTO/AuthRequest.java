package com.sena.inventorysystem.UserManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de autenticación")
public class AuthRequest {

    @Schema(description = "Nombre de usuario", example = "johndoe", required = true)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // Constructors
    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}