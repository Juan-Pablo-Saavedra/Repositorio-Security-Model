-- Insertar datos de ejemplo para el sistema de inventario - SQL Server Version
-- Version: 2.0.0

-- Insertar categorias
GO
INSERT INTO category (name, description) VALUES
('Tecnologia Digital', 'Dispositivos digitales y electronicos'),
('Vestuario', 'Ropa, calzado y complementos'),
('Decoracion del Hogar', 'Articulos de decoracion y hogar'),
('Actividades Fisicas', 'Equipos y accesorios para deportes'),
('Material Educativo', 'Libros y recursos de aprendizaje'),
('Juegos y Ocio', 'Juguetes y entretenimiento infantil'),
('Repuestos Automotrices', 'Accesorios y partes para automoviles'),
('Cuidado Personal', 'Productos de belleza y cuidado personal'),
('Equipos de Oficina', 'Suministros y mobiliario de oficina'),
('Herramientas de Jardin', 'Herramientas y equipos de jardineria');

-- Insertar proveedores
INSERT INTO supplier (name, contact_email, contact_phone, address) VALUES
('DigitalTech', 'contact@digitaltech.com', '+57 330 123 4567', 'Calle 250 #55-75, Sincelejo'),
('UrbanStyle', 'ventas@urbanstyle.com', '+57 331 234 5678', 'Carrera 130 #20-40, Valledupar'),
('ModernHome', 'info@modernhome.com', '+57 332 345 6789', 'Avenida 95 #85-05, Monteria'),
('ExtremeSports', 'ventas@extremesports.com', '+57 333 456 7890', 'Calle 160 #35-55, Quibdo'),
('KnowledgeBooks', 'contact@knowledgebooks.com', '+57 334 567 8901', 'Carrera 75 #45-65, Arauca'),
('FunToys', 'info@funtoys.com', '+57 335 678 9012', 'Avenida 40 #55-75, Leticia'),
('VehicleParts', 'ventas@vehicleparts.com', '+57 336 789 0123', 'Calle 140 #20-40, Inirida'),
('BeautyCare', 'contact@beautycare.com', '+57 337 890 1234', 'Carrera 55 #65-85, San Jose del Guaviare'),
('BusinessOffice', 'info@businessoffice.com', '+57 338 901 2345', 'Avenida 105 #15-35, Mitu'),
('EcoGarden', 'ventas@ecogarden.com', '+57 339 012 3456', 'Calle 85 #40-60, Puerto Carreño');

-- Insertar productos
INSERT INTO product (name, description, price, sku) VALUES
('Google Pixel 8', 'Smartphone Google Pixel 8 128GB', 4200000.00, 'GPX8-128'),
('Xiaomi 14', 'Smartphone Xiaomi 14 256GB', 3600000.00, 'XMI14-256'),
('Dell XPS 13', 'Laptop Dell XPS 13 con Intel i7', 7800000.00, 'DXPS13-I7'),
('Camisa Tommy', 'Camisa polo Tommy Hilfiger talla S', 95000.00, 'TOM-S-RED'),
('Jeans Calvin Klein', 'Jeans Calvin Klein slim talla 30', 220000.00, 'CK-30-GRY'),
('Zapatillas Puma', 'Zapatillas deportivas Puma RS-X', 380000.00, 'PUM-RSX-41-BLK'),
(' Sofa reclinable', ' Sofa reclinable individual cuero negro', 1800000.00, 'SOFA-REC-BLK'),
('Mesa redonda', 'Mesa de comedor redonda para 4 personas', 1500000.00, 'MESA-RND-4P'),
('Balon Wilson', 'Balon de futbol Wilson Evolution', 140000.00, 'BAL-WIL-EVO'),
('Raqueta Babolat', 'Raqueta de tenis Babolat Boost Drive', 480000.00, 'RQT-BAB-BD'),
('Don Quijote', 'Novela clasica de Cervantes', 50000.00, 'LIB-DONQUIJOTE'),
('Atlas Historico', 'Atlas historico y geografico', 90000.00, 'ATL-HIST-2024'),
('Muneca Disney', 'Muneca Disney Princesa edición limitada', 70000.00, 'DIS-PRINCESS'),
('Lego Star Wars', 'Set de construccion Lego Star Wars X-Wing', 320000.00, 'LEGO-SW-XWING'),
('Aceite Mobil', 'Aceite sintetico Mobil 1 5W-30 1L', 40000.00, 'ACE-MOB-5W30'),
('Filtro Bosch', 'Filtro de aire Bosch para Volkswagen Golf', 30000.00, 'FLT-BOS-VWGOLF'),
('Crema Loreal', 'Crema hidratante Loreal Paris 75ml', 50000.00, 'CRM-LOR-PAR75'),
('Vitamina B12', 'Suplemento vitamina B12 1000mcg', 30000.00, 'VIT-B12-1000'),
('Cuaderno Scribe', 'Cuaderno profesional 150 hojas', 10000.00, 'CUAD-SCR-150H'),
('Pluma Parker', 'Pluma Parker Jotter azul', 35000.00, 'PLU-PAR-JOT'),
('Tijeras Stanley', 'Tijeras de podar Stanley premium', 40000.00, 'TJR-STN-PREM'),
('Manguera Karcher', 'Manguera extensible Karcher 30m', 60000.00, 'MNG-KAR-30M');

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
('Roberto Silva', 'roberto.silva@email.com', '+57 340 123 4567', 'Calle 280 #50-70, Santa Marta'),
('Carmen Ortiz', 'carmen.ortiz@email.com', '+57 341 234 5678', 'Carrera 140 #30-50, Buenaventura'),
('Fernando Alvarez', 'fernando.alvarez@email.com', '+57 342 345 6789', 'Avenida 115 #35-55, Tumaco'),
('Patricia Reyes', 'patricia.reyes@email.com', '+57 343 456 7890', 'Calle 190 #40-60, Girardot'),
('Ricardo Medina', 'ricardo.medina@email.com', '+57 344 567 8901', 'Carrera 95 #55-75, Fusagasuga'),
('Monica Blanco', 'monica.blanco@email.com', '+57 345 678 9012', 'Avenida 65 #75-95, Zipaquira'),
('Javier Pardo', 'javier.pardo@email.com', '+57 346 789 0123', 'Calle 240 #25-45, Facatativa'),
('Natalia Suarez', 'natalia.suarez@email.com', '+57 347 890 1234', 'Carrera 75 #85-105, Chía'),
('Emilio Vargas', 'emilio.vargas@email.com', '+57 348 901 2345', 'Avenida 135 #30-50, Soacha'),
('Diana Montoya', 'diana.montoya@email.com', '+57 349 012 3456', 'Calle 95 #50-70, Mosquera');

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

('robertos', 'roberto.silva@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Roberto', 'Silva', '+57 340 123 4567', 'Calle 280 #50-70, Santa Marta'),
('carmeno', 'carmen.ortiz@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Carmen', 'Ortiz', '+57 341 234 5678', 'Carrera 140 #30-50, Buenaventura'),
('fernandoa', 'fernando.alvarez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Fernando', 'Alvarez', '+57 342 345 6789', 'Avenida 115 #35-55, Tumaco'),
('patriciar', 'patricia.reyes@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Patricia', 'Reyes', '+57 343 456 7890', 'Calle 190 #40-60, Girardot'),
('ricardom', 'ricardo.medina@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ricardo', 'Medina', '+57 344 567 8901', 'Carrera 95 #55-75, Fusagasuga'),
('monicab', 'monica.blanco@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Monica', 'Blanco', '+57 345 678 9012', 'Avenida 65 #75-95, Zipaquira'),
('javierp', 'javier.pardo@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Javier', 'Pardo', '+57 346 789 0123', 'Calle 240 #25-45, Facatativa'),
('natalias', 'natalia.suarez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Natalia', 'Suarez', '+57 347 890 1234', 'Carrera 75 #85-105, Chía'),
('emiliov', 'emilio.vargas@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Emilio', 'Vargas', '+57 348 901 2345', 'Avenida 135 #30-50, Soacha'),
('dianam', 'diana.montoya@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Diana', 'Montoya', '+57 349 012 3456', 'Calle 95 #50-70, Mosquera');