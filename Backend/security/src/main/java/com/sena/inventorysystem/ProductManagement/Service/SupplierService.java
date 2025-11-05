package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Repository.SupplierRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
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

    @Override
    public SupplierDto create(Supplier supplier) {
        if (supplierRepository.existsByContactEmail(supplier.getContactEmail())) {
            throw new BusinessException("Proveedor con email " + supplier.getContactEmail() + " ya existe");
        }
        Supplier savedSupplier = supplierRepository.save(supplier);
        return convertToDto(savedSupplier);
    }

    @Override
    public SupplierDto update(Long id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con id: " + id));

        if (!existingSupplier.getContactEmail().equals(supplier.getContactEmail()) &&
            supplierRepository.existsByContactEmail(supplier.getContactEmail())) {
            throw new ValidationException("Proveedor con email " + supplier.getContactEmail() + " ya existe");
        }

        existingSupplier.setName(supplier.getName());
        existingSupplier.setContactEmail(supplier.getContactEmail());
        existingSupplier.setContactPhone(supplier.getContactPhone());
        existingSupplier.setAddress(supplier.getAddress());

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return convertToDto(updatedSupplier);
    }

    @Override
    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NotFoundException("Proveedor no encontrado con id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierDto findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con id: " + id));
        return convertToDto(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierDto findByName(String name) {
        Supplier supplier = supplierRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con nombre: " + name));
        return convertToDto(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierDto> findByContactEmail(String email) {
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
                supplier.getAddress()
        );
    }
}