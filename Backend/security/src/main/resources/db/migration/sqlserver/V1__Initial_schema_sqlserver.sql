-- Initial schema for Inventory System - SQL Server Version
-- Version: 1.0.0

-- Categories table
CREATE TABLE categories (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(MAX)
);

-- Suppliers table
CREATE TABLE suppliers (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    contact_email NVARCHAR(100) NOT NULL UNIQUE,
    contact_phone NVARCHAR(20),
    address NVARCHAR(MAX)
);

-- Products table
CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(10,2) NOT NULL,
    sku NVARCHAR(50) NOT NULL UNIQUE,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Supplier-Product relationship table
CREATE TABLE supplier_products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT unique_supplier_product UNIQUE (supplier_id, product_id)
);

-- Clients table
CREATE TABLE clients (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    phone NVARCHAR(20),
    address NVARCHAR(MAX)
);

-- Orders table
CREATE TABLE orders (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    client_id BIGINT NOT NULL,
    order_date DATETIME2 NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Users table
CREATE TABLE user_table (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(50),
    last_name NVARCHAR(50),
    phone NVARCHAR(20),
    address NVARCHAR(MAX)
);

-- Indexes for better performance
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_orders_client ON orders(client_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_clients_email ON clients(email);
CREATE INDEX idx_user_username ON user_table(username);
CREATE INDEX idx_user_email ON user_table(email);