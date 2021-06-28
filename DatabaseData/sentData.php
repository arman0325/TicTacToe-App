<?php

$HostName = "127.0.0.1";
$HostUser = "arman";
$HostPass = "IuuKQEBkWpX0CNKB";
$DatabaseName = "tictactoe";
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $id = $_GET['id'];
 $score = $_GET['score'];
 $gameTime = $_GET['gameTime'];
 $Sql_Query = "insert into data (id,score,gameTime) values ('$id','$score','$gameTime')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Data Submit Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>