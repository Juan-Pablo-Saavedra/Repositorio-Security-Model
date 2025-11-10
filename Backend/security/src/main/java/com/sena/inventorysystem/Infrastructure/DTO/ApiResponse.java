package com.sena.inventorysystem.Infrastructure.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta estándar de la API")
public class ApiResponse<T> {

    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private boolean success;

    @Schema(description = "Mensaje descriptivo de la respuesta", example = "Operación realizada exitosamente")
    private String message;

    @Schema(description = "Datos de la respuesta (puede ser null)")
    private T data;

    // Constructors
    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}