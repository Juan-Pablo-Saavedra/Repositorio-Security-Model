package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.SupplierDto;
import com.sena.inventorysystem.ProductManagement.Entity.Supplier;
import com.sena.inventorysystem.ProductManagement.Repository.SupplierRepository;
import com.sena.inventorysystem.ProductManagement.Factory.SupplierFactory;
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
 * Pruebas unitarias para SupplierService
 *
 * Esta clase prueba la lógica de negocio del servicio de proveedores,
 * incluyendo validaciones, creación, actualización y consultas.
 */
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierFactory supplierFactory;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier testSupplier;
    private SupplierDto testSupplierDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testSupplier = new Supplier();
        testSupplier.setId(1L);
        testSupplier.setName("TechCorp S.A.");
        testSupplier.setContactEmail("contacto@techcorp.com");
        testSupplier.setContactPhone("+1234567890");
        testSupplier.setAddress("Av. Principal 123, Ciudad, País");

        testSupplierDto = new SupplierDto();
        testSupplierDto.setName("TechCorp S.A.");
        testSupplierDto.setContactEmail("contacto@techcorp.com");
        testSupplierDto.setContactPhone("+1234567890");
        testSupplierDto.setAddress("Av. Principal 123, Ciudad, País");
    }

    /**
     * Prueba la creación exitosa de proveedor
     *
     * Escenario: Crear proveedor con datos válidos
     * Entrada: Supplier con datos completos
     * Resultado esperado: Proveedor creado y SupplierDto retornado
     */
    @Test
    public void testCreateSupplierSuccess() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);

        SupplierDto result = supplierService.create(testSupplier);

        assertNotNull(result);
        assertEquals("TechCorp S.A.", result.getName());
        verify(supplierRepository).save(any(Supplier.class));
    }

    /**
     * Prueba la obtención de proveedor por ID exitoso
     *
     * Escenario: Buscar proveedor existente por ID
     * Entrada: ID válido de proveedor existente
     * Resultado esperado: SupplierDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(testSupplier));

        SupplierDto result = supplierService.findById(1L);

        assertNotNull(result);
        assertEquals("TechCorp S.A.", result.getName());
        verify(supplierRepository).findById(1L);
    }

    /**
     * Prueba error al buscar proveedor por ID inexistente
     *
     * Escenario: Buscar proveedor con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> supplierService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la obtención de todos los proveedores
     *
     * Escenario: Listar todos los proveedores del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de SupplierDto retornada
     */
    @Test
    public void testFindAll() {
        List<Supplier> suppliers = Arrays.asList(testSupplier);
        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<SupplierDto> result = supplierService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TechCorp S.A.", result.get(0).getName());
    }

    /**
     * Prueba la actualización exitosa de proveedor
     *
     * Escenario: Actualizar datos de proveedor existente
     * Entrada: ID válido y Supplier con datos actualizados
     * Resultado esperado: SupplierDto actualizado retornado
     */
    @Test
    public void testUpdateSupplierSuccess() {
        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(1L);
        updatedSupplier.setName("TechCorp Actualizado");
        updatedSupplier.setContactEmail("nuevo@techcorp.com");

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(testSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        SupplierDto result = supplierService.update(1L, updatedSupplier);

        assertNotNull(result);
        assertEquals("TechCorp Actualizado", result.getName());
        verify(supplierRepository).save(any(Supplier.class));
    }

    /**
     * Prueba error al actualizar proveedor inexistente
     *
     * Escenario: Intentar actualizar proveedor con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateSupplierNotFound() {
        when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> supplierService.update(999L, testSupplier));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la eliminación exitosa de proveedor
     *
     * Escenario: Eliminar proveedor existente
     * Entrada: ID válido de proveedor existente
     * Resultado esperado: Proveedor eliminado sin excepciones
     */
    @Test
    public void testDeleteSuccess() {
        when(supplierRepository.existsById(1L)).thenReturn(true);

        supplierService.delete(1L);

        verify(supplierRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar proveedor inexistente
     *
     * Escenario: Intentar eliminar proveedor con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteNotFound() {
        when(supplierRepository.existsById(999L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> supplierService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba validación de nombre vacío
     *
     * Escenario: Intentar crear proveedor con nombre vacío
     * Entrada: Supplier con name = ""
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testValidationEmptyName() {
        testSupplier.setName("");

        ValidationException exception = assertThrows(ValidationException.class,
            () -> supplierService.create(testSupplier));
        assertTrue(exception.getMessage().contains("nombre"));
    }
}