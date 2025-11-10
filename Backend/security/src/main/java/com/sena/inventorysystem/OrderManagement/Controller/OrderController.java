package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Service.IOrderService;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;

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
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) {
        try {
            // Obtener el cliente por ID
            Client client = new Client();
            client.setId(orderDto.getClientId()); // Asumimos que el clientId viene en el DTO

            Order order = new Order();
            order.setClient(client);
            order.setTotal(orderDto.getTotal());
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(Order.OrderStatus.PENDING);

            OrderDto createdOrder = orderService.create(order);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Pedido creado exitosamente", createdOrder));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al crear el pedido: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        try {
            OrderDto updatedOrder = orderService.update(id, order);
            return ResponseEntity.ok(new ApiResponse(true, "Pedido actualizado exitosamente", updatedOrder));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar el pedido: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Pedido eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar el pedido: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            OrderDto order = orderService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido encontrado", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Pedido no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<OrderDto> orders = orderService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos obtenidos exitosamente", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener pedidos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> findByClientId(@PathVariable Long clientId) {
        try {
            List<OrderDto> orders = orderService.findByClientId(clientId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos del cliente obtenidos", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar pedidos del cliente: " + e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable String status) {
        try {
            List<OrderDto> orders = orderService.findByStatus(status);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos por estado obtenidos", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar pedidos por estado: " + e.getMessage(), null));
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> findByOrderDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<OrderDto> orders = orderService.findByOrderDateRange(startDate, endDate);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos por rango de fechas obtenidos", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar pedidos por rango de fechas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/total-range")
    public ResponseEntity<?> findByTotalRange(
            @RequestParam BigDecimal minTotal,
            @RequestParam BigDecimal maxTotal) {
        try {
            List<OrderDto> orders = orderService.findByTotalRange(minTotal, maxTotal);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos por rango de total obtenidos", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar pedidos por rango de total: " + e.getMessage(), null));
        }
    }
}