package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Service.IOrderService;
import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(OrderController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("mysql")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.boot.test.mock.mockito.MockBean
    private IOrderService orderService;

    private OrderDto testOrderDto;
    private Order testOrder;
    private Client testClient;
    private List<OrderDto> orderList;

    @BeforeEach
    public void setUp() {
        testClient = new Client();
        testClient.setId(1L);
        testClient.setName("Juan Pérez");

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setClient(testClient);
        testOrder.setTotal(new BigDecimal("150.50"));
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setStatus(Order.OrderStatus.PENDING);

        testOrderDto = new OrderDto();
        testOrderDto.setClientId(1L);
        testOrderDto.setClientName("Juan Pérez");
        testOrderDto.setTotal(new BigDecimal("150.50"));
        testOrderDto.setStatus("PENDING");
        testOrderDto.setOrderDate(testOrder.getOrderDate());

        orderList = Arrays.asList(testOrderDto);
    }

    @Test
    public void testCreateOrderSuccess() throws Exception {
        when(orderService.create((Order) any())).thenReturn(testOrderDto);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedido creado exitosamente"))
                .andExpect(jsonPath("$.data.clientId").value(1L));
    }

    @Test
    public void testCreateOrderValidationError() throws Exception {
        OrderDto invalidDto = new OrderDto();
        invalidDto.setTotal(new BigDecimal("150.50"));

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateOrderSuccess() throws Exception {
        OrderDto updatedDto = new OrderDto();
        updatedDto.setClientId(1L);
        updatedDto.setClientName("Juan Pérez Actualizado");
        updatedDto.setTotal(new BigDecimal("200.00"));
        updatedDto.setStatus("CONFIRMED");
        updatedDto.setOrderDate(LocalDateTime.now());

        when(orderService.update(eq(1L), (Order) any())).thenReturn(updatedDto);

        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedido actualizado exitosamente"))
                .andExpect(jsonPath("$.data.status").value("CONFIRMED"));
    }

    @Test
    public void testDeleteOrderSuccess() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedido eliminado exitosamente"));
    }

    @Test
    public void testFindOrderByIdSuccess() throws Exception {
        when(orderService.findById(1L)).thenReturn(testOrderDto);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedido encontrado"))
                .andExpect(jsonPath("$.data.clientId").value(1L));
    }

    @Test
    public void testFindOrderByIdNotFound() throws Exception {
        when(orderService.findById(999L))
            .thenThrow(new RuntimeException("Pedido no encontrado: 999"));

        mockMvc.perform(get("/api/orders/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Pedido no encontrado: 999"));
    }

    @Test
    public void testFindAllOrders() throws Exception {
        when(orderService.findAll()).thenReturn(orderList);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos obtenidos exitosamente"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].clientId").value(1L));
    }

    @Test
    public void testFindOrdersByClientId() throws Exception {
        when(orderService.findByClientId(1L)).thenReturn(orderList);

        mockMvc.perform(get("/api/orders/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos del cliente obtenidos"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testFindOrdersByStatus() throws Exception {
        when(orderService.findByStatus("PENDING")).thenReturn(orderList);

        mockMvc.perform(get("/api/orders/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos por estado obtenidos"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testFindOrdersByDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        when(orderService.findByOrderDateRange(eq(startDate), eq(endDate))).thenReturn(orderList);

        mockMvc.perform(get("/api/orders/date-range")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos por rango de fechas obtenidos"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testFindOrdersByTotalRange() throws Exception {
        BigDecimal minTotal = new BigDecimal("100.00");
        BigDecimal maxTotal = new BigDecimal("200.00");

        when(orderService.findByTotalRange(eq(minTotal), eq(maxTotal))).thenReturn(orderList);

        mockMvc.perform(get("/api/orders/total-range")
                .param("minTotal", minTotal.toString())
                .param("maxTotal", maxTotal.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos por rango de total obtenidos"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void testGeneralErrorHandling() throws Exception {
        when(orderService.findAll())
            .thenThrow(new RuntimeException("Error interno del servidor"));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al obtener pedidos: Error interno del servidor"));
    }
}