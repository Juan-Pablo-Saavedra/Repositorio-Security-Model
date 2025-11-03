package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Service.SupplierService;
import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Gestión de Proveedores", description = "API para gestión de proveedores")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    @Operation(summary = "Crear proveedor", description = "Crea un nuevo proveedor")
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody Supplier supplier) {
        SupplierDto createdSupplier = supplierService.create(supplier);
        return ResponseEntity.ok(createdSupplier);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Obtiene un proveedor específico por su ID")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        SupplierDto supplier = supplierService.findById(id);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtiene la lista de todos los proveedores")
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> suppliers = supplierService.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Obtener proveedor por nombre", description = "Obtiene un proveedor por su nombre")
    public ResponseEntity<SupplierDto> getSupplierByName(@PathVariable String name) {
        SupplierDto supplier = supplierService.findByName(name);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar proveedores por email", description = "Busca proveedores que contengan el email especificado")
    public ResponseEntity<List<SupplierDto>> searchSuppliersByEmail(@PathVariable String email) {
        List<SupplierDto> suppliers = supplierService.findByEmail(email);
        return ResponseEntity.ok(suppliers);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor", description = "Actualiza la información de un proveedor")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        SupplierDto updatedSupplier = supplierService.update(id, supplier);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}