package com.sena.inventorysystem.ProductManagement.Repository;

import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByName(String name);

    List<Supplier> findByContactEmail(String email);

    boolean existsByContactEmail(String email);
}