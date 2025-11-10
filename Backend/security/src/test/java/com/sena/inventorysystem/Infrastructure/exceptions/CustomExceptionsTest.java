package com.sena.inventorysystem.Infrastructure.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las excepciones personalizadas
 *
 * Esta clase prueba todas las excepciones personalizadas del sistema
 * para asegurar que se comportan correctamente.
 */
public class CustomExceptionsTest {

    /**
     * Prueba la excepción BusinessException
     *
     * Escenario: Crear una BusinessException con mensaje personalizado
     * Entrada: message="Regla de negocio violada"
     * Resultado esperado: Excepción con mensaje correcto
     */
    @Test
    public void testBusinessException() {
        // Given
        String message = "Regla de negocio violada";

        // When
        BusinessException exception = new BusinessException(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    /**
     * Prueba la excepción NotFoundException
     *
     * Escenario: Crear una NotFoundException para recurso no encontrado
     * Entrada: message="Producto no encontrado con ID: 123"
     * Resultado esperado: Excepción con mensaje correcto
     */
    @Test
    public void testNotFoundException() {
        // Given
        String message = "Producto no encontrado con ID: 123";

        // When
        NotFoundException exception = new NotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    /**
     * Prueba la excepción ValidationException
     *
     * Escenario: Crear una ValidationException para errores de validación
     * Entrada: message="El precio debe ser mayor a cero"
     * Resultado esperado: Excepción con mensaje correcto
     */
    @Test
    public void testValidationException() {
        // Given
        String message = "El precio debe ser mayor a cero";

        // When
        ValidationException exception = new ValidationException(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    /**
     * Prueba constructores sin parámetros
     *
     * Escenario: Crear excepciones sin mensaje inicial
     * Entrada: Constructor vacío
     * Resultado esperado: Excepciones creadas sin errores
     */
    @Test
    public void testExceptionsWithoutMessage() {
        // When
        BusinessException businessEx = new BusinessException(null);
        NotFoundException notFoundEx = new NotFoundException(null);
        ValidationException validationEx = new ValidationException(null);

        // Then
        assertNull(businessEx.getMessage());
        assertNull(notFoundEx.getMessage());
        assertNull(validationEx.getMessage());
    }

    /**
     * Prueba mensajes vacíos
     *
     * Escenario: Crear excepciones con mensajes vacíos
     * Entrada: message=""
     * Resultado esperado: Mensajes vacíos manejados correctamente
     */
    @Test
    public void testExceptionsWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        BusinessException businessEx = new BusinessException(emptyMessage);
        NotFoundException notFoundEx = new NotFoundException(emptyMessage);
        ValidationException validationEx = new ValidationException(emptyMessage);

        // Then
        assertEquals(emptyMessage, businessEx.getMessage());
        assertEquals(emptyMessage, notFoundEx.getMessage());
        assertEquals(emptyMessage, validationEx.getMessage());
    }

    /**
     * Prueba mensajes largos
     *
     * Escenario: Crear excepciones con mensajes muy largos
     * Entrada: message="Mensaje muy largo..." (1000 caracteres)
     * Resultado esperado: Mensajes largos manejados correctamente
     */
    @Test
    public void testExceptionsWithLongMessage() {
        // Given
        String longMessage = "A".repeat(1000) + "Mensaje muy largo";

        // When
        BusinessException exception = new BusinessException(longMessage);

        // Then
        assertEquals(longMessage, exception.getMessage());
        assertTrue(exception.getMessage().length() > 1000);
    }

    /**
     * Prueba jerarquía de excepciones
     *
     * Escenario: Verificar que las excepciones heredan correctamente de RuntimeException
     * Entrada: Instancias de cada excepción personalizada
     * Resultado esperado: Todas son instancias de RuntimeException
     */
    @Test
    public void testExceptionHierarchy() {
        // When
        BusinessException businessEx = new BusinessException("test");
        NotFoundException notFoundEx = new NotFoundException("test");
        ValidationException validationEx = new ValidationException("test");

        // Then
        assertInstanceOf(RuntimeException.class, businessEx);
        assertInstanceOf(RuntimeException.class, notFoundEx);
        assertInstanceOf(RuntimeException.class, validationEx);

        // Also check that they are instances of Exception
        assertInstanceOf(Exception.class, businessEx);
        assertInstanceOf(Exception.class, notFoundEx);
        assertInstanceOf(Exception.class, validationEx);
    }
}