-- Initial schema for Inventory System - PostgreSQL Version
-- Version: 1.0.0

-- Categories table
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- Suppliers table
CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    contact_email VARCHAR(100),
    contact_phone VARCHAR(20),
    address TEXT
);

-- Products table
CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE
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
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT
);

-- Orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    order_date TIMESTAMP NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    address TEXT
);

-- Indexes for better performance
CREATE INDEX idx_product_sku ON product(sku);
CREATE INDEX idx_orders_client ON orders(client_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_clients_email ON clients(email);
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);