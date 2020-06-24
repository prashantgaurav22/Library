<html>
<head>
	<title>Inventory New Home</title>
</head>
<body>
	<h2>Inventory Management - New Items</h2>
	<hr/>
	<form action="confirmAddBook" method="POST">
	
		<div>
			<label>Book Code: </label>
			<input type="number" name="bcode" required />
		</div>
		
		
		<div>
			<label>Title: </label>
			<input type="text" name="title" required />
		</div>
		
		
		<div>
			<label>Author: </label>
			<input type="text" name="author" required />
		</div>
				
		<div>
			<button>Add Book</button>
		</div>
	
	</form>
</nav>
</body>
</html>