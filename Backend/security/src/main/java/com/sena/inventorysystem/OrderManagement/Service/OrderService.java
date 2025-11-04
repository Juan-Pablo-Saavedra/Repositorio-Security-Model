package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Repository.OrderRepository;
import com.sena.inventorysystem.OrderManagement.Repository.ClientRepository;
import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Service.interfaces.IOrderService;
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

    @Autowired
    private ClientRepository clientRepository;

    public OrderDto create(Order order) {
        // Validate client exists
        if (order.getClient() == null || order.getClient().getId() == null) {
            throw new BusinessException("Cliente es requerido para crear una orden");
        }

        Client client = clientRepository.findById(order.getClient().getId())
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));

        order.setClient(client);
        order.setCreatedBy("system");

        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Orden no encontrada"));
        return convertToDto(order);
    }

    public OrderDto update(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Orden no encontrada"));

        if (order.getClient() != null && order.getClient().getId() != null) {
            Client client = clientRepository.findById(order.getClient().getId())
                    .orElseThrow(() -> new BusinessException("Cliente no encontrado"));
            existingOrder.setClient(client);
        }

        existingOrder.setTotal(order.getTotal());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setUpdatedBy("system");

        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDto(updatedOrder);
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new BusinessException("Orden no encontrada");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto findById(Long id) {
        return getById(id);
    }

    @Override
    public List<OrderDto> findAll() {
        return getAll();
    }

    @Override
    public List<OrderDto> findByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateRange(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByTotalRange(BigDecimal minTotal, BigDecimal maxTotal) {
        return orderRepository.findByTotalRange(minTotal.doubleValue(), maxTotal.doubleValue()).stream()
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
                order.getStatus().toString(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCreatedBy(),
                order.getUpdatedBy()
        );
    }
}