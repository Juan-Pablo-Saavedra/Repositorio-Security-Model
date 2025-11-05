package com.sena.inventorysystem.ProductManagement.Controller;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Service.ISupplierService;
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
    public ResponseEntity<SupplierDto> create(@RequestBody Supplier supplier) {
        try {
            SupplierDto createdSupplier = supplierService.create(supplier);
            return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            SupplierDto updatedSupplier = supplierService.update(id, supplier);
            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            supplierService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> findById(@PathVariable Long id) {
        try {
            SupplierDto supplier = supplierService.findById(id);
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> findAll() {
        try {
            List<SupplierDto> suppliers = supplierService.findAll();
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SupplierDto> findByName(@PathVariable String name) {
        try {
            SupplierDto supplier = supplierService.findByName(name);
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<SupplierDto>> findByContactEmail(@PathVariable String email) {
        try {
            List<SupplierDto> suppliers = supplierService.findByContactEmail(email);
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}