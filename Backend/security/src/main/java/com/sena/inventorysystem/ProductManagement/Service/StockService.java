package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.Entity.Stock;
import com.sena.inventorysystem.ProductManagement.Repository.StockRepository;
import com.sena.inventorysystem.ProductManagement.DTO.StockDto;
import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.Repository.ProductRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    public StockDto create(Stock stock) {
        Product product = productRepository.findById(stock.getProduct().getId())
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        stock.setProduct(product);
        stock.setCreatedBy("system");
        Stock savedStock = stockRepository.save(stock);
        return convertToDto(savedStock);
    }

    public StockDto update(Long id, Stock stock) {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Stock no encontrado con id: " + id));

        existingStock.setQuantity(stock.getQuantity());
        existingStock.setMinQuantity(stock.getMinQuantity());
        existingStock.setMaxQuantity(stock.getMaxQuantity());
        existingStock.setLocation(stock.getLocation());
        existingStock.setUpdatedBy("system");

        Stock updatedStock = stockRepository.save(existingStock);
        return convertToDto(updatedStock);
    }

    public void delete(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new BusinessException("Stock no encontrado con id: " + id);
        }
        stockRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StockDto findById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Stock no encontrado con id: " + id));
        return convertToDto(stock);
    }

    @Transactional(readOnly = true)
    public List<StockDto> findAll() {
        return stockRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StockDto findByProductId(Long productId) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException("Stock no encontrado para producto: " + productId));
        return convertToDto(stock);
    }

    @Transactional(readOnly = true)
    public List<StockDto> findLowStock(Integer minQuantity) {
        return stockRepository.findByQuantityLessThan(minQuantity).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StockDto convertToDto(Stock stock) {
        return new StockDto(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getProduct().getName(),
                stock.getQuantity(),
                stock.getMinQuantity(),
                stock.getMaxQuantity(),
                stock.getLocation(),
                stock.getCreatedAt(),
                stock.getUpdatedAt(),
                stock.getCreatedBy(),
                stock.getUpdatedBy()
        );
    }
}