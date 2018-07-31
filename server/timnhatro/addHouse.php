<?php
	require "dbConnect.php";

	$title = $_POST['TITLE'];
	$address = $_POST['ADDRESS'];
	$image = $_POST['IMAGE'];
	$desc = $_POST['DESC'];
	$contact = $_POST['CONTACT'];
	$acreage = $_POST['ACREAGE'];
	$price = $_POST['PRICE'];
	$idUser = $_POST['IDUSER'];

	// $title = "hahaha";
	// $address = "hahahaha";
	// $image = "sdsds";
	// $desc = "sssssssss";
	// $contact = "ssssssssss";
	// $acreage = 12;
	// $price = 2;
	// $idUser = 1;

	$query = "INSERT INTO tblHouses VALUES(null, '$title', '$address', 1, '$image','$desc','$contact', $acreage, $price, 'today', 1, 1, $idUser)";

	if (mysqli_query($connect, $query)) {
		echo "success";
	}else{
		echo "fail";
	}

?>