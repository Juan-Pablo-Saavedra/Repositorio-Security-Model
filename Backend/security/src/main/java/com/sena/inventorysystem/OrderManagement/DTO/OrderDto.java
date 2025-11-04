package com.sena.inventorysystem.OrderManagement.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    private Long id;
    private Long clientId;
    private String clientName;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;

    // Constructors
    public OrderDto() {}

    public OrderDto(Long id, Long clientId, String clientName, LocalDateTime orderDate, BigDecimal total, String status) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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