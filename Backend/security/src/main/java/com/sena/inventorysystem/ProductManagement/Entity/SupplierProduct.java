package com.sena.inventorysystem.ProductManagement.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "supplier_product")
public class SupplierProduct {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "supply_price", precision = 10, scale = 2)
    private BigDecimal supplyPrice;

    // Constructors
    public SupplierProduct() {}

    public SupplierProduct(Supplier supplier, Product product, BigDecimal supplyPrice) {
        this.supplier = supplier;
        this.product = product;
        this.supplyPrice = supplyPrice;
    }

    // Getters and Setters
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public BigDecimal getSupplyPrice() { return supplyPrice; }
    public void setSupplyPrice(BigDecimal supplyPrice) { this.supplyPrice = supplyPrice; }
}