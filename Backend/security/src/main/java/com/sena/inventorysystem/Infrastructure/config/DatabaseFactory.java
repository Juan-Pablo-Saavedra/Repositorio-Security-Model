package com.sena.inventorysystem.Infrastructure.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseFactory {

    @Bean
    @Profile("mysql")
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/inventory_db");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    @Profile("postgresql")
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/inventory_db");
        dataSource.setUsername("inventory_user");
        dataSource.setPassword("inventory_pass");
        return dataSource;
    }

    @Bean
    @Profile("sqlserver")
    public DataSource sqlserverDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost:1434;databaseName=inventory_db;encrypt=false;trustServerCertificate=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("StrongPassword123!");
        return dataSource;
    }
}