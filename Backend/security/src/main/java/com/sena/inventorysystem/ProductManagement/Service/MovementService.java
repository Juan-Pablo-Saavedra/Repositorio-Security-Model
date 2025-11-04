package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.Entity.Movement;
import com.sena.inventorysystem.ProductManagement.Entity.Stock;
import com.sena.inventorysystem.ProductManagement.Repository.MovementRepository;
import com.sena.inventorysystem.ProductManagement.Repository.StockRepository;
import com.sena.inventorysystem.ProductManagement.DTO.MovementDto;
import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.Repository.ProductRepository;
import com.sena.inventorysystem.ProductManagement.Service.interfaces.IMovementService;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovementService implements IMovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    public MovementDto create(Movement movement) {
        Product product = productRepository.findById(movement.getProduct().getId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        movement.setProduct(product);
        movement.setCreatedBy("system");

        // Update stock based on movement type
        updateStock(movement);

        Movement savedMovement = movementRepository.save(movement);
        return convertToDto(savedMovement);
    }

    private void updateStock(Movement movement) {
        Stock stock = stockRepository.findByProductId(movement.getProduct().getId())
                .orElseThrow(() -> new BusinessException("Stock no encontrado para el producto"));

        Integer newQuantity;
        switch (movement.getType()) {
            case IN:
                newQuantity = stock.getQuantity() + movement.getQuantity();
                break;
            case OUT:
                newQuantity = stock.getQuantity() - movement.getQuantity();
                if (newQuantity < 0) {
                    throw new BusinessException("No hay suficiente stock disponible");
                }
                break;
            case ADJUSTMENT:
                newQuantity = movement.getQuantity(); // Direct adjustment
                break;
            default:
                throw new BusinessException("Tipo de movimiento no válido");
        }

        stock.setQuantity(newQuantity);
        stock.setUpdatedBy("system");
        stockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    public MovementDto findById(Long id) {
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Movimiento no encontrado con id: " + id));
        return convertToDto(movement);
    }

    @Transactional(readOnly = true)
    public List<MovementDto> findAll() {
        return movementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementDto> findByProductId(Long productId) {
        return movementRepository.findByProductIdAndType(productId, null).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return movementRepository.findByMovementDateRange(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementDto> findByType(Movement.MovementType type) {
        return movementRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MovementDto update(Long id, Movement movement) {
        Movement existingMovement = movementRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Movimiento no encontrado"));

        existingMovement.setType(movement.getType());
        existingMovement.setQuantity(movement.getQuantity());
        existingMovement.setReason(movement.getReason());
        existingMovement.setUpdatedBy("system");

        Movement updatedMovement = movementRepository.save(existingMovement);
        return convertToDto(updatedMovement);
    }

    public void delete(Long id) {
        if (!movementRepository.existsById(id)) {
            throw new BusinessException("Movimiento no encontrado");
        }
        movementRepository.deleteById(id);
    }

    private MovementDto convertToDto(Movement movement) {
        return new MovementDto(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getProduct().getName(),
                movement.getType().toString(),
                movement.getQuantity(),
                movement.getReason(),
                movement.getMovementDate(),
                movement.getCreatedAt(),
                movement.getUpdatedAt(),
                movement.getCreatedBy(),
                movement.getUpdatedBy()
        );
    }
}