<?php
// api.php - API endpoints for data operations
header('Content-Type: application/json');
require_once 'db.php';

$action = $_GET['action'] ?? '';

try {
    switch ($action) {
        case 'get_products':
            $stmt = $pdo->query('SELECT * FROM products ORDER BY id DESC');
            echo json_encode($stmt->fetchAll());
            break;

        case 'add_product':
            $data = json_decode(file_get_contents('php://input'), true);
            if (!$data || !isset($data['name'], $data['price'])) {
                throw new Exception('Invalid product data');
            }
            $stmt = $pdo->prepare('INSERT INTO products (name, category, stock, price) VALUES (?, ?, ?, ?)');
            $stmt->execute([$data['name'], $data['category'], $data['stock'], $data['price']]);
            echo json_encode(['status' => 'success', 'id' => $pdo->lastInsertId()]);
            break;

        case 'update_stock':
            $data = json_decode(file_get_contents('php://input'), true);
            if (!$data || !isset($data['id'], $data['stock'])) {
                throw new Exception('Invalid update data');
            }
            $stmt = $pdo->prepare('UPDATE products SET stock = ? WHERE id = ?');
            $stmt->execute([$data['stock'], $data['id']]);
            echo json_encode(['status' => 'success']);
            break;

        case 'get_logs':
            $stmt = $pdo->query('SELECT * FROM audit_log ORDER BY changed_at DESC LIMIT 50');
            echo json_encode($stmt->fetchAll());
            break;

        case 'get_report':
            $stmt = $pdo->query('SELECT * FROM daily_activity_report');
            echo json_encode($stmt->fetchAll());
            break;

        default:
            http_response_code(400);
            echo json_encode(['error' => 'Invalid action']);
            break;
    }
}
catch (PDOException $e) { // Catch PDO specific exceptions for database errors
    http_response_code(500);
    echo json_encode(['error' => 'Database operation failed: ' . $e->getMessage()]);
}
catch (Exception $e) { // Catch general exceptions for other API errors
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}
?>
