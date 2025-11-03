package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.Entity.Stock;
import com.sena.inventorysystem.ProductManagement.Service.StockService;
import com.sena.inventorysystem.ProductManagement.DTO.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Gestión de Stock", description = "API para gestión de inventario y stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    @Operation(summary = "Crear stock", description = "Crea un nuevo registro de stock para un producto")
    public ResponseEntity<StockDto> createStock(@RequestBody Stock stock) {
        StockDto createdStock = stockService.create(stock);
        return ResponseEntity.ok(createdStock);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener stock por ID", description = "Obtiene un registro de stock específico por su ID")
    public ResponseEntity<StockDto> getStockById(@PathVariable Long id) {
        StockDto stock = stockService.findById(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    @Operation(summary = "Obtener todo el stock", description = "Obtiene la lista de todos los registros de stock")
    public ResponseEntity<List<StockDto>> getAllStock() {
        List<StockDto> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Obtener stock por producto", description = "Obtiene el stock de un producto específico")
    public ResponseEntity<StockDto> getStockByProductId(@PathVariable Long productId) {
        StockDto stock = stockService.findByProductId(productId);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Obtener stock bajo", description = "Obtiene productos con stock por debajo del mínimo")
    public ResponseEntity<List<StockDto>> getLowStock(@RequestParam(defaultValue = "10") Integer minQuantity) {
        List<StockDto> lowStock = stockService.findLowStock(minQuantity);
        return ResponseEntity.ok(lowStock);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar stock", description = "Actualiza la información de un registro de stock")
    public ResponseEntity<StockDto> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        StockDto updatedStock = stockService.update(id, stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar stock", description = "Elimina un registro de stock del sistema")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}