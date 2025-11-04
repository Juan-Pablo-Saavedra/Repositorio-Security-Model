package com.sena.inventorysystem.ProductManagement.Service.interfaces;

import com.sena.inventorysystem.ProductManagement.DTO.MovementDto;
import com.sena.inventorysystem.ProductManagement.Entity.Movement;

import java.time.LocalDateTime;
import java.util.List;

public interface IMovementService {

    MovementDto create(Movement movement);

    MovementDto update(Long id, Movement movement);

    void delete(Long id);

    MovementDto findById(Long id);

    List<MovementDto> findAll();

    List<MovementDto> findByProductId(Long productId);

    List<MovementDto> findByType(Movement.MovementType type);

    List<MovementDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}