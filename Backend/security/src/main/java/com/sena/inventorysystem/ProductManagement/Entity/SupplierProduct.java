package com.sena.inventorysystem.ProductManagement.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "supplier_product")
public class SupplierProduct implements Serializable {

    @EmbeddedId
    private SupplierProductId id;

    @Column(name = "supply_price", precision = 10, scale = 2)
    private BigDecimal supplyPrice;

    // Constructors
    public SupplierProduct() {}

    public SupplierProduct(Supplier supplier, Product product, BigDecimal supplyPrice) {
        this.id = new SupplierProductId(supplier.getId(), product.getId());
        this.supplyPrice = supplyPrice;
    }

    // Getters and Setters
    public SupplierProductId getId() { return id; }
    public void setId(SupplierProductId id) { this.id = id; }

    public BigDecimal getSupplyPrice() { return supplyPrice; }
    public void setSupplyPrice(BigDecimal supplyPrice) { this.supplyPrice = supplyPrice; }

    @Embeddable
    public static class SupplierProductId implements Serializable {
        private Long supplierId;
        private Long productId;

        public SupplierProductId() {}

        public SupplierProductId(Long supplierId, Long productId) {
            this.supplierId = supplierId;
            this.productId = productId;
        }

        // Getters and Setters
        public Long getSupplierId() { return supplierId; }
        public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SupplierProductId that = (SupplierProductId) o;
            return supplierId.equals(that.supplierId) && productId.equals(that.productId);
        }

        @Override
        public int hashCode() {
            return supplierId.hashCode() + productId.hashCode();
        }
    }
}