package com.sena.inventorysystem.ProductManagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCategory() throws Exception {
        CategoryDto input = new CategoryDto();
        input.setName("Electrónicos");
        input.setDescription("Productos electrónicos y gadgets");

        CategoryDto output = new CategoryDto();
        output.setName("Electrónicos");
        output.setDescription("Productos electrónicos y gadgets");

        when(categoryService.createCategory((CategoryDto) any(CategoryDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría creada exitosamente"));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryDto category1 = new CategoryDto();
        category1.setName("Electrónicos");
        category1.setDescription("Productos electrónicos");

        CategoryDto category2 = new CategoryDto();
        category2.setName("Ropa");
        category2.setDescription("Vestimenta y accesorios");

        List<CategoryDto> categories = Arrays.asList(category1, category2);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Electrónicos"));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        CategoryDto category = new CategoryDto();
        category.setName("Electrónicos");
        category.setDescription("Productos electrónicos y gadgets");

        when(categoryService.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Electrónicos"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryDto input = new CategoryDto();
        input.setName("Electrónicos Actualizados");
        input.setDescription("Descripción actualizada");

        CategoryDto output = new CategoryDto();
        output.setName("Electrónicos Actualizados");
        output.setDescription("Descripción actualizada");

        when(categoryService.updateCategory(eq(1L), (CategoryDto) any(CategoryDto.class))).thenReturn(output);

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría actualizada exitosamente"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categoría eliminada exitosamente"));
    }

    @Test
    public void testGetCategoryByName() throws Exception {
        CategoryDto category = new CategoryDto();
        category.setName("Electrónicos");
        category.setDescription("Productos electrónicos");

        when(categoryService.findByName("Electrónicos")).thenReturn(category);

        mockMvc.perform(get("/api/categories/name/Electrónicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Electrónicos"));
    }
}