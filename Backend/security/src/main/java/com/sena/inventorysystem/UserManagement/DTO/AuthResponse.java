package com.sena.inventorysystem.UserManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación")
public class AuthResponse {

    @Schema(description = "Token JWT de autenticación", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Tipo de token", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre de usuario", example = "johndoe")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "john@example.com")
    private String email;

    @Schema(description = "Nombre del usuario", example = "John")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Doe")
    private String lastName;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String type, Long id, String username, String email, String firstName, String lastName) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}