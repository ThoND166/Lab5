<?php
$response = array();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (
        isset($_POST['name']) && !empty($_POST['name']) &&
        isset($_POST['price']) && !empty($_POST['price']) &&
        isset($_POST['description'])
    ) {
        $name = $_POST['name'];
        $price = $_POST['price'];
        $description = $_POST['description'];

        // Kết nối đến cơ sở dữ liệu
        require_once __DIR__ . '/db_connect.php';
        $db = new DB_CONNECT();
        $conn = $db->connect();

        if ($conn) {
            // Chuẩn bị truy vấn SQL
            $stmt = $conn->prepare("INSERT INTO products(name, price, description) VALUES (?, ?, ?)");
            $stmt->bind_param("sds", $name, $price, $description);

            // Thực thi truy vấn
            if ($stmt->execute()) {
                $response["success"] = 1;
                $response["message"] = "Product successfully created.";
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred while adding the product.";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Database connection failed.";
            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing.";
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Invalid request method.";
    echo json_encode($response);
}
?>
