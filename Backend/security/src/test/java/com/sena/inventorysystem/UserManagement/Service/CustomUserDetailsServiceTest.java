package com.sena.inventorysystem.UserManagement.Service;

import com.sena.inventorysystem.UserManagement.Entity.User;
import com.sena.inventorysystem.UserManagement.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CustomUserDetailsService
 *
 * Esta clase prueba la implementación de UserDetailsService,
 * que se encarga de cargar usuarios por username para autenticación.
 */
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear usuario de prueba
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword123");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhone("+1234567890");
        testUser.setAddress("Test Address");
    }

    /**
     * Prueba la carga exitosa de usuario por username
     *
     * Escenario: Cargar usuario existente
     * Entrada: Username válido
     * Resultado esperado: UserDetails con información correcta
     */
    @Test
    public void testLoadUserByUsernameSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword123", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        verify(userRepository).findByUsername("testuser");
    }

    /**
     * Prueba error al cargar usuario inexistente por username
     *
     * Escenario: Intentar cargar usuario que no existe
     * Entrada: Username que no existe
     * Resultado esperado: UsernameNotFoundException lanzada
     */
    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername("nonexistent"));
        
        assertTrue(exception.getMessage().contains("Usuario no encontrado: nonexistent"));
        verify(userRepository).findByUsername("nonexistent");
    }

    /**
     * Prueba con usuario que tiene username nulo
     *
     * Escenario: Intentar cargar usuario con username null
     * Entrada: Username null
     * Resultado esperado: UsernameNotFoundException lanzada
     */
    @Test
    public void testLoadUserByUsernameNull() {
        when(userRepository.findByUsername(null)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername(null));
        
        assertTrue(exception.getMessage().contains("Usuario no encontrado: null"));
        verify(userRepository).findByUsername(null);
    }

    /**
     * Prueba con usuario que tiene username vacío
     *
     * Escenario: Intentar cargar usuario con username vacío
     * Entrada: Username vacío
     * Resultado esperado: UsernameNotFoundException lanzada
     */
    @Test
    public void testLoadUserByUsernameEmpty() {
        when(userRepository.findByUsername("")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername(""));
        
        assertTrue(exception.getMessage().contains("Usuario no encontrado: "));
        verify(userRepository).findByUsername("");
    }

    /**
     * Prueba que el UserDetails tenga los roles correctos
     *
     * Escenario: Cargar usuario con roles específicos
     * Entrada: Usuario con rol USER
     * Resultado esperado: UserDetails con rol ROLE_USER
     */
    @Test
    public void testUserDetailsHasCorrectRole() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    /**
     * Prueba que el UserDetails no esté bloqueado
     *
     * Escenario: Cargar usuario activo
     * Entrada: Usuario válido
     * Resultado esperado: UserDetails con enabled=true
     */
    @Test
    public void testUserDetailsIsEnabled() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertTrue(userDetails.isEnabled());
    }

    /**
     * Prueba que el UserDetails no esté expirado
     *
     * Escenario: Cargar usuario activo
     * Entrada: Usuario válido
     * Resultado esperado: UserDetails con no expirado
     */
    @Test
    public void testUserDetailsIsNotExpired() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertTrue(userDetails.isAccountNonExpired());
    }

    /**
     * Prueba que la cuenta no esté bloqueada
     *
     * Escenario: Cargar usuario activo
     * Entrada: Usuario válido
     * Resultado esperado: UserDetails con cuenta no bloqueada
     */
    @Test
    public void testUserDetailsIsNotLocked() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertTrue(userDetails.isAccountNonLocked());
    }

    /**
     * Prueba que las credenciales no estén expiradas
     *
     * Escenario: Cargar usuario activo
     * Entrada: Usuario válido
     * Resultado esperado: UserDetails con credenciales no expiradas
     */
    @Test
    public void testUserCredentialsAreNotExpired() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertTrue(userDetails.isCredentialsNonExpired());
    }
}