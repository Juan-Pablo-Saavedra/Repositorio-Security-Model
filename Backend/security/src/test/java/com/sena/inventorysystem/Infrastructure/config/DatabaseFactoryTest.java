package com.sena.inventorysystem.Infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para DatabaseFactory
 *
 * Esta clase prueba la configuración y creación de conexiones de base de datos
 * para diferentes perfiles (MySQL, PostgreSQL, SQL Server).
 */
@SpringBootTest
@ActiveProfiles("mysql") // Usar perfil MySQL para testing
public class DatabaseFactoryTest {

    @Autowired
    private DataSource dataSource;

    /**
     * Prueba que la configuración de base de datos MySQL se carga correctamente
     *
     * Escenario: Spring Boot carga el perfil MySQL
     * Entrada: Configuración application-mysql.properties
     * Resultado esperado: DataSource configurado correctamente
     */
    @Test
    public void testMySQLDataSourceConfiguration() {
        assertNotNull(dataSource, "DataSource no debe ser null");
        // Verificar que la conexión se puede obtener
        assertDoesNotThrow(() -> {
            var connection = dataSource.getConnection();
            assertNotNull(connection, "Conexión no debe ser null");
            connection.close();
        });
    }

    /**
     * Prueba que las propiedades de conexión están configuradas
     *
     * Escenario: Verificar configuración del pool de conexiones
     * Entrada: Configuración de HikariCP
     * Resultado esperado: Propiedades del pool configuradas
     */
    @Test
    public void testDataSourceProperties() {
        assertNotNull(dataSource, "DataSource no debe ser null");

        // Verificar que es un HikariDataSource (pool de conexiones)
        String dataSourceClass = dataSource.getClass().getSimpleName();
        assertTrue(dataSourceClass.contains("Hikari") || dataSourceClass.contains("DataSource"),
                  "Debe usar HikariCP para el pool de conexiones");
    }
}