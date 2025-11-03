-- Schema de Usuarios para MySQL
-- Ejecutar este script en la base de datos inventory_db

-- Tabla de usuarios
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

-- Insertar usuario administrador por defecto
-- Contraseña: admin123 (encriptada con BCrypt)
INSERT INTO user (username, email, password, first_name, last_name, phone, address, created_by) VALUES
('admin', 'admin@inventorysystem.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdxp7UeI8C5q0VoO', 'Admin', 'System', '1234567890', 'System Address', 'system');