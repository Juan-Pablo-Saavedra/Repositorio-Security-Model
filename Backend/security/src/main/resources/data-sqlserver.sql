-- Inicialización de datos para SQL Server
-- Este archivo se ejecuta automáticamente cuando se usa el perfil SQL Server

-- Insertar usuario administrador por defecto (contraseña: admin123 - encriptada con BCrypt)
INSERT INTO [user] (username, email, password, first_name, last_name, phone, address, created_by) VALUES
('admin', 'admin@inventorysystem.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdxp7UeI8C5q0VoO', 'Admin', 'System', '1234567890', 'System Address', 'system');

-- Insertar categorías de ejemplo
INSERT INTO category (name, description, created_by) VALUES
('Electronics', 'Dispositivos electrónicos y accesorios', 'system'),
('Clothing', 'Ropa y artículos de moda', 'system'),
('Books', 'Libros y publicaciones', 'system'),
('Home & Garden', 'Suministros para el hogar y jardín', 'system');

-- Insertar proveedores de ejemplo
INSERT INTO supplier (name, contact_email, contact_phone, address, created_by) VALUES
('TechCorp', 'contact@techcorp.com', '555-0101', '123 Tech Street, Silicon Valley, CA', 'system'),
('FashionHub', 'sales@fashionhub.com', '555-0102', '456 Fashion Ave, New York, NY', 'system'),
('BookWorld', 'orders@bookworld.com', '555-0103', '789 Reading Lane, Boston, MA', 'system');

-- Insertar clientes de ejemplo
INSERT INTO client (name, email, phone, address, created_by) VALUES
('John Doe', 'john.doe@email.com', '555-1001', '123 Main St, Anytown, USA', 'system'),
('Jane Smith', 'jane.smith@email.com', '555-1002', '456 Oak Ave, Somewhere, USA', 'system'),
('Bob Johnson', 'bob.johnson@email.com', '555-1003', '789 Pine Rd, Elsewhere, USA', 'system');

-- Insertar productos de ejemplo
INSERT INTO product (name, description, price, sku, created_by) VALUES
('Laptop Dell XPS 13', 'Ultrabook de alto rendimiento con 16GB RAM y 512GB SSD', 1299.99, 'DELL-XPS13-001', 'system'),
('Wireless Mouse Logitech', 'Mouse inalámbrico ergonómico con receptor USB', 29.99, 'LOGI-MOUSE-001', 'system'),
('T-Shirt Cotton Basic', 'Camiseta cómoda de 100% algodón en varios tamaños', 15.99, 'TSHIRT-BASIC-001', 'system'),
('Java Programming Book', 'Guía completa de programación en Java', 49.99, 'BOOK-JAVA-001', 'system'),
('Garden Hose 50ft', 'Manguera de jardín duradera con conectores de latón', 34.99, 'HOSE-GARDEN-001', 'system');

-- Asociar productos con categorías
INSERT INTO product_category (product_id, category_id) VALUES
(1, 1), -- Laptop -> Electronics
(2, 1), -- Mouse -> Electronics
(3, 2), -- T-Shirt -> Clothing
(4, 3), -- Book -> Books
(5, 4); -- Hose -> Home & Garden

-- Asociar proveedores con productos
INSERT INTO supplier_product (supplier_id, product_id, supply_price) VALUES
(1, 1, 1199.99), -- TechCorp suministra Laptop
(1, 2, 25.99),   -- TechCorp suministra Mouse
(2, 3, 12.99),   -- FashionHub suministra T-Shirt
(3, 4, 39.99),   -- BookWorld suministra Book
(3, 5, 29.99);   -- BookWorld suministra Hose

-- Insertar stock inicial para productos
INSERT INTO stock (product_id, quantity, min_quantity, max_quantity, location, created_by) VALUES
(1, 10, 2, 50, 'Warehouse A - Shelf 1', 'system'),
(2, 25, 5, 100, 'Warehouse A - Shelf 2', 'system'),
(3, 50, 10, 200, 'Warehouse B - Shelf 1', 'system'),
(4, 15, 3, 75, 'Warehouse C - Shelf 1', 'system'),
(5, 8, 2, 30, 'Warehouse D - Shelf 1', 'system');

-- Insertar movimientos de ejemplo
INSERT INTO movement (product_id, type, quantity, reason, created_by) VALUES
(1, 'IN', 10, 'Stock inicial', 'system'),
(2, 'IN', 25, 'Stock inicial', 'system'),
(3, 'IN', 50, 'Stock inicial', 'system'),
(4, 'IN', 15, 'Stock inicial', 'system'),
(5, 'IN', 8, 'Stock inicial', 'system');

-- Insertar órdenes de ejemplo
INSERT INTO [order] (client_id, total, status, created_by) VALUES
(1, 1329.98, 'CONFIRMED', 'system'),
(2, 65.98, 'PENDING', 'system');

-- Insertar detalles de órdenes
INSERT INTO order_detail (order_id, product_id, quantity, unit_price, subtotal, created_by) VALUES
(1, 1, 1, 1299.99, 1299.99, 'system'), -- Orden 1: 1 Laptop
(1, 2, 1, 29.99, 29.99, 'system'),    -- Orden 1: 1 Mouse
(2, 3, 2, 15.99, 31.98, 'system'),    -- Orden 2: 2 T-Shirts
(2, 4, 1, 49.99, 49.99, 'system');    -- Orden 2: 1 Book

-- Asociar órdenes con productos
INSERT INTO order_product (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 1299.99),
(1, 2, 1, 29.99),
(2, 3, 2, 15.99),
(2, 4, 1, 49.99);