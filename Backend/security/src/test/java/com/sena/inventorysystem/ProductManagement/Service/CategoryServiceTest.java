package com.sena.inventorysystem.ProductManagement.Service;

import com.sena.inventorysystem.ProductManagement.DTO.CategoryDto;
import com.sena.inventorysystem.ProductManagement.Entity.Category;
import com.sena.inventorysystem.ProductManagement.Repository.CategoryRepository;
import com.sena.inventorysystem.ProductManagement.Factory.CategoryFactory;
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
 * Pruebas unitarias para CategoryService
 *
 * Esta clase prueba la lógica de negocio del servicio de categorías,
 * incluyendo validaciones, creación, actualización y consultas.
 */
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryFactory categoryFactory;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;
    private CategoryDto testCategoryDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Electrónicos");
        testCategory.setDescription("Productos electrónicos y gadgets");

        testCategoryDto = new CategoryDto();
        testCategoryDto.setName("Electrónicos");
        testCategoryDto.setDescription("Productos electrónicos y gadgets");
    }

    /**
     * Prueba la creación exitosa de categoría
     *
     * Escenario: Crear categoría con datos válidos
     * Entrada: Category con datos completos
     * Resultado esperado: Categoría creada y CategoryDto retornado
     */
    @Test
    public void testCreateCategorySuccess() {
        when(categoryRepository.existsByName("Electrónicos")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryDto result = categoryService.create(testCategory);

        assertNotNull(result);
        assertEquals("Electrónicos", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    /**
     * Prueba error al crear categoría con nombre duplicado
     *
     * Escenario: Intentar crear categoría con nombre que ya existe
     * Entrada: Category con nombre ya existente
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testCreateCategoryDuplicateName() {
        when(categoryRepository.existsByName("Electrónicos")).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class,
            () -> categoryService.create(testCategory));
        assertTrue(exception.getMessage().contains("nombre"));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    /**
     * Prueba la obtención de categoría por ID exitoso
     *
     * Escenario: Buscar categoría existente por ID
     * Entrada: ID válido de categoría existente
     * Resultado esperado: CategoryDto retornado correctamente
     */
    @Test
    public void testFindByIdSuccess() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        CategoryDto result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals("Electrónicos", result.getName());
        verify(categoryRepository).findById(1L);
    }

    /**
     * Prueba error al buscar categoría por ID inexistente
     *
     * Escenario: Buscar categoría con ID que no existe
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByIdNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> categoryService.findById(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la búsqueda de categoría por nombre
     *
     * Escenario: Buscar categoría por nombre único
     * Entrada: Nombre válido
     * Resultado esperado: CategoryDto encontrada
     */
    @Test
    public void testFindByNameSuccess() {
        when(categoryRepository.findByName("Electrónicos")).thenReturn(Optional.of(testCategory));

        CategoryDto result = categoryService.findByName("Electrónicos");

        assertNotNull(result);
        assertEquals("Electrónicos", result.getName());
    }

    /**
     * Prueba error al buscar categoría por nombre inexistente
     *
     * Escenario: Buscar categoría con nombre que no existe
     * Entrada: Nombre inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testFindByNameNotFound() {
        when(categoryRepository.findByName("Nonexistent")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> categoryService.findByName("Nonexistent"));
        assertTrue(exception.getMessage().contains("Nonexistent"));
    }

    /**
     * Prueba la obtención de todas las categorías
     *
     * Escenario: Listar todas las categorías del sistema
     * Entrada: Sin parámetros
     * Resultado esperado: Lista de CategoryDto retornada
     */
    @Test
    public void testFindAll() {
        List<Category> categories = Arrays.asList(testCategory);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electrónicos", result.get(0).getName());
    }

    /**
     * Prueba la actualización exitosa de categoría
     *
     * Escenario: Actualizar datos de categoría existente
     * Entrada: ID válido y Category con datos actualizados
     * Resultado esperado: CategoryDto actualizado retornado
     */
    @Test
    public void testUpdateCategorySuccess() {
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Electrónicos Actualizados");
        updatedCategory.setDescription("Descripción actualizada");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByName("Electrónicos Actualizados")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        CategoryDto result = categoryService.update(1L, updatedCategory);

        assertNotNull(result);
        assertEquals("Electrónicos Actualizados", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    /**
     * Prueba error al actualizar categoría con nombre duplicado
     *
     * Escenario: Intentar actualizar categoría con nombre que ya existe
     * Entrada: Category con nombre ya usado por otra categoría
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testUpdateCategoryDuplicateName() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Existing Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByName("Existing Category")).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class,
            () -> categoryService.update(1L, updatedCategory));
        assertTrue(exception.getMessage().contains("nombre"));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    /**
     * Prueba error al actualizar categoría inexistente
     *
     * Escenario: Intentar actualizar categoría con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testUpdateCategoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> categoryService.update(999L, testCategory));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba la eliminación exitosa de categoría
     *
     * Escenario: Eliminar categoría existente
     * Entrada: ID válido de categoría existente
     * Resultado esperado: Categoría eliminada sin excepciones
     */
    @Test
    public void testDeleteSuccess() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.delete(1L);

        verify(categoryRepository).deleteById(1L);
    }

    /**
     * Prueba error al eliminar categoría inexistente
     *
     * Escenario: Intentar eliminar categoría con ID inexistente
     * Entrada: ID inexistente
     * Resultado esperado: NotFoundException lanzada
     */
    @Test
    public void testDeleteNotFound() {
        when(categoryRepository.existsById(999L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> categoryService.delete(999L));
        assertTrue(exception.getMessage().contains("999"));
    }

    /**
     * Prueba validación de nombre vacío
     *
     * Escenario: Intentar crear categoría con nombre vacío
     * Entrada: Category con name = ""
     * Resultado esperado: ValidationException lanzada
     */
    @Test
    public void testValidationEmptyName() {
        testCategory.setName("");

        ValidationException exception = assertThrows(ValidationException.class,
            () -> categoryService.create(testCategory));
        assertTrue(exception.getMessage().contains("nombre"));
    }
}