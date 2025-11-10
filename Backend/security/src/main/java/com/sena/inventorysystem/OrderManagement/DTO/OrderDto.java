package com.sena.inventorysystem.OrderManagement.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO para la creación y actualización de órdenes")
public class OrderDto {

    @Schema(description = "ID del cliente que realiza la orden", example = "1", required = true)
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;

    @Schema(description = "Nombre del cliente (solo lectura)", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    private String clientName;

    @Schema(description = "Fecha de la orden (solo lectura)", example = "2023-12-01T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDate;

    @Schema(description = "Total de la orden", example = "150.50", required = true)
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;

    @Schema(description = "Estado de la orden", example = "PENDING", allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"}, defaultValue = "PENDING")
    private String status = "PENDING";

    // Constructors
    public OrderDto() {}

    public OrderDto(Long clientId, String clientName, LocalDateTime orderDate, BigDecimal total, String status) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
    }

    // Getters and Setters
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}