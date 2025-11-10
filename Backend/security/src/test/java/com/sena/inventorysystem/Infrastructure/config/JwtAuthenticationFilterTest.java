package com.sena.inventorysystem.Infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para JwtAuthenticationFilter
 *
 * Esta clase prueba el filtro de autenticación JWT que valida tokens
 * en las requests HTTP.
 */
public class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter();
        
        // Usar reflexión para acceder a campos privados
        try {
            Field jwtUtilField = JwtAuthenticationFilter.class.getDeclaredField("jwtUtil");
            jwtUtilField.setAccessible(true);
            jwtUtilField.set(jwtAuthenticationFilter, jwtUtil);
            
            Field userDetailsServiceField = JwtAuthenticationFilter.class.getDeclaredField("userDetailsService");
            userDetailsServiceField.setAccessible(true);
            userDetailsServiceField.set(jwtAuthenticationFilter, userDetailsService);
        } catch (Exception e) {
            throw new RuntimeException("Error configurando dependencias para test", e);
        }
    }

    /**
     * Prueba procesamiento de request sin header Authorization
     *
     * Escenario: Request sin token JWT
     * Entrada: HttpServletRequest sin header Authorization
     * Resultado esperado: Continúa la cadena de filtros sin autenticación
     */
    @Test
    public void testDoFilterInternalWithoutAuthorizationHeader() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).extractUsername(anyString());
    }

    /**
     * Prueba procesamiento de request con header Authorization válido
     *
     * Escenario: Request con token JWT válido
     * Entrada: HttpServletRequest con header "Bearer <token>"
     * Resultado esperado: Autenticación exitosa y continúa la cadena
     */
    @Test
    public void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        // Given
        String token = "valid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userDetails.getUsername()).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).validateToken(token, username);
        verify(userDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);
    }

    /**
     * Prueba procesamiento de request con token inválido
     *
     * Escenario: Request con token JWT inválido
     * Entrada: HttpServletRequest con header "Bearer <invalid_token>"
     * Resultado esperado: No establece autenticación pero continúa la cadena
     */
    @Test
    public void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
        // Given
        String token = "invalid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userDetails.getUsername()).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(false);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).validateToken(token, username);
        verify(filterChain).doFilter(request, response);
    }

    /**
     * Prueba procesamiento de request con header Authorization malformado
     *
     * Escenario: Request con header Authorization sin "Bearer "
     * Entrada: HttpServletRequest con header "Authorization: token"
     * Resultado esperado: No procesa el token y continúa la cadena
     */
    @Test
    public void testDoFilterInternalWithMalformedAuthorizationHeader() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn("invalid_token_format");

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(jwtUtil, never()).extractUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
}