package com.sena.inventorysystem.UserManagement.Utils;

public class UserConstants {

    // User roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // Default values
    public static final String DEFAULT_ROLE = ROLE_USER;

    // Error messages
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USERNAME_ALREADY_EXISTS = "Nombre de usuario ya existe";
    public static final String EMAIL_ALREADY_EXISTS = "Email ya existe";
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";

    // Success messages
    public static final String USER_REGISTERED = "Usuario registrado exitosamente";
    public static final String USER_UPDATED = "Usuario actualizado exitosamente";
    public static final String USER_DELETED = "Usuario eliminado exitosamente";
    public static final String LOGIN_SUCCESSFUL = "Inicio de sesión exitoso";

    private UserConstants() {
        // Utility class
    }
}