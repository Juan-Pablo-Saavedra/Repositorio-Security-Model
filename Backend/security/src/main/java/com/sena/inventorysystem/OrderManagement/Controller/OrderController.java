package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Service.IOrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> create(@Valid @RequestBody Order order) {
        OrderDto createdOrder = orderService.create(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @Valid @RequestBody Order order) {
        OrderDto updatedOrder = orderService.update(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll() {
        List<OrderDto> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDto>> findByClientId(@PathVariable Long clientId) {
        List<OrderDto> orders = orderService.findByClientId(clientId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> findByStatus(@PathVariable String status) {
        List<OrderDto> orders = orderService.findByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDto>> findByOrderDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OrderDto> orders = orderService.findByOrderDateRange(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/total-range")
    public ResponseEntity<List<OrderDto>> findByTotalRange(
            @RequestParam BigDecimal minTotal,
            @RequestParam BigDecimal maxTotal) {
        List<OrderDto> orders = orderService.findByTotalRange(minTotal, maxTotal);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}