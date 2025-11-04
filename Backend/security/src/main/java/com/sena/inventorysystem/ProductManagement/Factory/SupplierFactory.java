package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;

public class SupplierFactory {

    public static Supplier createSupplier(String name, String contactEmail, String contactPhone, String address) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContactEmail(contactEmail);
        supplier.setContactPhone(contactPhone);
        supplier.setAddress(address);
        return supplier;
    }

    public static Supplier createSupplierFromDto(SupplierDto dto) {
        return createSupplier(dto.getName(), dto.getContactEmail(), dto.getContactPhone(), dto.getAddress());
    }

    public static SupplierDto createDtoFromSupplier(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactEmail(),
                supplier.getContactPhone(),
                supplier.getAddress()
        );
    }
}