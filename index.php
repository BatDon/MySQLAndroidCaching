<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "MySQLAndroidCaching";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    echo "error Connecting";
} 


$sql = "SELECT id, firstName, lastName, address, phone FROM clientsInfo";
$result = $conn->query($sql);

if (!$result) {
    echo("result has an error");
    echo(mysqli_error($conn));
}

$mysqlrows=array();
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
    	$jsonRow=array("id"=>(int)$row["id"], "firstName"=>$row["firstName"], "lastName"=>$row["lastName"], "address"=>$row["address"], "phone"=>$row["phone"]);
        $mysqlrows[]=$jsonRow;
    }
} else {
    echo "0 results";
}

$myObj = new \stdClass();
$myObj->client = $mysqlrows;

echo json_encode($myObj);

$conn->close();
?>

