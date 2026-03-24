USE student_db;

-- Customers table
CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    order_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Insert sample data
INSERT IGNORE INTO customers (id, name, email) VALUES 
(1, 'Alice Johnson', 'alice@example.com'),
(2, 'Bob Smith', 'bob@example.com'),
(3, 'Charlie Brown', 'charlie@example.com');

INSERT IGNORE INTO products (id, name, price) VALUES 
(1, 'Laptop', 1200.00),
(2, 'Smartphone', 800.00),
(3, 'Headphones', 150.00),
(4, 'Webcam', 90.00);

-- Insert sample orders
INSERT INTO orders (customer_id, product_id, order_date, total_amount) VALUES 
(1, 1, '2024-03-20', 1200.00),
(1, 4, '2024-03-22', 90.00),
(2, 2, '2024-03-21', 800.00),
(1, 3, '2024-03-23', 150.00),
(3, 1, '2024-03-19', 1200.00);
