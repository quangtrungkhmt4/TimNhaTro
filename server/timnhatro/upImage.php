<?php
	//require "dbConnect.php";
	$target_dir = "/images";
	$image = $_POST['StringBase'];

	if (!file_exists($target_dir)) {
		mkdir($target_dir, 0777, true);
	}
	$target_dir = $target_dir ."/". rand() ."_". time() . ".jpeg";
	if (file_put_contents($target_dir, base64_decode($image))) {
		echo $target_dir;
	}else{
		echo "fail";
	}


?>