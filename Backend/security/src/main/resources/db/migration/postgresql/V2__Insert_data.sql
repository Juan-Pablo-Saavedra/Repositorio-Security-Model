-- Insertar datos de ejemplo para el sistema de inventario - PostgreSQL Version
-- Version: 2.0.0

-- Insertar categorías
INSERT INTO category (name, description) VALUES
('Tecnología', 'Dispositivos tecnológicos y electrónicos'),
('Moda', 'Ropa, calzado y accesorios de moda'),
('Hogar y Decoración', 'Artículos para el hogar y decoración'),
('Deportes y Fitness', 'Equipos deportivos y artículos de fitness'),
('Educación', 'Libros, cursos y material educativo'),
('Entretenimiento', 'Juegos, juguetes y entretenimiento'),
('Automoción', 'Accesorios y repuestos para vehículos'),
('Bienestar', 'Productos de salud, belleza y bienestar'),
('Oficina', 'Suministros y equipos de oficina'),
('Jardinería', 'Herramientas y artículos de jardinería');

-- Insertar proveedores
INSERT INTO supplier (name, contact_email, contact_phone, address) VALUES
('Innovatech', 'contact@innovatech.com', '+57 310 123 4567', 'Calle 200 #50-70, Medellín'),
('StyleFashion', 'ventas@stylefashion.com', '+57 311 234 5678', 'Carrera 100 #15-35, Cali'),
('HomeDecor', 'info@homedecor.com', '+57 312 345 6789', 'Avenida 80 #90-10, Barranquilla'),
('ActiveSports', 'ventas@activesports.com', '+57 313 456 7890', 'Calle 150 #25-45, Cartagena'),
('ReadBooks', 'contact@readbooks.com', '+57 314 567 8901', 'Carrera 70 #40-60, Bucaramanga'),
('PlayToys', 'info@playtoys.com', '+57 315 678 9012', 'Avenida 30 #50-70, Pereira'),
('CarParts', 'ventas@carparts.com', '+57 316 789 0123', 'Calle 120 #15-35, Manizales'),
('WellnessCare', 'contact@wellnesscare.com', '+57 317 890 1234', 'Carrera 50 #60-80, Pasto'),
('OfficeWorld', 'info@officeworld.com', '+57 318 901 2345', 'Avenida 90 #10-30, Cúcuta'),
('GreenGarden', 'ventas@greengarden.com', '+57 319 012 3456', 'Calle 60 #35-55, Ibagué');

-- Insertar productos
INSERT INTO product (name, description, price, sku) VALUES
('iPhone 15 Pro', 'Smartphone Apple iPhone 15 Pro 256GB', 5500000.00, 'IPH15P-256'),
('Samsung Galaxy S25', 'Smartphone Samsung Galaxy S25 512GB', 4800000.00, 'SGS25-512'),
('MacBook Pro M4', 'Laptop Apple MacBook Pro con chip M4', 12000000.00, 'MBP-M4-14'),
('Camisa Lacoste', 'Camisa polo Lacoste talla L', 120000.00, 'LAC-L-BLU'),
('Jeans Diesel', 'Jeans Diesel slim talla 34', 250000.00, 'DSL-34-BLK'),
('Zapatillas Adidas', 'Zapatillas deportivas Adidas Ultraboost', 450000.00, 'ADS-UB-43-WHT'),
('Sofá modular', 'Sofá modular 4 piezas tela gris', 3200000.00, 'SOFA-MOD-4P'),
('Mesa extensible', 'Mesa de comedor extensible para 8 personas', 2200000.00, 'MESA-EXT-8P'),
('Balón Adidas', 'Balón de fútbol Adidas oficial', 150000.00, 'BAL-ADS-PRO'),
('Raqueta Head', 'Raqueta de tenis Head Graphene 360', 550000.00, 'RQT-HD-G360'),
('Cien años de soledad', 'Novela de Gabriel García Márquez', 55000.00, 'LIB-CIEN-SOL'),
('Atlas Geográfico', 'Atlas geográfico actualizado 2025', 95000.00, 'ATL-GEO-2025'),
('Muñeca LOL', 'Muñeca LOL Surprise edición especial', 75000.00, 'LOL-SURPRISE'),
('Lego Technic', 'Set de construcción Lego Technic coche', 350000.00, 'LEGO-TEC-CAR'),
('Aceite Castrol', 'Aceite sintético Castrol GTX 5W-40 1L', 45000.00, 'ACE-CAS-5W40'),
('Filtro Mann', 'Filtro de aire Mann para Mazda 3', 35000.00, 'FLT-MAN-MAZ3'),
('Crema Nivea', 'Crema hidratante Nivea Q10 100ml', 55000.00, 'CRM-NIV-Q10'),
('Vitamina D3', 'Suplemento vitamina D3 1000UI', 35000.00, 'VIT-D3-1000'),
('Cuaderno Norma', 'Cuaderno universitario 200 hojas', 12000.00, 'CUAD-NOR-200H'),
('Lápiz Staedtler', 'Lápiz HB Staedtler Noris', 3000.00, 'LAP-STD-HB'),
('Tijeras Fiskars', 'Tijeras de podar Fiskars profesionales', 45000.00, 'TJR-FIS-PROF'),
('Manguera Gardena', 'Manguera extensible Gardena 25m', 55000.00, 'MNG-GRD-25M');

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
('Pedro Sánchez', 'pedro.sanchez@email.com', '+57 320 123 4567', 'Calle 300 #60-80, Armenia'),
('Laura Morales', 'laura.morales@email.com', '+57 321 234 5678', 'Carrera 120 #25-45, Popayán'),
('Miguel Ángel Ruiz', 'miguel.ruiz@email.com', '+57 322 345 6789', 'Avenida 110 #40-60, Neiva'),
('Isabella Castro', 'isabella.castro@email.com', '+57 323 456 7890', 'Calle 180 #30-50, Villavicencio'),
('David Jiménez', 'david.jimenez@email.com', '+57 324 567 8901', 'Carrera 85 #50-70, Tunja'),
('Gabriela Vargas', 'gabriela.vargas@email.com', '+57 325 678 9012', 'Avenida 55 #70-90, Florencia'),
('Sebastián Mendoza', 'sebastian.mendoza@email.com', '+57 326 789 0123', 'Calle 220 #15-35, Yopal'),
('Valeria Gutiérrez', 'valeria.gutierrez@email.com', '+57 327 890 1234', 'Carrera 65 #80-100, Mocoa'),
('Mateo Herrera', 'mateo.herrera@email.com', '+57 328 901 2345', 'Avenida 125 #20-40, San Andrés'),
('Sofía Ramírez', 'sofia.ramirez@email.com', '+57 329 012 3456', 'Calle 75 #45-65, Riohacha');

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

('pedros', 'pedro.sanchez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Pedro', 'Sánchez', '+57 320 123 4567', 'Calle 300 #60-80, Armenia'),
('lauram', 'laura.morales@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Laura', 'Morales', '+57 321 234 5678', 'Carrera 120 #25-45, Popayán'),
('miguelr', 'miguel.ruiz@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Miguel Ángel', 'Ruiz', '+57 322 345 6789', 'Avenida 110 #40-60, Neiva'),
('isabellac', 'isabella.castro@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Isabella', 'Castro', '+57 323 456 7890', 'Calle 180 #30-50, Villavicencio'),
('davidj', 'david.jimenez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'David', 'Jiménez', '+57 324 567 8901', 'Carrera 85 #50-70, Tunja'),
('gabrielav', 'gabriela.vargas@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Gabriela', 'Vargas', '+57 325 678 9012', 'Avenida 55 #70-90, Florencia'),
('sebastianm', 'sebastian.mendoza@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Sebastián', 'Mendoza', '+57 326 789 0123', 'Calle 220 #15-35, Yopal'),
('valeriag', 'valeria.gutierrez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Valeria', 'Gutiérrez', '+57 327 890 1234', 'Carrera 65 #80-100, Mocoa'),
('mateoh', 'mateo.herrera@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Mateo', 'Herrera', '+57 328 901 2345', 'Avenida 125 #20-40, San Andrés'),
('sofiar', 'sofia.ramirez@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Sofía', 'Ramírez', '+57 329 012 3456', 'Calle 75 #45-65, Riohacha');