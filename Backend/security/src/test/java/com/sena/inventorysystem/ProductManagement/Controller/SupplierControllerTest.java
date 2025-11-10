package com.sena.inventorysystem.ProductManagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Service.ISupplierService;
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

@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateSupplier() throws Exception {
        SupplierDto input = new SupplierDto();
        input.setName("TechCorp S.A.");
        input.setContactEmail("contacto@techcorp.com");
        input.setContactPhone("+1234567890");
        input.setAddress("Av. Principal 123");

        SupplierDto output = new SupplierDto();
        output.setName("TechCorp S.A.");
        output.setContactEmail("contacto@techcorp.com");
        output.setContactPhone("+1234567890");
        output.setAddress("Av. Principal 123");

        when(supplierService.createSupplier((SupplierDto) any(SupplierDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor creado exitosamente"));
    }

    @Test
    public void testGetAllSuppliers() throws Exception {
        SupplierDto supplier1 = new SupplierDto();
        supplier1.setName("TechCorp S.A.");
        supplier1.setContactEmail("contacto@techcorp.com");
        supplier1.setContactPhone("+1234567890");
        supplier1.setAddress("Av. Principal 123");

        SupplierDto supplier2 = new SupplierDto();
        supplier2.setName("Global Supplies");
        supplier2.setContactEmail("info@globalsupplies.com");
        supplier2.setContactPhone("+9876543210");
        supplier2.setAddress("Calle Secundaria 456");

        List<SupplierDto> suppliers = Arrays.asList(supplier1, supplier2);

        when(supplierService.findAll()).thenReturn(suppliers);

        mockMvc.perform(get("/api/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("TechCorp S.A."));
    }

    @Test
    public void testGetSupplierById() throws Exception {
        SupplierDto supplier = new SupplierDto();
        supplier.setName("TechCorp S.A.");
        supplier.setContactEmail("contacto@techcorp.com");
        supplier.setContactPhone("+1234567890");
        supplier.setAddress("Av. Principal 123");

        when(supplierService.findById(1L)).thenReturn(supplier);

        mockMvc.perform(get("/api/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("TechCorp S.A."));
    }

    @Test
    public void testUpdateSupplier() throws Exception {
        SupplierDto input = new SupplierDto();
        input.setName("TechCorp Actualizada");
        input.setContactEmail("nuevo@techcorp.com");
        input.setContactPhone("+1234567890");
        input.setAddress("Nueva Dirección 789");

        SupplierDto output = new SupplierDto();
        output.setName("TechCorp Actualizada");
        output.setContactEmail("nuevo@techcorp.com");
        output.setContactPhone("+1234567890");
        output.setAddress("Nueva Dirección 789");

        when(supplierService.updateSupplier(eq(1L), (SupplierDto) any(SupplierDto.class))).thenReturn(output);

        mockMvc.perform(put("/api/suppliers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor actualizado exitosamente"));
    }

    @Test
    public void testDeleteSupplier() throws Exception {
        doNothing().when(supplierService).delete(1L);

        mockMvc.perform(delete("/api/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Proveedor eliminado exitosamente"));
    }

    @Test
    public void testGetSupplierByName() throws Exception {
        SupplierDto supplier = new SupplierDto();
        supplier.setName("TechCorp S.A.");
        supplier.setContactEmail("contacto@techcorp.com");
        supplier.setContactPhone("+1234567890");
        supplier.setAddress("Av. Principal 123");

        when(supplierService.findByName("TechCorp S.A.")).thenReturn(supplier);

        mockMvc.perform(get("/api/suppliers/name/TechCorp S.A."))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("TechCorp S.A."));
    }

    @Test
    public void testGetSuppliersByEmail() throws Exception {
        SupplierDto supplier = new SupplierDto();
        supplier.setName("TechCorp S.A.");
        supplier.setContactEmail("contacto@techcorp.com");
        supplier.setContactPhone("+1234567890");
        supplier.setAddress("Av. Principal 123");

        List<SupplierDto> suppliers = Arrays.asList(supplier);

        when(supplierService.findByContactEmail("contacto@techcorp.com")).thenReturn(suppliers);

        mockMvc.perform(get("/api/suppliers/email/contacto@techcorp.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1));
    }
}