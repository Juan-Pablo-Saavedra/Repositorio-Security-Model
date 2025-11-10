package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Service.IClientService;
import com.sena.inventorysystem.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ClientController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("mysql")
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.mock.mockito.MockBean
    private IClientService clientService;

    private ClientDto testClientDto;
    private List<ClientDto> clientList;

    @BeforeEach
    public void setUp() {
        testClientDto = new ClientDto();
        testClientDto.setName("Juan Pérez");
        testClientDto.setEmail("juan.perez@example.com");
        testClientDto.setPhone("+1234567890");
        testClientDto.setAddress("Calle 123, Ciudad");

        clientList = Arrays.asList(testClientDto);
    }

    @Test
    public void testCreateClientSuccess() throws Exception {
        Client client = new Client();
        client.setName("Juan Pérez");
        client.setEmail("juan.perez@example.com");
        client.setPhone("+1234567890");
        client.setAddress("Calle 123, Ciudad");

        when(clientService.create((Client) any())).thenReturn(testClientDto);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClientDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente creado exitosamente"))
                .andExpect(jsonPath("$.data.name").value("Juan Pérez"));
    }

    @Test
    public void testCreateClientValidationError() throws Exception {
        ClientDto invalidDto = new ClientDto();
        invalidDto.setName(""); // Nombre vacío - debe fallar validación
        invalidDto.setEmail("test@example.com");

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClientSuccess() throws Exception {
        Client client = new Client();
        client.setName("Juan Pérez Actualizado");
        client.setEmail("nuevo@example.com");
        client.setPhone("+1234567890");
        client.setAddress("Nueva Dirección");

        ClientDto updatedDto = new ClientDto();
        updatedDto.setName("Juan Pérez Actualizado");
        updatedDto.setEmail("nuevo@example.com");
        updatedDto.setPhone("+1234567890");
        updatedDto.setAddress("Nueva Dirección");

        when(clientService.update(eq(1L), (Client) any())).thenReturn(updatedDto);

        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente actualizado exitosamente"))
                .andExpect(jsonPath("$.data.name").value("Juan Pérez Actualizado"));
    }

    @Test
    public void testDeleteClientSuccess() throws Exception {
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente eliminado exitosamente"));
    }

    @Test
    public void testFindClientByIdSuccess() throws Exception {
        when(clientService.findById(1L)).thenReturn(testClientDto);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente encontrado"))
                .andExpect(jsonPath("$.data.name").value("Juan Pérez"));
    }

    @Test
    public void testFindClientByIdNotFound() throws Exception {
        when(clientService.findById(999L))
            .thenThrow(new RuntimeException("Cliente no encontrado: 999"));

        mockMvc.perform(get("/api/clients/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado: 999"));
    }

    @Test
    public void testFindAllClients() throws Exception {
        when(clientService.findAll()).thenReturn(clientList);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Clientes obtenidos exitosamente"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Juan Pérez"));
    }

    @Test
    public void testFindClientByEmailSuccess() throws Exception {
        when(clientService.findByEmail("juan.perez@example.com")).thenReturn(testClientDto);

        mockMvc.perform(get("/api/clients/email/juan.perez@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cliente encontrado"))
                .andExpect(jsonPath("$.data.email").value("juan.perez@example.com"));
    }

    @Test
    public void testGeneralErrorHandling() throws Exception {
        when(clientService.findAll())
            .thenThrow(new RuntimeException("Error interno del servidor"));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al obtener clientes: Error interno del servidor"));
    }
}