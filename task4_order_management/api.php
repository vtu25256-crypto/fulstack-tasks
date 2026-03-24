<?php
header('Content-Type: application/json');
require_once 'db.php';

$action = $_GET['action'] ?? '';

try {
    if ($action === 'get_history') {
        // JOIN query to get order history
        $stmt = $pdo->query('
            SELECT 
                o.id as order_id, 
                c.name as customer_name, 
                p.name as product_name, 
                o.order_date, 
                o.total_amount 
            FROM orders o
            JOIN customers c ON o.customer_id = c.id
            JOIN products p ON o.product_id = p.id
            ORDER BY o.order_date DESC
        ');
        echo json_encode($stmt->fetchAll());
        exit;
    }

    if ($action === 'get_insights') {
        // Subquery for highest value order
        $highest = $pdo->query('
            SELECT o.total_amount, c.name as customer_name, p.name as product_name
            FROM orders o
            JOIN customers c ON o.customer_id = c.id
            JOIN products p ON o.product_id = p.id
            WHERE o.total_amount = (SELECT MAX(total_amount) FROM orders)
            LIMIT 1
        ')->fetch();

        // Subquery for most active customer
        $active = $pdo->query('
            SELECT c.name, COUNT(o.id) as order_count
            FROM customers c
            JOIN orders o ON c.id = o.customer_id
            GROUP BY c.id
            ORDER BY order_count DESC
            LIMIT 1
        ')->fetch();

        echo json_encode([
            'highest_order' => $highest,
            'most_active' => $active
        ]);
        exit;
    }

} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
}
?>
