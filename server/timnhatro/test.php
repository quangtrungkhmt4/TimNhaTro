<?php
	$connect = mysqli_connect("localhost","root","","vietnam");
	mysqli_query($connect, "SET NAMES 'utf8'");

	$query = "select * from district";

	$data = mysqli_query($connect, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		echo $row['name'];
	}
?>