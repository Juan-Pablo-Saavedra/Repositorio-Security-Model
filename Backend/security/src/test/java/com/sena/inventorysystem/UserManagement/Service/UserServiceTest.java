package com.sena.inventorysystem.UserManagement.Service;

import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.DTO.UserDto;
import com.sena.inventorysystem.UserManagement.Repository.UserRepository;
import com.sena.inventorysystem.UserManagement.Factory.UserFactory;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UserService
 *
 * Esta clase prueba la lógica de negocio del servicio de usuarios,
 * incluyendo creación, actualización, eliminación y validaciones.
 */
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar datos de prueba
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhone("1234567890");
        testUser.setAddress("Test Address");

        testUserDto = new UserDto();
        testUserDto.setUsername("testuser");
        testUserDto.setEmail("test@example.com");
        testUserDto.setFirstName("Test");
        testUserDto.setLastName("User");
        testUserDto.setPhone("1234567890");
        testUserDto.setAddress("Test Address");
    }

    /**
     * Prueba la creación exitosa de un usuario
     *
     * Escenario: Crear usuario con datos válidos
     * Entrada: UserDto con datos completos y válidos
     * Resultado esperado: Usuario creado y UserDto retornado
     */
    @Test
    public void testCreateUserSuccess() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userFactory.createUserFromDto(any(UserDto.class), anyString())).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userFactory.createDtoFromUser(any(User.class))).thenReturn(testUserDto);

        // When
        UserDto result = userService.createUser(testUserDto);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    /**
     * Prueba error al crear usuario con username duplicado
     *
     * Escenario: Intentar crear usuario con username que ya existe
     * Entrada: UserDto con username ya existente
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testCreateUserDuplicateUsername() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> userService.createUser(testUserDto));
        assertTrue(exception.getMessage().contains("username"));
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Prueba error al crear usuario con email duplicado
     *
     * Escenario: Intentar crear usuario con email que ya existe
     * Entrada: UserDto con email ya existente
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testCreateUserDuplicateEmail() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> userService.createUser(testUserDto));
        assertTrue(exception.getMessage().contains("email"));
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Prueba la obtención de usuario por ID exitoso
     *
     * Escenario: Buscar usuario existente por ID
     * Entrada: ID válido de usuario existente
     * Resultado esperado: UserDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userFactory.createDtoFromUser(testUser)).thenReturn(testUserDto);

        // When
        UserDto result = userService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    /**
     * Prueba error al buscar usuario por ID inexistente
     *
     * Escenario: Buscar usuario con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la obtención de todos los usuarios
     *
     * Escenario: Listar todos los usuarios del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de UserDto retornada
     */
    @Test
    public void testFindAll() {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userFactory.createDtoFromUser(testUser)).thenReturn(testUserDto);

        // When
        List<UserDto> result = userService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    /**
     * Prueba la búsqueda de usuario por username
     *
     * Escenario: Buscar usuario por username único
     * Entrada: Username válido
     * Resultado esperado: UserDto encontrado
     */
    @Test
    public void testFindByUsernameSuccess() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userFactory.createDtoFromUser(testUser)).thenReturn(testUserDto);

        // When
        UserDto result = userService.findByUsername("testuser");

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    /**
     * Prueba error al buscar usuario por username inexistente
     *
     * Escenario: Buscar usuario con username que no existe
     * Entrada: Username inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByUsernameNotFound() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userService.findByUsername("nonexistent"));
        assertTrue(exception.getMessage().contains("nonexistent"));
    }

    /**
     * Prueba la búsqueda de usuario por email
     *
     * Escenario: Buscar usuario por email único
     * Entrada: Email válido
     * Resultado esperado: UserDto encontrado
     */
    @Test
    public void testFindByEmailSuccess() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userFactory.createDtoFromUser(testUser)).thenReturn(testUserDto);

        // When
        UserDto result = userService.findByEmail("test@example.com");

        // Then
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    /**
     * Prueba la actualización exitosa de usuario
     *
     * Escenario: Actualizar datos de usuario existente
     * Entrada: ID válido y User con datos actualizados
     * Resultado esperado: UserDto actualizado retornado
     */
    @Test
    public void testUpdateSuccess() {
        // Given
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userFactory.createDtoFromUser(any(User.class))).thenReturn(testUserDto);

        // When
        UserDto result = userService.update(1L, updatedUser);

        // Then
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    /**
     * Prueba error al actualizar usuario inexistente
     *
     * Escenario: Intentar actualizar usuario con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateNotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userService.update(999L, testUser));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la eliminación exitosa de usuario
     *
     * Escenario: Eliminar usuario existente
     * Entrada: ID válido de usuario existente
     * Resultado esperado: Usuario eliminado sin excepciones
     */
    @Test
    public void testDeleteSuccess() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        userService.delete(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar usuario inexistente
     *
     * Escenario: Intentar eliminar usuario con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteNotFound() {
        // Given
        when(userRepository.existsById(999L)).thenReturn(false);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba verificación de existencia por username
     *
     * Escenario: Verificar si username existe
     * Entrada: Username a verificar
     * Resultado esperado: Boolean indicando existencia
     */
    @Test
    public void testExistsByUsername() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        when(userRepository.existsByUsername("nonexistent")).thenReturn(false);

        // When
        boolean exists = userService.existsByUsername("testuser");
        boolean notExists = userService.existsByUsername("nonexistent");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    /**
     * Prueba verificación de existencia por email
     *
     * Escenario: Verificar si email existe
     * Entrada: Email a verificar
     * Resultado esperado: Boolean indicando existencia
     */
    @Test
    public void testExistsByEmail() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        // When
        boolean exists = userService.existsByEmail("test@example.com");
        boolean notExists = userService.existsByEmail("nonexistent@example.com");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }
}