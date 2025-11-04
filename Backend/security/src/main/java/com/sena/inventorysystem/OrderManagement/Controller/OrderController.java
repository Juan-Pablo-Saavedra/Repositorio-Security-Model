package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody Order order) {
        OrderDto createdOrder = orderService.create(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        List<OrderDto> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        OrderDto order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody Order order) {
        OrderDto updatedOrder = orderService.update(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDto>> findByClientId(@PathVariable Long clientId) {
        List<OrderDto> orders = orderService.findByClientId(clientId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> findByStatus(@PathVariable Order.OrderStatus status) {
        List<OrderDto> orders = orderService.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDto>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OrderDto> orders = orderService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/total-range")
    public ResponseEntity<List<OrderDto>> findByTotalRange(
            @RequestParam BigDecimal minTotal,
            @RequestParam BigDecimal maxTotal) {
        List<OrderDto> orders = orderService.findByTotalRange(minTotal, maxTotal);
        return ResponseEntity.ok(orders);
    }
}