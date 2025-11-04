package com.sena.inventorysystem.ProductManagement.Factory;

import com.sena.inventorysystem.ProductManagement.Entity.Supplier;

public class SupplierFactory {

    public static Supplier createSupplier(String name, String contactEmail, String contactPhone, String address) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContactEmail(contactEmail);
        supplier.setContactPhone(contactPhone);
        supplier.setAddress(address);
        return supplier;
    }

    public static Supplier createSupplierWithAudit(String name, String contactEmail, String contactPhone, String address, String createdBy) {
        Supplier supplier = createSupplier(name, contactEmail, contactPhone, address);
        supplier.setCreatedBy(createdBy);
        return supplier;
    }
}