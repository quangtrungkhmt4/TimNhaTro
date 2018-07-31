<?php
	require "dbConnect.php";


	$user = $_POST['userREGISTER'];
	$pass = $_POST['passREGISTER'];
	$name = $_POST['nameREGISTER'];
	$phone = $_POST['phoneREGISTER'];

	$query = "INSERT INTO  tblUsers VALUES(null, '$user', '$pass', '$name', '$phone', '')";

	if (mysqli_query($connect, $query)) {
		echo "success";
	}else{
		echo "fail";
	}

?>