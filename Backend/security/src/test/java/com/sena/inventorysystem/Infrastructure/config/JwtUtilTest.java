package com.sena.inventorysystem.Infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para JwtUtil
 *
 * Esta clase prueba la funcionalidad de generación y validación de tokens JWT
 * utilizada en el sistema de autenticación.
 */
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String testSecret = "testSecretKeyForJwtUtilTestingPurposesOnly";

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        // Inyectar la clave secreta de prueba usando reflexión
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", 3600000L); // 1 hora
    }

    /**
     * Prueba la generación de token JWT
     *
     * Escenario: Generar token con username, userId y email
     * Entrada: username="testuser", userId=1L, email="test@example.com"
     * Resultado esperado: Token JWT válido generado
     */
    @Test
    public void testGenerateToken() {
        // Given
        String username = "testuser";
        Long userId = 1L;
        String email = "test@example.com";

        // When
        String token = jwtUtil.generateToken(username, userId, email);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ")); // JWT tokens start with "eyJ"
    }

    /**
     * Prueba la extracción del username desde token
     *
     * Escenario: Extraer username de un token generado
     * Entrada: Token generado con username="testuser"
     * Resultado esperado: Username correcto extraído
     */
    @Test
    public void testExtractUsername() {
        // Given
        String username = "testuser";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        String extractedUsername = jwtUtil.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    /**
     * Prueba la validación de token válido
     *
     * Escenario: Validar token que no ha expirado
     * Entrada: Token generado recientemente
     * Resultado esperado: Token válido (true)
     */
    @Test
    public void testValidateTokenValid() {
        // Given
        String username = "testuser";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        boolean isValid = jwtUtil.validateToken(token, username);

        // Then
        assertTrue(isValid);
    }

    /**
     * Prueba la validación de token con username incorrecto
     *
     * Escenario: Validar token con username diferente al del token
     * Entrada: Token generado con "testuser" pero validado con "wronguser"
     * Resultado esperado: Token inválido (false)
     */
    @Test
    public void testValidateTokenInvalidUsername() {
        // Given
        String username = "testuser";
        String wrongUsername = "wronguser";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        boolean isValid = jwtUtil.validateToken(token, wrongUsername);

        // Then
        assertFalse(isValid);
    }

    /**
     * Prueba la extracción de userId desde token
     *
     * Escenario: Extraer userId de un token generado
     * Entrada: Token generado con userId=123L
     * Resultado esperado: userId correcto extraído
     */
    @Test
    public void testExtractUserId() {
        // Given
        String username = "testuser";
        Long userId = 123L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        Long extractedUserId = jwtUtil.extractUserId(token);

        // Then
        assertEquals(userId, extractedUserId);
    }

    /**
     * Prueba la extracción de email desde token
     *
     * Escenario: Extraer email de un token generado
     * Entrada: Token generado con email="test@example.com"
     * Resultado esperado: Email correcto extraído
     */
    @Test
    public void testExtractEmail() {
        // Given
        String username = "testuser";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        String extractedEmail = jwtUtil.extractEmail(token);

        // Then
        assertEquals(email, extractedEmail);
    }

    /**
     * Prueba token expirado
     *
     * Escenario: Crear token con tiempo de expiración muy corto y esperar
     * Entrada: Token con expiración de 1 milisegundo
     * Resultado esperado: Token expirado después de esperar
     */
    @Test
    public void testTokenExpiration() throws InterruptedException {
        // Given - Token con expiración muy corta
        ReflectionTestUtils.setField(jwtUtil, "expiration", 1L); // 1ms
        String username = "testuser";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When - Esperar a que expire
        Thread.sleep(5); // Esperar 5ms

        // Then - Token debería ser inválido
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(token));
    }

    /**
     * Prueba token malformado
     *
     * Escenario: Intentar validar un token malformado
     * Entrada: String que no es un token JWT válido
     * Resultado esperado: Excepción al procesar
     */
    @Test
    public void testMalformedToken() {
        // Given
        String malformedToken = "this.is.not.a.valid.jwt.token";

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(malformedToken));
        assertThrows(Exception.class, () -> jwtUtil.validateToken(malformedToken, "anyuser"));
    }

    /**
     * Prueba token vacío o nulo
     *
     * Escenario: Intentar procesar tokens vacíos o nulos
     * Entrada: Token vacío o null
     * Resultado esperado: Manejo correcto de casos edge
     */
    @Test
    public void testEmptyOrNullToken() {
        // Test empty token
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(""));
        assertThrows(Exception.class, () -> jwtUtil.validateToken("", "user"));

        // Test null token
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(null));
        assertThrows(Exception.class, () -> jwtUtil.validateToken(null, "user"));
    }

    /**
     * Prueba claims con caracteres especiales
     *
     * Escenario: Generar token con username que contiene caracteres especiales
     * Entrada: Username con caracteres especiales
     * Resultado esperado: Token generado y validado correctamente
     */
    @Test
    public void testTokenWithSpecialCharacters() {
        // Given
        String username = "test.user+special@example.com";
        Long userId = 1L;
        String email = "test@example.com";
        String token = jwtUtil.generateToken(username, userId, email);

        // When
        String extractedUsername = jwtUtil.extractUsername(token);
        boolean isValid = jwtUtil.validateToken(token, username);

        // Then
        assertEquals(username, extractedUsername);
        assertTrue(isValid);
    }

    /**
     * Prueba múltiples tokens generados
     *
     * Escenario: Generar múltiples tokens y verificar que sean únicos
     * Entrada: Múltiples llamadas a generateToken
     * Resultado esperado: Tokens únicos generados
     */
    @Test
    public void testMultipleTokensAreUnique() {
        // Given
        String username1 = "user1";
        String username2 = "user2";
        Long userId = 1L;
        String email = "test@example.com";

        // When
        String token1 = jwtUtil.generateToken(username1, userId, email);
        String token2 = jwtUtil.generateToken(username2, userId, email);

        // Then
        assertNotEquals(token1, token2);
        assertNotEquals(jwtUtil.extractUsername(token1), jwtUtil.extractUsername(token2));
    }
}