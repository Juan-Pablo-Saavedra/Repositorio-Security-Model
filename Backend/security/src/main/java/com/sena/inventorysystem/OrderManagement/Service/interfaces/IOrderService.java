package com.sena.inventorysystem.OrderManagement.Service.interfaces;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    OrderDto create(Order order);

    OrderDto update(Long id, Order order);

    void delete(Long id);

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    List<OrderDto> findByClientId(Long clientId);

    List<OrderDto> findByStatus(String status);

    List<OrderDto> findByOrderDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<OrderDto> findByTotalRange(BigDecimal minTotal, BigDecimal maxTotal);
}