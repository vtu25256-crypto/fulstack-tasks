-- Database Schema for Automated Logging
-- Designed for MySQL/MariaDB

-- 1. Products Table (Primary Data)
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    stock INT DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Audit Log Table (Historical Tracking)
CREATE TABLE IF NOT EXISTS audit_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    action_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    record_id INT NOT NULL,
    old_values TEXT,
    new_values TEXT,
    changed_by VARCHAR(100) DEFAULT 'SYSTEM',
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Triggers for Automated Logging

DELIMITER //

-- Trigger for INSERT operations
CREATE TRIGGER after_products_insert
AFTER INSERT ON products
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, action_type, record_id, new_values)
    VALUES (
        'products', 
        'INSERT', 
        NEW.id, 
        CONCAT('ID: ', NEW.id, ', Name: ', NEW.name, ', Stock: ', NEW.stock, ', Price: ', NEW.price)
    );
END//

-- Trigger for UPDATE operations
CREATE TRIGGER after_products_update
AFTER UPDATE ON products
FOR EACH ROW
BEGIN
    -- Log only if values actually changed
    IF (OLD.name <> NEW.name OR OLD.stock <> NEW.stock OR OLD.price <> NEW.price OR OLD.category <> NEW.category) THEN
        INSERT INTO audit_log (table_name, action_type, record_id, old_values, new_values)
        VALUES (
            'products', 
            'UPDATE', 
            NEW.id,
            CONCAT('Name: ', OLD.name, ', Stock: ', OLD.stock, ', Price: ', OLD.price),
            CONCAT('Name: ', NEW.name, ', Stock: ', NEW.stock, ', Price: ', NEW.price)
        );
    END IF;
END//

DELIMITER ;

-- 4. View for Daily Activity Reports
CREATE OR REPLACE VIEW daily_activity_report AS
SELECT 
    DATE(changed_at) AS report_date,
    action_type,
    COUNT(*) AS total_actions,
    GROUP_CONCAT(DISTINCT table_name) AS affected_tables
FROM audit_log
GROUP BY report_date, action_type
ORDER BY report_date DESC;

-- Verification Queries (Optional to run)
-- INSERT INTO products (name, category, stock, price) VALUES ('Wireless Mouse', 'Electronics', 50, 25.99);
-- UPDATE products SET stock = 45 WHERE name = 'Wireless Mouse';
-- SELECT * FROM audit_log;
-- SELECT * FROM daily_activity_report;
