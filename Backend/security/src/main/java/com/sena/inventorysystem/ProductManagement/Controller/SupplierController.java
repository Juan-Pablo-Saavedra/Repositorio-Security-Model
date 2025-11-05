package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Service.ISupplierService;
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
    public ResponseEntity<SupplierDto> create(@Valid @RequestBody Supplier supplier) {
        SupplierDto createdSupplier = supplierService.create(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        SupplierDto updatedSupplier = supplierService.update(id, supplier);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> findById(@PathVariable Long id) {
        SupplierDto supplier = supplierService.findById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> findAll() {
        List<SupplierDto> suppliers = supplierService.findAll();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SupplierDto> findByName(@PathVariable String name) {
        SupplierDto supplier = supplierService.findByName(name);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<SupplierDto>> findByContactEmail(@PathVariable String email) {
        List<SupplierDto> suppliers = supplierService.findByContactEmail(email);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }
}