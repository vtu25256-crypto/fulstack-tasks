<?php
header('Content-Type: application/json');
require_once 'db.php';

$action = $_GET['action'] ?? '';

try {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $data = json_decode(file_get_contents('php://input'), true);
        
        if (!$data || !isset($data['username'], $data['password'])) {
            throw new Exception('Please enter username and password');
        }

        $username = trim($data['username']);
        $password = $data['password'];

        $stmt = $pdo->prepare('SELECT id, username, password FROM users WHERE username = ?');
        $stmt->execute([$username]);
        $user = $stmt->fetch();

        if ($user && password_verify($password, $user['password'])) {
            echo json_encode(['status' => 'success', 'message' => 'Login successful! Welcome, ' . $user['username']]);
        } else {
            throw new Exception('Invalid username or password');
        }
        exit;
    }
} catch (Exception $e) {
    http_response_code(401);
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
}
?>
