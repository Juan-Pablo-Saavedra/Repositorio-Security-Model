-- SQL Server Schema for Inventory System
CREATE DATABASE inventory_db;

USE inventory_db;

-- Categories table
CREATE TABLE category (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NTEXT,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50)
);

-- Products table
CREATE TABLE product (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    description NTEXT,
    price DECIMAL(10,2) NOT NULL,
    sku NVARCHAR(50) UNIQUE,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50)
);

-- Suppliers table
CREATE TABLE supplier (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    contact_email NVARCHAR(100),
    contact_phone NVARCHAR(20),
    address NTEXT,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50)
);

-- Clients table
CREATE TABLE client (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    phone NVARCHAR(20),
    address NTEXT,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50)
);

-- Stock table
CREATE TABLE stock (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    min_quantity INT DEFAULT 0,
    max_quantity INT,
    location NVARCHAR(100),
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Movements table
CREATE TABLE movement (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL,
    type NVARCHAR(20) NOT NULL CHECK (type IN ('IN', 'OUT', 'ADJUSTMENT')),
    quantity INT NOT NULL,
    reason NTEXT,
    movement_date DATETIME2 DEFAULT GETDATE(),
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Orders table
CREATE TABLE [order] (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    client_id BIGINT NOT NULL,
    order_date DATETIME2 DEFAULT GETDATE(),
    total DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50),
    FOREIGN KEY (client_id) REFERENCES client(id)
);

-- Order Details table
CREATE TABLE order_detail (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    created_by NVARCHAR(50),
    updated_by NVARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES [order](id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Pivot tables
CREATE TABLE product_category (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE supplier_product (
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    supply_price DECIMAL(10,2),
    PRIMARY KEY (supplier_id, product_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE order_product (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES [order](id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);