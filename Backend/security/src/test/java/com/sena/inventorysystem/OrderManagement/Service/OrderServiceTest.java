package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;
import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Repository.OrderRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para OrderService
 *
 * Esta clase prueba la lógica de negocio del servicio de pedidos,
 * incluyendo validaciones, creación, actualización y consultas.
 */
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private OrderDto testOrderDto;
    private Client testClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
        testOrderDto.setOrderDate(testOrder.getOrderDate());
        testOrderDto.setTotal(new BigDecimal("150.50"));
        testOrderDto.setStatus("PENDING");
    }

    /**
     * Prueba la creación exitosa de pedido
     *
     * Escenario: Crear pedido con datos válidos
     * Entrada: Order con datos completos
     * Resultado esperado: Pedido creado y OrderDto retornado
     */
    @Test
    public void testCreateOrderSuccess() {
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        OrderDto result = orderService.create(testOrder);

        assertNotNull(result);
        assertEquals(new BigDecimal("150.50"), result.getTotal());
        assertEquals("PENDING", result.getStatus());
        verify(orderRepository).save(any(Order.class));
    }

    /**
     * Prueba la obtención de pedido por ID exitoso
     *
     * Escenario: Buscar pedido existente por ID
     * Entrada: ID válido de pedido existente
     * Resultado esperado: OrderDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        OrderDto result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(new BigDecimal("150.50"), result.getTotal());
        assertEquals("PENDING", result.getStatus());
        verify(orderRepository).findById(1L);
    }

    /**
     * Prueba error al buscar pedido por ID inexistente
     *
     * Escenario: Buscar pedido con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> orderService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la búsqueda de pedidos por cliente
     *
     * Escenario: Buscar pedidos de un cliente específico
     * Entrada: ID de cliente válido
     * Resultado esperado: Lista de OrderDto del cliente
     */
    @Test
    public void testFindByClientId() {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findByClientId(1L)).thenReturn(orders);

        List<OrderDto> result = orderService.findByClientId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getClientId());
    }

    /**
     * Prueba la búsqueda de pedidos por estado
     *
     * Escenario: Buscar pedidos con estado específico
     * Entrada: Estado del pedido
     * Resultado esperado: Lista de OrderDto con ese estado
     */
    @Test
    public void testFindByStatus() {
        List<Order> orders = Arrays.asList(testOrder);
        Order.OrderStatus status = Order.OrderStatus.PENDING;
        when(orderRepository.findByStatus(status)).thenReturn(orders);

        List<OrderDto> result = orderService.findByStatus("PENDING");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }

    /**
     * Prueba la búsqueda de pedidos por rango de fechas
     *
     * Escenario: Buscar pedidos dentro de un rango de fechas
     * Entrada: Fecha de inicio y fin
     * Resultado esperado: Lista de OrderDto en el rango
     */
    @Test
    public void testFindByOrderDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findByOrderDateRange(startDate, endDate)).thenReturn(orders);

        List<OrderDto> result = orderService.findByOrderDateRange(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Prueba la búsqueda de pedidos por rango de total
     *
     * Escenario: Buscar pedidos dentro de un rango de totales
     * Entrada: Total mínimo y máximo
     * Resultado esperado: Lista de OrderDto en el rango
     */
    @Test
    public void testFindByTotalRange() {
        BigDecimal minTotal = new BigDecimal("100.00");
        BigDecimal maxTotal = new BigDecimal("200.00");

        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findByTotalRange(minTotal, maxTotal)).thenReturn(orders);

        List<OrderDto> result = orderService.findByTotalRange(minTotal, maxTotal);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTotal().compareTo(minTotal) >= 0);
        assertTrue(result.get(0).getTotal().compareTo(maxTotal) <= 0);
    }

    /**
     * Prueba la obtención de todos los pedidos
     *
     * Escenario: Listar todos los pedidos del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de OrderDto retornada
     */
    @Test
    public void testFindAll() {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDto> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("150.50"), result.get(0).getTotal());
    }

    /**
     * Prueba la actualización exitosa de pedido
     *
     * Escenario: Actualizar estado de pedido existente
     * Entrada: ID válido y Order con datos actualizados
     * Resultado esperado: OrderDto actualizado retornado
     */
    @Test
    public void testUpdateOrderSuccess() {
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setClient(testClient);
        updatedOrder.setTotal(new BigDecimal("200.00"));
        updatedOrder.setStatus(Order.OrderStatus.CONFIRMED);
        updatedOrder.setOrderDate(testOrder.getOrderDate());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        OrderDto result = orderService.update(1L, updatedOrder);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
    }

    /**
     * Prueba error al actualizar pedido inexistente
     *
     * Escenario: Intentar actualizar pedido con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateOrderNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> orderService.update(999L, testOrder));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la eliminación exitosa de pedido
     *
     * Escenario: Eliminar pedido existente
     * Entrada: ID válido de pedido existente
     * Resultado esperado: Pedido eliminado sin excepciones
     */
    @Test
    public void testDeleteSuccess() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.delete(1L);

        verify(orderRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar pedido inexistente
     *
     * Escenario: Intentar eliminar pedido con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteNotFound() {
        when(orderRepository.existsById(999L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> orderService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
    }
}