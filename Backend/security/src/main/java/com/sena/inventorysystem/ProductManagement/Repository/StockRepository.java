package com.sena.inventorysystem.ProductManagement.Repository;

import com.sena.inventorysystem.ProductManagement.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long productId);

    List<Stock> findByQuantityLessThan(Integer minQuantity);

    @Query("SELECT s FROM Stock s WHERE s.quantity BETWEEN :min AND :max")
    List<Stock> findByQuantityRange(@Param("min") Integer min, @Param("max") Integer max);

    List<Stock> findByLocation(String location);
}