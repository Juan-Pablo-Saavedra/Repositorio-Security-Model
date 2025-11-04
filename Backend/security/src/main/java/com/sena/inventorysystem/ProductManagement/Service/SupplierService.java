package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Repository.SupplierRepository;
import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Service.interfaces.ISupplierService;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierDto create(Supplier supplier) {
        if (supplierRepository.existsByContactEmail(supplier.getContactEmail())) {
            throw new BusinessException("Proveedor con email " + supplier.getContactEmail() + " ya existe");
        }
        supplier.setCreatedBy("system");
        Supplier savedSupplier = supplierRepository.save(supplier);
        return convertToDto(savedSupplier);
    }

    public SupplierDto update(Long id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Proveedor no encontrado con id: " + id));

        if (!existingSupplier.getContactEmail().equals(supplier.getContactEmail()) &&
            supplierRepository.existsByContactEmail(supplier.getContactEmail())) {
            throw new BusinessException("Proveedor con email " + supplier.getContactEmail() + " ya existe");
        }

        existingSupplier.setName(supplier.getName());
        existingSupplier.setContactEmail(supplier.getContactEmail());
        existingSupplier.setContactPhone(supplier.getContactPhone());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setUpdatedBy("system");

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return convertToDto(updatedSupplier);
    }

    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new BusinessException("Proveedor no encontrado con id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public SupplierDto findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Proveedor no encontrado con id: " + id));
        return convertToDto(supplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierDto findByName(String name) {
        Supplier supplier = supplierRepository.findByName(name)
                .orElseThrow(() -> new BusinessException("Proveedor no encontrado con nombre: " + name));
        return convertToDto(supplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierDto> findByEmail(String email) {
        return supplierRepository.findByContactEmail(email).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SupplierDto convertToDto(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactEmail(),
                supplier.getContactPhone(),
                supplier.getAddress(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                supplier.getCreatedBy(),
                supplier.getUpdatedBy()
        );
    }
}