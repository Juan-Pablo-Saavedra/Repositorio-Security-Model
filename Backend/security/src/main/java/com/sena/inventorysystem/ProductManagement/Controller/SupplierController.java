package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Service.ISupplierService;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        try {
            SupplierDto createdSupplier = supplierService.createSupplier(supplierDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Proveedor creado exitosamente", createdSupplier));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al crear el proveedor: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierDto supplierDto) {
        try {
            SupplierDto updatedSupplier = supplierService.updateSupplier(id, supplierDto);
            if(updatedSupplier == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Proveedor no encontrado con ID: " + id, null));
            }
            return ResponseEntity.ok(new ApiResponse(true, "Proveedor actualizado exitosamente", updatedSupplier));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar el proveedor: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        try {
            supplierService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Proveedor eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar el proveedor: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            SupplierDto supplier = supplierService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Proveedor encontrado", supplier));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Proveedor no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<SupplierDto> suppliers = supplierService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Proveedores obtenidos exitosamente", suppliers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener proveedores: " + e.getMessage(), null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            SupplierDto supplier = supplierService.findByName(name);
            return ResponseEntity.ok(new ApiResponse<>(true, "Proveedor encontrado", supplier));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Proveedor no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByContactEmail(@PathVariable String email) {
        try {
            List<SupplierDto> suppliers = supplierService.findByContactEmail(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Proveedores encontrados", suppliers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar proveedores: " + e.getMessage(), null));
        }
    }
}