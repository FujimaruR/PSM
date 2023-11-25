<?php 

	class DB{
		private $host;
		private $db;
		private $user;
		private $password;
		private $charset;
		

		public function __construct()
		{
			$this->host = 'localhost';
			$this->db = 'id21563249_vetbuddy';
			$this->user = 'id21563249_username';
			$this->password = 'PasitoTunTun!123';
			$this->charset = 'utf8mb4';
		}

		function connect(){

			try{
				$conn = "mysql:host=".$this->host.";dbname=".$this->db.";charset=".$this->charset;
				$options = [
					PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
					PDO::ATTR_EMULATE_PREPARES => false
				];
				$pdo = new PDO($conn, $this->user, $this->password);
				
				return $pdo;

			}catch(PDOException $e){
				echo"No me conencte";
			}
		}
	
	}

?>