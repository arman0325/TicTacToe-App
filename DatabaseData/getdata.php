<?php
$servername = "localhost";
$username = "arman";
$password = "IuuKQEBkWpX0CNKB";
$database = "tictactoe";
 
 
$conn = new mysqli($servername, $username, $password, $database);
 
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$results = array(); 
 
$sql = "SELECT id,score,gameTime FROM data ORDER BY score DESC,gameTime;";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($id, $score, $gameTime);
 
while($stmt->fetch()){
 
 $temp = [
 'id'=>$id,
 'score'=>$score,
 'gameTime'=>$gameTime
 ];
 
 array_push($results, $temp);
}
 
echo json_encode($results);