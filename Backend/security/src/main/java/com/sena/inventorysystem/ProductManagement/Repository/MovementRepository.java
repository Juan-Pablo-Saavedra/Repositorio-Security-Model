package com.sena.inventorysystem.ProductManagement.Repository;

import com.sena.inventorysystem.ProductManagement.Entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query("SELECT m FROM Movement m WHERE m.movementDate BETWEEN :startDate AND :endDate")
    List<Movement> findByMovementDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Movement> findByProductIdAndType(Long productId, Movement.MovementType type);

    List<Movement> findByType(Movement.MovementType type);
}