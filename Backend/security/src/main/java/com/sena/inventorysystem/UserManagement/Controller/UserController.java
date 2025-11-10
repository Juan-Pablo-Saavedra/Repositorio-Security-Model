package com.sena.inventorysystem.UserManagement.Controller;

import com.sena.inventorysystem.UserManagement.DTO.AuthRequest;
import com.sena.inventorysystem.UserManagement.DTO.AuthResponse;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.Service.IUserService;
import com.sena.inventorysystem.UserManagement.Repository.UserRepository;
import com.sena.inventorysystem.Infrastructure.config.JwtUtil;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Usuario registrado exitosamente", createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al registrar el usuario: " + e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );

            if (authentication.isAuthenticated()) {
                // Obtener información del usuario para el token
                User user = userRepository.findByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
                String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getEmail());

                AuthResponse authResponse = new AuthResponse(
                    token,
                    "Bearer",
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName()
                );

                return ResponseEntity.ok(new ApiResponse(true, "Login exitoso", authResponse));
            } else {
                throw new BadCredentialsException("Credenciales inválidas");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Credenciales inválidas", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error en el login: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());

            UserDto updatedUser = userService.update(id, user);
            return ResponseEntity.ok(new ApiResponse(true, "Usuario actualizado exitosamente", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar el usuario: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Usuario eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar el usuario: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            UserDto user = userService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<UserDto> users = userService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos exitosamente", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener usuarios: " + e.getMessage(), null));
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            UserDto user = userService.findByUsername(username);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            UserDto user = userService.findByEmail(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> existsByUsername(@PathVariable String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return ResponseEntity.ok(new ApiResponse<>(true, "Verificación completada", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al verificar usuario: " + e.getMessage(), null));
        }
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> existsByEmail(@PathVariable String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Verificación completada", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al verificar email: " + e.getMessage(), null));
        }
    }
}