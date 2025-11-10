package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.Entity.Product;
import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import com.sena.inventorysystem.ProductManagement.Repository.ProductRepository;
import com.sena.inventorysystem.ProductManagement.Factory.ProductFactory;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para ProductService
 *
 * Esta clase prueba la lógica de negocio del servicio de productos,
 * incluyendo validaciones, creación, actualización y consultas.
 */
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductDto testProductDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar datos de prueba
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Producto de Prueba");
        testProduct.setDescription("Descripción de prueba");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setSku("TEST001");

        testProductDto = new ProductDto();
        testProductDto.setName("Producto de Prueba");
        testProductDto.setDescription("Descripción de prueba");
        testProductDto.setPrice(new BigDecimal("99.99"));
        testProductDto.setSku("TEST001");
    }

    /**
     * Prueba la creación exitosa de producto
     *
     * Escenario: Crear producto con datos válidos y SKU único
     * Entrada: ProductDto con datos completos y válidos
     * Resultado esperado: Producto creado y ProductDto retornado
     */
    @Test
    public void testCreateProductSuccess() {
        // Given
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(productFactory.createProductFromDto(any(ProductDto.class))).thenReturn(testProduct);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productFactory.createDtoFromProduct(any(Product.class))).thenReturn(testProductDto);

        // When
        ProductDto result = productService.createProduct(testProductDto);

        // Then
        assertNotNull(result);
        assertEquals("Producto de Prueba", result.getName());
        assertEquals("TEST001", result.getSku());
        verify(productRepository).save(any(Product.class));
    }

    /**
     * Prueba error al crear producto con SKU duplicado
     *
     * Escenario: Intentar crear producto con SKU que ya existe
     * Entrada: ProductDto con SKU ya existente
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testCreateProductDuplicateSku() {
        // Given
        when(productRepository.existsBySku("TEST001")).thenReturn(true);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> productService.createProduct(testProductDto));
        assertTrue(exception.getMessage().contains("SKU"));
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Prueba error al crear producto con precio inválido
     *
     * Escenario: Intentar crear producto con precio cero o negativo
     * Entrada: ProductDto con price = 0
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testCreateProductInvalidPrice() {
        // Given
        testProductDto.setPrice(new BigDecimal("0.00"));
        when(productRepository.existsBySku(anyString())).thenReturn(false);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> productService.createProduct(testProductDto));
        assertTrue(exception.getMessage().contains("precio"));
    }

    /**
     * Prueba la obtención de producto por ID exitoso
     *
     * Escenario: Buscar producto existente por ID
     * Entrada: ID válido de producto existente
     * Resultado esperado: ProductDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productFactory.createDtoFromProduct(testProduct)).thenReturn(testProductDto);

        // When
        ProductDto result = productService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Producto de Prueba", result.getName());
        verify(productRepository).findById(1L);
    }

    /**
     * Prueba error al buscar producto por ID inexistente
     *
     * Escenario: Buscar producto con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> productService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la búsqueda de producto por SKU
     *
     * Escenario: Buscar producto por SKU único
     * Entrada: SKU válido
     * Resultado esperado: ProductDto encontrado
     */
    @Test
    public void testFindBySkuSuccess() {
        // Given
        when(productRepository.findBySku("TEST001")).thenReturn(Optional.of(testProduct));
        when(productFactory.createDtoFromProduct(testProduct)).thenReturn(testProductDto);

        // When
        ProductDto result = productService.findBySku("TEST001");

        // Then
        assertNotNull(result);
        assertEquals("TEST001", result.getSku());
    }

    /**
     * Prueba error al buscar producto por SKU inexistente
     *
     * Escenario: Buscar producto con SKU que no existe
     * Entrada: SKU inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindBySkuNotFound() {
        // Given
        when(productRepository.findBySku("NONEXISTENT")).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> productService.findBySku("NONEXISTENT"));
        assertTrue(exception.getMessage().contains("NONEXISTENT"));
    }

    /**
     * Prueba la búsqueda de productos por nombre
     *
     * Escenario: Buscar productos que contengan un texto en el nombre
     * Entrada: Texto de búsqueda
     * Resultado esperado: Lista de ProductDto que coinciden
     */
    @Test
    public void testFindByName() {
        // Given
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findByNameContainingIgnoreCase("prueba")).thenReturn(products);
        when(productFactory.createDtoFromProduct(testProduct)).thenReturn(testProductDto);

        // When
        List<ProductDto> result = productService.findByName("prueba");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Producto de Prueba", result.get(0).getName());
    }

    /**
     * Prueba la búsqueda de productos por rango de precios
     *
     * Escenario: Buscar productos dentro de un rango de precios
     * Entrada: Precio mínimo y máximo
     * Resultado esperado: Lista de ProductDto en el rango
     */
    @Test
    public void testFindByPriceRange() {
        // Given
        BigDecimal minPrice = new BigDecimal("50.00");
        BigDecimal maxPrice = new BigDecimal("150.00");
        List<Product> products = Arrays.asList(testProduct);

        when(productRepository.findByPriceRange(minPrice.doubleValue(), maxPrice.doubleValue())).thenReturn(products);
        when(productFactory.createDtoFromProduct(testProduct)).thenReturn(testProductDto);

        // When
        List<ProductDto> result = productService.findByPriceRange(minPrice, maxPrice);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("99.99"), result.get(0).getPrice());
    }

    /**
     * Prueba la obtención de todos los productos
     *
     * Escenario: Listar todos los productos del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de ProductDto retornada
     */
    @Test
    public void testFindAll() {
        // Given
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findAll()).thenReturn(products);
        when(productFactory.createDtoFromProduct(testProduct)).thenReturn(testProductDto);

        // When
        List<ProductDto> result = productService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Producto de Prueba", result.get(0).getName());
    }

    /**
     * Prueba la actualización exitosa de producto
     *
     * Escenario: Actualizar datos de producto existente
     * Entrada: ID válido y ProductDto con datos actualizados
     * Resultado esperado: ProductDto actualizado retornado
     */
    @Test
    public void testUpdateProductSuccess() {
        // Given
        ProductDto updatedDto = new ProductDto();
        updatedDto.setName("Producto Actualizado");
        updatedDto.setPrice(new BigDecimal("149.99"));
        updatedDto.setSku("TEST001");

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Producto Actualizado");
        updatedProduct.setPrice(new BigDecimal("149.99"));
        updatedProduct.setSku("TEST001");

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.existsBySku("TEST001")).thenReturn(false);
        when(productFactory.createProductFromDto(any(ProductDto.class))).thenReturn(updatedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productFactory.createDtoFromProduct(any(Product.class))).thenReturn(updatedDto);

        // When
        ProductDto result = productService.updateProduct(1L, updatedDto);

        // Then
        assertNotNull(result);
        assertEquals("Producto Actualizado", result.getName());
        assertEquals(new BigDecimal("149.99"), result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    /**
     * Prueba error al actualizar producto con SKU duplicado
     *
     * Escenario: Intentar actualizar producto con SKU que ya existe en otro producto
     * Entrada: ProductDto con SKU ya usado por otro producto
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testUpdateProductDuplicateSku() {
        // Given
        ProductDto updatedDto = new ProductDto();
        updatedDto.setSku("EXISTING001");

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.existsBySku("EXISTING001")).thenReturn(true);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> productService.updateProduct(1L, updatedDto));
        assertTrue(exception.getMessage().contains("SKU"));
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Prueba error al actualizar producto inexistente
     *
     * Escenario: Intentar actualizar producto con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateProductNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> productService.updateProduct(999L, testProductDto));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la eliminación exitosa de producto
     *
     * Escenario: Eliminar producto existente
     * Entrada: ID válido de producto existente
     * Resultado esperado: Producto eliminado sin excepciones
     */
    @Test
    public void testDeleteSuccess() {
        // Given
        when(productRepository.existsById(1L)).thenReturn(true);

        // When
        productService.delete(1L);

        // Then
        verify(productRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar producto inexistente
     *
     * Escenario: Intentar eliminar producto con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteNotFound() {
        // Given
        when(productRepository.existsById(999L)).thenReturn(false);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> productService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba validación de nombre vacío
     *
     * Escenario: Intentar crear producto con nombre vacío
     * Entrada: ProductDto con name = ""
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testValidationEmptyName() {
        // Given
        testProductDto.setName("");
        when(productRepository.existsBySku(anyString())).thenReturn(false);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> productService.createProduct(testProductDto));
        assertTrue(exception.getMessage().contains("nombre"));
    }

    /**
     * Prueba validación de nombre nulo
     *
     * Escenario: Intentar crear producto con nombre nulo
     * Entrada: ProductDto con name = null
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testValidationNullName() {
        // Given
        testProductDto.setName(null);
        when(productRepository.existsBySku(anyString())).thenReturn(false);

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class,
            () -> productService.createProduct(testProductDto));
        assertTrue(exception.getMessage().contains("nombre"));
    }
}