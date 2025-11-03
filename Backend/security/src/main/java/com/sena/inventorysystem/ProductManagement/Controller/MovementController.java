package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.Entity.Movement;
import com.sena.inventorysystem.ProductManagement.Service.MovementService;
import com.sena.inventorysystem.ProductManagement.DTO.MovementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movements")
@Tag(name = "Gestión de Movimientos", description = "API para gestión de movimientos de inventario")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @PostMapping
    @Operation(summary = "Crear movimiento", description = "Crea un nuevo movimiento de inventario")
    public ResponseEntity<MovementDto> createMovement(@RequestBody Movement movement) {
        MovementDto createdMovement = movementService.create(movement);
        return ResponseEntity.ok(createdMovement);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID", description = "Obtiene un movimiento específico por su ID")
    public ResponseEntity<MovementDto> getMovementById(@PathVariable Long id) {
        MovementDto movement = movementService.findById(id);
        return ResponseEntity.ok(movement);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los movimientos", description = "Obtiene la lista de todos los movimientos")
    public ResponseEntity<List<MovementDto>> getAllMovements() {
        List<MovementDto> movements = movementService.findAll();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Obtener movimientos por producto", description = "Obtiene todos los movimientos de un producto específico")
    public ResponseEntity<List<MovementDto>> getMovementsByProductId(@PathVariable Long productId) {
        List<MovementDto> movements = movementService.findByProductId(productId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Obtener movimientos por rango de fechas", description = "Obtiene movimientos dentro de un rango de fechas")
    public ResponseEntity<List<MovementDto>> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<MovementDto> movements = movementService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Obtener movimientos por tipo", description = "Obtiene movimientos de un tipo específico (IN, OUT, ADJUSTMENT)")
    public ResponseEntity<List<MovementDto>> getMovementsByType(@PathVariable Movement.MovementType type) {
        List<MovementDto> movements = movementService.findByType(type);
        return ResponseEntity.ok(movements);
    }
}