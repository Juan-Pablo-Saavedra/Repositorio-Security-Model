package com.sena.inventorysystem.ProductManagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.inventorysystem.ProductManagement.DTO.ProductDto;
import com.sena.inventorysystem.ProductManagement.Service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto input = new ProductDto();
        input.setName("Laptop");
        input.setSku("LAP001");
        input.setDescription("Laptop gaming");
        input.setPrice(new BigDecimal("1500.00"));

        ProductDto output = new ProductDto();
        output.setName("Laptop");
        output.setSku("LAP001");
        output.setDescription("Laptop gaming");
        output.setPrice(new BigDecimal("1500.00"));

        when(productService.createProduct((ProductDto) any(ProductDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto creado exitosamente"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDto product1 = new ProductDto();
        product1.setName("Laptop");
        product1.setSku("LAP001");
        product1.setPrice(new BigDecimal("1500.00"));
        product1.setDescription("Laptop gaming");

        ProductDto product2 = new ProductDto();
        product2.setName("Mouse");
        product2.setSku("MOU001");
        product2.setPrice(new BigDecimal("50.00"));
        product2.setDescription("Mouse wireless");

        List<ProductDto> products = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Laptop"));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Laptop");
        product.setSku("LAP001");
        product.setPrice(new BigDecimal("1500.00"));
        product.setDescription("Laptop gaming");

        when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Laptop"))
                .andExpect(jsonPath("$.data.sku").value("LAP001"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductDto input = new ProductDto();
        input.setName("Laptop Actualizada");
        input.setSku("LAP001");
        input.setDescription("Laptop gaming actualizada");
        input.setPrice(new BigDecimal("1600.00"));

        ProductDto output = new ProductDto();
        output.setName("Laptop Actualizada");
        output.setSku("LAP001");
        output.setDescription("Laptop gaming actualizada");
        output.setPrice(new BigDecimal("1600.00"));

        when(productService.updateProduct(eq(1L), (ProductDto) any(ProductDto.class))).thenReturn(output);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto actualizado exitosamente"))
                .andExpect(jsonPath("$.data.name").value("Laptop Actualizada"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Producto eliminado exitosamente"));
    }

    @Test
    public void testGetProductBySku() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Laptop");
        product.setSku("LAP001");
        product.setPrice(new BigDecimal("1500.00"));
        product.setDescription("Laptop gaming");

        when(productService.findBySku("LAP001")).thenReturn(product);

        mockMvc.perform(get("/api/products/sku/LAP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.sku").value("LAP001"))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    public void testFindProductsByName() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Laptop Gaming");
        product.setSku("LAP001");
        product.setPrice(new BigDecimal("1500.00"));
        product.setDescription("Laptop gaming");

        List<ProductDto> products = Arrays.asList(product);

        when(productService.findByName("Laptop")).thenReturn(products);

        mockMvc.perform(get("/api/products/search")
                .param("name", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Laptop Gaming"));
    }

    @Test
    public void testFindProductsByPriceRange() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Laptop");
        product.setSku("LAP001");
        product.setPrice(new BigDecimal("1500.00"));
        product.setDescription("Laptop gaming");

        List<ProductDto> products = Arrays.asList(product);

        when(productService.findByPriceRange(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
                .thenReturn(products);

        mockMvc.perform(get("/api/products/price-range")
                .param("minPrice", "1000.00")
                .param("maxPrice", "2000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Laptop"));
    }
}