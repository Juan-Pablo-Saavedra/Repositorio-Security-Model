package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Repository.OrderRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDto create(Order order) {
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Override
    public OrderDto update(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido no encontrado con id: " + id));

        existingOrder.setClient(order.getClient());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setTotal(order.getTotal());
        existingOrder.setStatus(order.getStatus());

        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDto(updatedOrder);
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new BusinessException("Pedido no encontrado con id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido no encontrado con id: " + id));
        return convertToDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByStatus(String status) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        return orderRepository.findByStatus(orderStatus).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByOrderDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateRange(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findByTotalRange(BigDecimal minTotal, BigDecimal maxTotal) {
        return orderRepository.findByTotalRange(minTotal, maxTotal).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getClient().getId(),
                order.getClient().getName(),
                order.getOrderDate(),
                order.getTotal(),
                order.getStatus().toString()
        );
    }
}