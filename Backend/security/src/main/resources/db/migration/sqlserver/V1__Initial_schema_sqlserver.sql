-- Initial schema for Inventory System - SQL Server Version
-- Version: 1.0.0

-- Categories table
CREATE TABLE category (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(MAX)
);

-- Suppliers table
CREATE TABLE supplier (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    contact_email NVARCHAR(100),
    contact_phone NVARCHAR(20),
    address NVARCHAR(MAX)
);

-- Products table
CREATE TABLE product (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(10,2) NOT NULL,
    sku NVARCHAR(50) NOT NULL UNIQUE
);

-- Product-Category relationship table
CREATE TABLE product_category (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Supplier-Product relationship table
CREATE TABLE supplier_product (
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    supply_price DECIMAL(10,2),
    PRIMARY KEY (supplier_id, product_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
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
CREATE TABLE users (
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
CREATE INDEX idx_product_sku ON product(sku);
CREATE INDEX idx_orders_client ON orders(client_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_clients_email ON clients(email);
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);