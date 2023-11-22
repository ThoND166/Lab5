<?php
class DB_CONNECT {
    private $conn;

    function __construct() {
        $this->connect();
    }

    function __destruct() {
        $this->close();
    }

    function connect() {
        require_once __DIR__ . '/db_config.php';
        $this->conn = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

        if ($this->conn->connect_error) {
            die("Connection failed: " . $this->conn->connect_error);
        }

        return $this->conn;
    }

    function close() {
        if ($this->conn) {
            $this->conn->close();
        }
    }
}
?>
