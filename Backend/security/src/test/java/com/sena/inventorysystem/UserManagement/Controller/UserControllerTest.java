package com.sena.inventorysystem.UserManagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.inventorysystem.UserManagement.DTO.AuthRequest;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.Service.IUserService;
import com.sena.inventorysystem.UserManagement.Repository.UserRepository;
import com.sena.inventorysystem.Infrastructure.config.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.sena.inventorysystem.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para UserController
 *
 * Esta clase prueba los endpoints REST del controlador de usuarios usando MockMvc,
 * simulando requests HTTP reales y verificando responses completas.
 */
@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private Authentication authentication;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto createTestUserDto() {
        UserDto dto = new UserDto();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setPhone("1234567890");
        dto.setAddress("Test Address");
        return dto;
    }

    private AuthRequest createTestAuthRequest() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        return request;
    }

    /**
     * Prueba el registro exitoso de usuario
     *
     * Escenario: POST /api/users/register con datos válidos
     * Entrada: UserDto válido en JSON
     * Resultado esperado: HTTP 201 Created con mensaje de éxito
     */
    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Given
        UserDto requestDto = createTestUserDto();
        when(userService.createUser(any(UserDto.class))).thenReturn(requestDto);

        // When & Then
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    /**
     * Prueba error en registro de usuario
     *
     * Escenario: POST /api/users/register con datos inválidos
     * Entrada: UserDto que causa excepción en servicio
     * Resultado esperado: HTTP 400 Bad Request
     */
    @Test
    public void testRegisterUserError() throws Exception {
        // Given
        UserDto requestDto = createTestUserDto();
        when(userService.createUser(any(UserDto.class)))
            .thenThrow(new RuntimeException("Error de validación"));

        // When & Then
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al registrar el usuario: Error de validación"));
    }

    /**
     * Prueba login exitoso
     *
     * Escenario: POST /api/users/login con credenciales válidas
     * Entrada: AuthRequest con username y password correctos
     * Resultado esperado: HTTP 200 OK con AuthResponse
     */
    @Test
    public void testLoginSuccess() throws Exception {
        // Given
        AuthRequest request = createTestAuthRequest();
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(testUser));
        when(jwtUtil.generateToken("testuser", 1L, "test@example.com")).thenReturn("jwt.token.here");

        // When & Then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login exitoso"))
                .andExpect(jsonPath("$.data.token").value("jwt.token.here"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    /**
     * Prueba login con credenciales inválidas
     *
     * Escenario: POST /api/users/login con credenciales incorrectas
     * Entrada: AuthRequest con credenciales inválidas
     * Resultado esperado: HTTP 401 Unauthorized
     */
    @Test
    public void testLoginBadCredentials() throws Exception {
        // Given
        AuthRequest request = createTestAuthRequest();
        when(authenticationManager.authenticate(any()))
            .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        // When & Then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
    }

    /**
     * Prueba obtención de usuario por ID exitoso
     *
     * Escenario: GET /api/users/{id} con ID existente
     * Entrada: ID válido
     * Resultado esperado: HTTP 200 OK con UserDto
     */
    @Test
    public void testFindByIdSuccess() throws Exception {
        // Given
        UserDto responseDto = createTestUserDto();
        when(userService.findById(1L)).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario encontrado"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    /**
     * Prueba error al obtener usuario por ID inexistente
     *
     * Escenario: GET /api/users/{id} con ID inexistente
     * Entrada: ID que no existe
     * Resultado esperado: HTTP 404 Not Found
     */
    @Test
    public void testFindByIdNotFound() throws Exception {
        // Given
        when(userService.findById(999L))
            .thenThrow(new com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException("Usuario no encontrado"));

        // When & Then
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * Prueba obtención de todos los usuarios
     *
     * Escenario: GET /api/users para listar todos los usuarios
     * Entrada: Sin parámetros
     * Resultado esperado: HTTP 200 OK con lista de UserDto
     */
    @Test
    public void testFindAll() throws Exception {
        // Given
        List<UserDto> users = Arrays.asList(createTestUserDto());
        when(userService.findAll()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuarios obtenidos exitosamente"))
                .andExpect(jsonPath("$.data[0].username").value("testuser"));
    }

    /**
     * Prueba búsqueda de usuario por username
     *
     * Escenario: GET /api/users/username/{username}
     * Entrada: Username válido
     * Resultado esperado: HTTP 200 OK con UserDto
     */
    @Test
    public void testFindByUsernameSuccess() throws Exception {
        // Given
        UserDto responseDto = createTestUserDto();
        when(userService.findByUsername("testuser")).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario encontrado"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    /**
     * Prueba búsqueda de usuario por email
     *
     * Escenario: GET /api/users/email/{email}
     * Entrada: Email válido
     * Resultado esperado: HTTP 200 OK con UserDto
     */
    @Test
    public void testFindByEmailSuccess() throws Exception {
        // Given
        UserDto responseDto = createTestUserDto();
        when(userService.findByEmail("test@example.com")).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario encontrado"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }

    /**
     * Prueba actualización exitosa de usuario
     *
     * Escenario: PUT /api/users/{id} con datos válidos
     * Entrada: ID válido y UserDto con datos actualizados
     * Resultado esperado: HTTP 200 OK
     */
    @Test
    public void testUpdateUserSuccess() throws Exception {
        // Given
        UserDto requestDto = createTestUserDto();
        UserDto responseDto = createTestUserDto();
        responseDto.setFirstName("Updated Name");

        when(userService.update(anyLong(), any(User.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario actualizado exitosamente"));
    }

    /**
     * Prueba eliminación exitosa de usuario
     *
     * Escenario: DELETE /api/users/{id} con ID válido
     * Entrada: ID de usuario existente
     * Resultado esperado: HTTP 200 OK
     */
    @Test
    public void testDeleteUserSuccess() throws Exception {
        // Given
        doNothing().when(userService).delete(1L);

        // When & Then
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario eliminado exitosamente"));
    }

    /**
     * Prueba verificación de existencia por username
     *
     * Escenario: GET /api/users/check-username/{username}
     * Entrada: Username a verificar
     * Resultado esperado: HTTP 200 OK con boolean
     */
    @Test
    public void testExistsByUsername() throws Exception {
        // Given
        when(userService.existsByUsername("testuser")).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/api/users/check-username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Verificación completada"))
                .andExpect(jsonPath("$.data").value(true));
    }

    /**
     * Prueba verificación de existencia por email
     *
     * Escenario: GET /api/users/check-email/{email}
     * Entrada: Email a verificar
     * Resultado esperado: HTTP 200 OK con boolean
     */
    @Test
    public void testExistsByEmail() throws Exception {
        // Given
        when(userService.existsByEmail("test@example.com")).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/api/users/check-email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Verificación completada"))
                .andExpect(jsonPath("$.data").value(false));
    }

    /**
     * Prueba manejo de errores generales en controladores
     *
     * Escenario: Cualquier endpoint que lance RuntimeException
     * Entrada: Servicio que lanza excepción
     * Resultado esperado: HTTP 500 Internal Server Error
     */
    @Test
    public void testGeneralErrorHandling() throws Exception {
        // Given
        when(userService.findAll())
            .thenThrow(new RuntimeException("Error interno del servidor"));

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al obtener usuarios: Error interno del servidor"));
    }
}