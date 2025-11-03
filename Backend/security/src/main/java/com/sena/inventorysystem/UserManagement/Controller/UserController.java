package com.sena.inventorysystem.UserManagement.Controller;

import com.sena.inventorysystem.UserManagement.DTO.AuthRequest;
import com.sena.inventorysystem.UserManagement.DTO.AuthResponse;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Gestión de Usuarios", description = "API para gestión de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        AuthResponse authResponse = userService.register(user);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve información de sesión")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = userService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información de un usuario específico")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene la lista de todos los usuarios")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        UserDto userDto = userService.update(id, user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getCreatedBy(),
                user.getUpdatedBy()
        );
    }
}