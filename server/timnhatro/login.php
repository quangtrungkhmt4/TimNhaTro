<?php
	require "dbConnect.php";


	$user = $_POST['userLOGIN'];
	$pass = $_POST['passLOGIN'];

	$query = "SELECT * FROM tblUsers WHERE USER = '$user' AND PASSWORD = '$pass'";

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
		echo "fail";
	}else{
		$userID = $array[0];
		$id = [];
		foreach ($userID as $key => $value) {
			$id[] = $value;
		// $id = $array[0][0];
		// var_dump($id);die;
		}
		echo $id[0]."-".$id[1]."-".$id[2]."-".$id[3]."-".$id[4];
	}
?>