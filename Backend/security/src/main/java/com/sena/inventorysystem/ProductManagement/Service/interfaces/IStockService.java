package com.sena.inventorysystem.ProductManagement.Service.interfaces;

import com.sena.inventorysystem.ProductManagement.DTO.StockDto;
import com.sena.inventorysystem.ProductManagement.Entity.Stock;

import java.util.List;

public interface IStockService {

    StockDto create(Stock stock);

    StockDto update(Long id, Stock stock);

    void delete(Long id);

    StockDto findById(Long id);

    List<StockDto> findAll();

    StockDto findByProductId(Long productId);

    List<StockDto> findLowStock(Integer minQuantity);
}