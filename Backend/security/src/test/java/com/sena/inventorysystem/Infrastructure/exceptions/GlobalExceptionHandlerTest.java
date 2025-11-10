package com.sena.inventorysystem.Infrastructure.exceptions;

import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para GlobalExceptionHandler
 *
 * Esta clase prueba el manejo global de excepciones para asegurar
 * que todas las excepciones se convierten correctamente en respuestas HTTP.
 */
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandler = new GlobalExceptionHandler();
    }

    /**
     * Prueba el manejo de BusinessException
     *
     * Escenario: BusinessException lanzada desde servicio
     * Entrada: BusinessException con mensaje "Regla de negocio violada"
     * Resultado esperado: ResponseEntity con BAD_REQUEST y ApiResponse estructurada
     */
    @Test
    public void testHandleBusinessException() {
        // Given
        String message = "Regla de negocio violada";
        BusinessException exception = new BusinessException(message);

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleBusinessException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(message, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    /**
     * Prueba el manejo de NotFoundException
     *
     * Escenario: Recurso no encontrado en base de datos
     * Entrada: NotFoundException con mensaje "Usuario no encontrado"
     * Resultado esperado: ResponseEntity con NOT_FOUND y ApiResponse estructurada
     */
    @Test
    public void testHandleNotFoundException() {
        // Given
        String message = "Usuario no encontrado con ID: 123";
        NotFoundException exception = new NotFoundException(message);

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleNotFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(message, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    /**
     * Prueba el manejo de ValidationException
     *
     * Escenario: Error de validación en datos de entrada
     * Entrada: ValidationException con mensaje "Datos inválidos"
     * Resultado esperado: ResponseEntity con BAD_REQUEST y ApiResponse estructurada
     */
    @Test
    public void testHandleValidationException() {
        // Given
        String message = "El precio debe ser mayor a cero";
        ValidationException exception = new ValidationException(message);

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleValidationException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(message, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    /**
     * Prueba el manejo de MethodArgumentNotValidException
     *
     * Escenario: Validación fallida en DTO con anotaciones @Valid
     * Entrada: MethodArgumentNotValidException con errores de campo
     * Resultado esperado: ResponseEntity con BAD_REQUEST y mapa de errores
     */
    @Test
    public void testHandleValidationExceptions() {
        // Given
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("productDto", "name", "El nombre es obligatorio"));
        fieldErrors.add(new FieldError("productDto", "price", "El precio debe ser mayor a cero"));

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // When
        ResponseEntity<ApiResponse<Map<String, String>>> response =
            exceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Errores de validación en los campos", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());

        Map<String, String> errors = response.getBody().getData();
        assertEquals(2, errors.size());
        assertEquals("El nombre es obligatorio", errors.get("name"));
        assertEquals("El precio debe ser mayor a cero", errors.get("price"));
    }

    /**
     * Prueba el manejo de excepciones generales
     *
     * Escenario: Excepción no manejada específicamente (NullPointerException, etc.)
     * Entrada: RuntimeException genérica
     * Resultado esperado: ResponseEntity con INTERNAL_SERVER_ERROR
     */
    @Test
    public void testHandleGeneralException() {
        // Given
        String message = "Error interno del servidor";
        RuntimeException exception = new RuntimeException(message);

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(message, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    /**
     * Prueba el manejo de BadCredentialsException
     *
     * Escenario: Credenciales inválidas en login
     * Entrada: BadCredentialsException de Spring Security
     * Resultado esperado: ResponseEntity con UNAUTHORIZED
     */
    @Test
    public void testHandleBadCredentialsException() {
        // Given
        BadCredentialsException exception = new BadCredentialsException("Credenciales inválidas");

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleBadCredentialsException(exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Credenciales inválidas: usuario o contraseña incorrectos", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    /**
     * Prueba el manejo de excepciones con mensajes nulos
     *
     * Escenario: Excepciones sin mensaje específico
     * Entrada: BusinessException con message=null
     * Resultado esperado: Manejo correcto sin errores
     */
    @Test
    public void testHandleExceptionWithNullMessage() {
        // Given
        BusinessException exception = new BusinessException(null);

        // When
        ResponseEntity<ApiResponse<String>> response = exceptionHandler.handleBusinessException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getMessage());
    }

    /**
     * Prueba respuestas sin datos (data = null)
     *
     * Escenario: Verificar que todas las respuestas de error tienen data = null
     * Entrada: Diferentes tipos de excepciones
     * Resultado esperado: data siempre es null en respuestas de error
     */
    @Test
    public void testAllErrorResponsesHaveNullData() {
        // Test BusinessException
        BusinessException businessEx = new BusinessException("Error");
        ResponseEntity<ApiResponse<String>> businessResponse = exceptionHandler.handleBusinessException(businessEx);
        assertNull(businessResponse.getBody().getData());

        // Test NotFoundException
        NotFoundException notFoundEx = new NotFoundException("No encontrado");
        ResponseEntity<ApiResponse<String>> notFoundResponse = exceptionHandler.handleNotFoundException(notFoundEx);
        assertNull(notFoundResponse.getBody().getData());

        // Test ValidationException
        ValidationException validationEx = new ValidationException("Inválido");
        ResponseEntity<ApiResponse<String>> validationResponse = exceptionHandler.handleValidationException(validationEx);
        assertNull(validationResponse.getBody().getData());
    }
}