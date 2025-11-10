package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Repository.ClientRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para ClientService
 *
 * Esta clase prueba la lógica de negocio del servicio de clientes,
 * incluyendo validaciones, creación, actualización y consultas.
 */
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client testClient;
    private ClientDto testClientDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear cliente de prueba
        testClient = new Client();
        testClient.setId(1L);
        testClient.setName("Juan Pérez");
        testClient.setEmail("juan.perez@example.com");
        testClient.setPhone("+1234567890");
        testClient.setAddress("Calle 123, Ciudad, País");

        // Crear DTO de prueba
        testClientDto = new ClientDto();
        testClientDto.setName("Juan Pérez");
        testClientDto.setEmail("juan.perez@example.com");
        testClientDto.setPhone("+1234567890");
        testClientDto.setAddress("Calle 123, Ciudad, País");
    }

    /**
     * Prueba la creación exitosa de cliente
     *
     * Escenario: Crear cliente con datos válidos
     * Entrada: Client con datos completos
     * Resultado esperado: ClientDto retornado correctamente
     */
    @Test
    public void testCreateClientSuccess() {
        when(clientRepository.existsByEmail(eq("juan.perez@example.com"))).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(testClient);

        ClientDto result = clientService.create(testClient);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getName());
        assertEquals("juan.perez@example.com", result.getEmail());
        verify(clientRepository).save(any(Client.class));
        verify(clientRepository).existsByEmail(eq("juan.perez@example.com"));
    }

    /**
     * Prueba error al crear cliente con email duplicado
     *
     * Escenario: Intentar crear cliente con email que ya existe
     * Entrada: Client con email existente
     * Resultado esperado: BusinessException lanzada
     */
    @Test
    public void testCreateClientDuplicateEmail() {
        when(clientRepository.existsByEmail(eq("juan.perez@example.com"))).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
            () -> clientService.create(testClient));
        assertTrue(exception.getMessage().contains("juan.perez@example.com"));
        verify(clientRepository, never()).save(any(Client.class));
    }

    /**
     * Prueba la obtención de cliente por ID exitoso
     *
     * Escenario: Buscar cliente existente por ID
     * Entrada: ID válido de cliente existente
     * Resultado esperado: ClientDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        ClientDto result = clientService.findById(1L);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getName());
        assertEquals("juan.perez@example.com", result.getEmail());
        verify(clientRepository).findById(1L);
    }

    /**
     * Prueba error al buscar cliente por ID inexistente
     *
     * Escenario: Buscar cliente con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> clientService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la obtención de todos los clientes
     *
     * Escenario: Listar todos los clientes del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de ClientDto retornada
     */
    @Test
    public void testFindAll() {
        List<Client> clients = Arrays.asList(testClient);
        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientDto> result = clientService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
    }

    /**
     * Prueba la búsqueda de cliente por email exitoso
     *
     * Escenario: Buscar cliente por email existente
     * Entrada: Email válido
     * Resultado esperado: ClientDto retornado correctamente
     */
    @Test
    public void testFindByEmailSuccess() {
        when(clientRepository.findByEmail(eq("juan.perez@example.com"))).thenReturn(Optional.of(testClient));

        ClientDto result = clientService.findByEmail("juan.perez@example.com");

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getName());
        assertEquals("juan.perez@example.com", result.getEmail());
        verify(clientRepository).findByEmail(eq("juan.perez@example.com"));
    }

    /**
     * Prueba error al buscar cliente por email inexistente
     *
     * Escenario: Buscar cliente con email que no existe
     * Entrada: Email inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByEmailNotFound() {
        when(clientRepository.findByEmail(eq("nonexistent@example.com"))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> clientService.findByEmail("nonexistent@example.com"));
        assertTrue(exception.getMessage().contains("nonexistent@example.com"));
    }

    /**
     * Prueba la actualización exitosa de cliente
     *
     * Escenario: Actualizar cliente existente
     * Entrada: ID válido y Client con datos actualizados
     * Resultado esperado: ClientDto actualizado retornado
     */
    @Test
    public void testUpdateClientSuccess() {
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("Juan Pérez Actualizado");
        updatedClient.setEmail("juan.perez@example.com");
        updatedClient.setPhone("+0987654321");
        updatedClient.setAddress("Calle 456, Ciudad, País");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        ClientDto result = clientService.update(1L, updatedClient);

        assertNotNull(result);
        verify(clientRepository).save(any(Client.class));
    }

    /**
     * Prueba error al actualizar cliente inexistente
     *
     * Escenario: Intentar actualizar cliente con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateClientNotFound() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> clientService.update(999L, testClient));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba error al actualizar cliente con email duplicado
     *
     * Escenario: Intentar actualizar cliente con email que ya existe
     * Entrada: Client con email existente
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testUpdateClientDuplicateEmail() {
        Client differentClient = new Client();
        differentClient.setId(2L);
        differentClient.setName("Otro Cliente");
        differentClient.setEmail("juan.perez@example.com");
        differentClient.setPhone("+1234567890");
        differentClient.setAddress("Calle 123, Ciudad, País");

        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setName("Cliente Original");
        existingClient.setEmail("original@example.com");
        existingClient.setPhone("+1111111111");
        existingClient.setAddress("Dirección Original");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.existsByEmail(eq("juan.perez@example.com"))).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class,
            () -> clientService.update(1L, differentClient));
        assertTrue(exception.getMessage().contains("juan.perez@example.com"));
        verify(clientRepository, never()).save(any(Client.class));
    }

    /**
     * Prueba la eliminación exitosa de cliente
     *
     * Escenario: Eliminar cliente existente
     * Entrada: ID válido de cliente existente
     * Resultado esperado: Cliente eliminado sin excepciones
     */
    @Test
    public void testDeleteClientSuccess() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar cliente inexistente
     *
     * Escenario: Intentar eliminar cliente con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteClientNotFound() {
        when(clientRepository.existsById(999L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> clientService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
        verify(clientRepository, never()).deleteById(999L);
    }
}