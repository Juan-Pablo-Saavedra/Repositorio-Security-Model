package com.sena.inventorysystem.Infrastructure.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase ApiResponse
 *
 * Esta clase prueba la funcionalidad del DTO ApiResponse que se utiliza
 * para estandarizar las respuestas HTTP en toda la aplicación.
 */
public class ApiResponseTest {

    /**
     * Prueba la creación de una respuesta exitosa con datos
     *
     * Escenario: Crear una respuesta exitosa con mensaje y datos
     * Entrada: success=true, message="Operación exitosa", data="datos de prueba"
     * Resultado esperado: Objeto ApiResponse con los valores correctos
     */
    @Test
    public void testApiResponseSuccessWithData() {
        // Given
        String message = "Operación exitosa";
        String data = "datos de prueba";

        // When
        ApiResponse<String> response = new ApiResponse<>(true, message, data);

        // Then
        assertTrue(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    /**
     * Prueba la creación de una respuesta exitosa sin datos
     *
     * Escenario: Crear una respuesta exitosa sin datos (para operaciones como DELETE)
     * Entrada: success=true, message="Eliminación exitosa", data=null
     * Resultado esperado: Objeto ApiResponse con data=null
     */
    @Test
    public void testApiResponseSuccessWithoutData() {
        // Given
        String message = "Eliminación exitosa";

        // When
        ApiResponse<Void> response = new ApiResponse<>(true, message, null);

        // Then
        assertTrue(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertNull(response.getData());
    }

    /**
     * Prueba la creación de una respuesta de error
     *
     * Escenario: Crear una respuesta de error con mensaje de error
     * Entrada: success=false, message="Error interno", data=null
     * Resultado esperado: Objeto ApiResponse con success=false
     */
    @Test
    public void testApiResponseError() {
        // Given
        String message = "Error interno del servidor";

        // When
        ApiResponse<String> response = new ApiResponse<>(false, message, null);

        // Then
        assertFalse(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertNull(response.getData());
    }

    /**
     * Prueba los constructores y getters/setters
     *
     * Escenario: Verificar que los constructores y métodos de acceso funcionan correctamente
     * Entrada: Constructor vacío y setters
     * Resultado esperado: Valores correctos después de usar setters
     */
    @Test
    public void testApiResponseConstructorsAndGettersSetters() {
        // Given
        ApiResponse<String> response = new ApiResponse<>();

        // When
        response.setSuccess(true);
        response.setMessage("Mensaje de prueba");
        response.setData("Datos de prueba");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Mensaje de prueba", response.getMessage());
        assertEquals("Datos de prueba", response.getData());
    }

    /**
     * Prueba la respuesta con diferentes tipos de datos genéricos
     *
     * Escenario: Verificar que ApiResponse funciona con diferentes tipos de datos
     * Entrada: Integer, Double, Boolean
     * Resultado esperado: Funciona correctamente con tipos primitivos envueltos
     */
    @Test
    public void testApiResponseWithDifferentDataTypes() {
        // Test with Integer
        ApiResponse<Integer> intResponse = new ApiResponse<>(true, "Número", 42);
        assertEquals(42, intResponse.getData());

        // Test with Double
        ApiResponse<Double> doubleResponse = new ApiResponse<>(true, "Decimal", 3.14);
        assertEquals(3.14, doubleResponse.getData());

        // Test with Boolean
        ApiResponse<Boolean> boolResponse = new ApiResponse<>(true, "Booleano", true);
        assertTrue(boolResponse.getData());
    }
}