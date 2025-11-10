-- Insertar datos de ejemplo para el sistema de inventario - MySQL Version
-- Version: 2.0.0

-- Insertar categorías
INSERT INTO category (name, description) VALUES
('Electrónicos', 'Productos electrónicos y dispositivos'),
('Ropa', 'Ropa y accesorios'),
('Hogar', 'Artículos para el hogar'),
('Deportes', 'Equipos y accesorios deportivos'),
('Libros', 'Libros y material educativo'),
('Juguetes', 'Juguetes y entretenimiento'),
('Automotriz', 'Accesorios para vehículos'),
('Salud', 'Productos de salud y belleza'),
('Oficina', 'Suministros de oficina'),
('Jardín', 'Herramientas y artículos de jardín');

-- Insertar proveedores
INSERT INTO supplier (name, contact_email, contact_phone, address) VALUES
('TechCorp', 'contact@techcorp.com', '+57 300 123 4567', 'Calle 123 #45-67, Bogotá'),
('FashionPlus', 'ventas@fashionplus.com', '+57 301 234 5678', 'Carrera 89 #12-34, Medellín'),
('HomeStyle', 'info@homestyle.com', '+57 302 345 6789', 'Avenida 45 #67-89, Cali'),
('SportMax', 'ventas@sportmax.com', '+57 303 456 7890', 'Calle 78 #90-12, Barranquilla'),
('BookWorld', 'contact@bookworld.com', '+57 304 567 8901', 'Carrera 56 #34-56, Cartagena'),
('ToyLand', 'info@toyland.com', '+57 305 678 9012', 'Avenida 23 #45-67, Bucaramanga'),
('AutoParts', 'ventas@autoparts.com', '+57 306 789 0123', 'Calle 90 #12-34, Pereira'),
('HealthCare', 'contact@healthcare.com', '+57 307 890 1234', 'Carrera 34 #56-78, Manizales'),
('OfficeSupplies', 'info@officesupplies.com', '+57 308 901 2345', 'Avenida 67 #89-01, Pasto'),
('GardenTools', 'ventas@gardentools.com', '+57 309 012 3456', 'Calle 45 #23-45, Cúcuta');

-- Insertar productos
INSERT INTO product (name, description, price, sku) VALUES
('iPhone 15', 'Smartphone Apple iPhone 15 128GB', 4500000.00, 'IPH15-128'),
('Samsung Galaxy S24', 'Smartphone Samsung Galaxy S24 256GB', 3800000.00, 'SGS24-256'),
('MacBook Air M3', 'Laptop Apple MacBook Air con chip M3', 8500000.00, 'MBA-M3-13'),
('Camisa Polo', 'Camisa polo algodón talla M', 75000.00, 'POLO-M-WHT'),
('Jeans Levi\'s', 'Jeans Levi\'s 511 talla 32', 180000.00, 'LV511-32-BLU'),
('Zapatillas Nike', 'Zapatillas deportivas Nike Air Max', 350000.00, 'NK-AM-42-BLK'),
('Sofá 3 plazas', 'Sofá de 3 plazas tela beige', 2500000.00, 'SOFA-3P-BGE'),
('Mesa comedor', 'Mesa de comedor para 6 personas', 1800000.00, 'MESA-COM-6P'),
('Balón fútbol', 'Balón de fútbol profesional', 120000.00, 'BAL-FUT-PRO'),
('Raqueta tennis', 'Raqueta de tenis Wilson Pro Staff', 450000.00, 'RQT-TEN-WPS'),
('El Quijote', 'Novela de Miguel de Cervantes', 45000.00, 'LIB-QUIJOTE'),
('Atlas Mundial', 'Atlas geográfico del mundo', 85000.00, 'ATL-MUNDO-2024'),
('Muñeca Barbie', 'Muñeca Barbie clásica', 65000.00, 'BARBIE-CLASSIC'),
('Lego Creator', 'Set de construcción Lego Creator 3-en-1', 280000.00, 'LEGO-CRE-3IN1'),
('Aceite motor', 'Aceite sintético 5W-30 1L', 35000.00, 'ACE-MOT-5W30'),
('Filtro aire', 'Filtro de aire para Toyota Corolla', 25000.00, 'FLT-AIR-TCOR'),
('Crema hidratante', 'Crema hidratante facial 50ml', 45000.00, 'CRM-HID-FAC50'),
('Vitamina C', 'Suplemento vitamina C 500mg', 25000.00, 'VIT-C-500MG'),
('Cuaderno A4', 'Cuaderno rayado 100 hojas', 8000.00, 'CUAD-A4-100H'),
('Bolígrafo BIC', 'Bolígrafo azul BIC cristal', 2000.00, 'BOL-BIC-AZUL'),
('Tijeras podar', 'Tijeras de podar profesionales', 35000.00, 'TJR-POD-PROF'),
('Manguera jardín', 'Manguera extensible 20m', 45000.00, 'MNG-JRD-20M');

-- Insertar relaciones producto-categoría
INSERT INTO product_category (product_id, category_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 2), (5, 2), (6, 2), (7, 3), (8, 3),
(9, 4), (10, 4), (11, 5), (12, 5), (13, 6), (14, 6), (15, 7),
(16, 7), (17, 8), (18, 8), (19, 9), (20, 9), (21, 10), (22, 10);

-- Insertar relaciones proveedor-producto
INSERT INTO supplier_product (supplier_id, product_id, supply_price) VALUES
(1, 1, 4000000.00), (1, 2, 3500000.00), (1, 3, 8000000.00), (2, 4, 65000.00),
(2, 5, 150000.00), (2, 6, 300000.00), (3, 7, 2200000.00), (3, 8, 1600000.00),
(4, 9, 100000.00), (4, 10, 400000.00), (5, 11, 35000.00), (5, 12, 70000.00),
(6, 13, 55000.00), (6, 14, 250000.00), (7, 15, 30000.00), (7, 16, 20000.00),
(8, 17, 35000.00), (8, 18, 20000.00), (9, 19, 6000.00), (9, 20, 1500.00),
(10, 21, 28000.00), (10, 22, 38000.00);

-- Insertar clientes
INSERT INTO clients (name, email, phone, address) VALUES
('Juan Pérez', 'juan.perez@email.com', '+57 300 123 4567', 'Calle 123 #45-67, Bogotá'),
('María García', 'maria.garcia@email.com', '+57 301 234 5678', 'Carrera 89 #12-34, Medellín'),
('Carlos Rodríguez', 'carlos.rodriguez@email.com', '+57 302 345 6789', 'Avenida 45 #67-89, Cali'),
('Ana Martínez', 'ana.martinez@email.com', '+57 303 456 7890', 'Calle 78 #90-12, Barranquilla'),
('Luis Hernández', 'luis.hernandez@email.com', '+57 304 567 8901', 'Carrera 56 #34-56, Cartagena'),
('Sofia López', 'sofia.lopez@email.com', '+57 305 678 9012', 'Avenida 23 #45-67, Bucaramanga'),
('Diego González', 'diego.gonzalez@email.com', '+57 306 789 0123', 'Calle 90 #12-34, Pereira'),
('Valentina Díaz', 'valentina.diaz@email.com', '+57 307 890 1234', 'Carrera 34 #56-78, Manizales'),
('Andrés Torres', 'andres.torres@email.com', '+57 308 901 2345', 'Avenida 67 #89-01, Pasto'),
('Camila Ramírez', 'camila.ramirez@email.com', '+57 309 012 3456', 'Calle 45 #23-45, Cúcuta');

-- Insertar pedidos
INSERT INTO orders (client_id, order_date, total, status) VALUES
(1, '2024-11-01 10:00:00', 4500000.00, 'PENDING'),
(2, '2024-11-02 14:30:00', 180000.00, 'CONFIRMED'),
(3, '2024-11-03 09:15:00', 2500000.00, 'SHIPPED'),
(4, '2024-11-04 16:45:00', 350000.00, 'DELIVERED'),
(5, '2024-11-05 11:20:00', 45000.00, 'PENDING'),
(6, '2024-11-06 13:10:00', 280000.00, 'CONFIRMED'),
(7, '2024-11-07 15:30:00', 35000.00, 'SHIPPED'),
(8, '2024-11-08 10:45:00', 45000.00, 'DELIVERED'),
(9, '2024-11-09 12:15:00', 8000.00, 'PENDING'),
(10, '2024-11-10 14:00:00', 45000.00, 'CONFIRMED');

-- Insertar usuarios (incluyendo johndoe con password123)
INSERT INTO users (username, email, password, first_name, last_name, phone, address) VALUES
('admin', 'admin@inventory.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'System', '+57 300 000 0000', 'System Address'),

('juanp', 'juan.perez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Juan', 'Pérez', '+57 300 123 4567', 'Calle 123 #45-67, Bogotá'),
('mariag', 'maria.garcia@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'María', 'García', '+57 301 234 5678', 'Carrera 89 #12-34, Medellín'),
('carlosr', 'carlos.rodriguez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Carlos', 'Rodríguez', '+57 302 345 6789', 'Avenida 45 #67-89, Cali'),
('anam', 'ana.martinez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ana', 'Martínez', '+57 303 456 7890', 'Calle 78 #90-12, Barranquilla'),
('luish', 'luis.hernandez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Luis', 'Hernández', '+57 304 567 8901', 'Carrera 56 #34-56, Cartagena'),
('sofial', 'sofia.lopez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Sofia', 'López', '+57 305 678 9012', 'Avenida 23 #45-67, Bucaramanga'),
('diegog', 'diego.gonzalez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Diego', 'González', '+57 306 789 0123', 'Calle 90 #12-34, Pereira'),
('valentinad', 'valentina.diaz@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Valentina', 'Díaz', '+57 307 890 1234', 'Carrera 34 #56-78, Manizales'),
('andrest', 'andres.torres@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Andrés', 'Torres', '+57 308 901 2345', 'Avenida 67 #89-01, Pasto'),
('camilarm', 'camila.ramirez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Camila', 'Ramírez', '+57 309 012 3456', 'Calle 45 #23-45, Cúcuta');