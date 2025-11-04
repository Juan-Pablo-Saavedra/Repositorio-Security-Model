package com.sena.inventorysystem.ProductManagement.Service.interfaces;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Entity.Supplier;

import java.util.List;

public interface ISupplierService {

    SupplierDto create(Supplier supplier);

    SupplierDto update(Long id, Supplier supplier);

    void delete(Long id);

    SupplierDto findById(Long id);

    List<SupplierDto> findAll();

    SupplierDto findByName(String name);

    List<SupplierDto> findByContactEmail(String email);
}