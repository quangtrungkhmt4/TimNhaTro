<?php
	require "dbConnect.php";


	$user = $_POST['userREGISTER'];

	$query = "SELECT * FROM tblUsers WHERE USER = '$user'";

	$data = mysqli_query($connect, $query);

	class User{
		function User($idUser, $userName, $password, $name, $phone){
			$this->IDUSER = $idUser;
			$this->USER = $userName;
			$this->PASSWORD = $password;
			$this->NAME = $name;
			$this->PHONE = $phone;
			$this->IMAGE = "";
		}
	}

	$array = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($array, new User($row['IDUSER'], $row['USER'], $row['PASSWORD'], $row['NAME'], $row['PHONE'], $row['IMAGE']));
	}

	if (count($array) == 0) {
		echo "success";
	}else{
		echo "fail";
	}
?>