<?php
	require "dbConnect.php";



	$query = "SELECT * FROM tblHouses";

	$data = mysqli_query($connect, $query);

	class House{
		function House($id, $title, $address, $image, $desc, $contact, $acreage, $price, $idUser){
			$this->IDHOUSE = $id;
			$this->TITLE = $title;
			$this->ADDRESS = $address;
			$this->IMAGE = $image;
			$this->DESC = $desc;
			$this->CONTACT = $contact;
			$this->ACREAGE = $acreage;
			$this->PRICE = $price;
			$this->IDUSER = $idUser;
		}
	}

	$array = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($array, new House($row['IDHOUSE'], $row['TITLE'], $row['ADDRESS'], $row['IMAGE'], $row['DESC'], $row['CONTACT'], $row['ACREAGE'], $row['PRICE'], $row['IDUSER']));
	}

	echo json_encode($array);
?>