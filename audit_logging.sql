-- 1. Create a sample table to demonstrate auditing
CREATE TABLE IF NOT EXISTS employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    salary DECIMAL(10, 2)
);

-- 2. Create the audit log table
CREATE TABLE IF NOT EXISTS audit_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    action_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    record_id INT NOT NULL,
    old_data JSON,
    new_data JSON,
    changed_by VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Trigger for INSERT operations
DELIMITER //
CREATE TRIGGER trg_employees_insert
AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, action_type, record_id, new_data, changed_by)
    VALUES (
        'employees',
        'INSERT',
        NEW.id,
        JSON_OBJECT('name', NEW.name, 'department', NEW.department, 'salary', NEW.salary),
        USER()
    );
END;
//
DELIMITER ;

-- 4. Trigger for UPDATE operations
DELIMITER //
CREATE TRIGGER trg_employees_update
AFTER UPDATE ON employees
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (table_name, action_type, record_id, old_data, new_data, changed_by)
    VALUES (
        'employees',
        'UPDATE',
        NEW.id,
        JSON_OBJECT('name', OLD.name, 'department', OLD.department, 'salary', OLD.salary),
        JSON_OBJECT('name', NEW.name, 'department', NEW.department, 'salary', NEW.salary),
        USER()
    );
END;
//
DELIMITER ;

-- 5. View for Daily Activity Reports
CREATE OR REPLACE VIEW vw_daily_activity_report AS
SELECT 
    DATE(changed_at) AS activity_date,
    table_name,
    action_type,
    COUNT(*) AS operation_count
FROM 
    audit_log
GROUP BY 
    DATE(changed_at),
    table_name,
    action_type
ORDER BY 
    activity_date DESC, table_name, action_type;

-- ==========================================
-- Sample Usage / Test Cases
-- ==========================================
/*
-- Insert a new record
INSERT INTO employees (name, department, salary) VALUES ('Alice Smith', 'Engineering', 85000);

-- Update an existing record
UPDATE employees SET salary = 90000 WHERE name = 'Alice Smith';

-- View the raw audit log
SELECT * FROM audit_log;

-- View the daily activity report
SELECT * FROM vw_daily_activity_report;
*/
