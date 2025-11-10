package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.Repository.ProductRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto create(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new BusinessException("Producto con SKU " + product.getSku() + " ya existe");
        }
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductDto update(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        if (!existingProduct.getSku().equals(product.getSku()) && productRepository.existsBySku(product.getSku())) {
            throw new ValidationException("Producto con SKU " + product.getSku() + " ya existe");
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSku(product.getSku());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setSku(productDto.getSku());

        return create(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        if (!existingProduct.getSku().equals(productDto.getSku()) && productRepository.existsBySku(productDto.getSku())) {
            throw new ValidationException("Producto con SKU " + productDto.getSku() + " ya existe");
        }

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setSku(productDto.getSku());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado con id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));
        return convertToDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con SKU: " + sku));
        return convertToDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice.doubleValue(), maxPrice.doubleValue()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku()
        );
    }
}