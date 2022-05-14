<%@page import="servermodel.Inventory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inventory Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.5.0.min.js"></script>
<script src="Components/inventory.js"></script>

</head>
<body>
	<div align="center">
		<h2 class="mb-3">Create a Inventory Record</h2>

		<div class="mt-3 ml-3">
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
		</div>

		<form id="formInventory" name="formInventory" class="justify-content-center" style ="width:60%">

			<div class="form-row">
				<div class="form-group col-md-6">
					<label class="form-label"> Inventory Item Code </label> 
					<input type="text" class="form-control" id="invCode" name="invCode" placeholder="Inventory Item Code">
				</div>
				<div class="form-group col-md-6">
					<label class="form-label">Inventory Item Name</label> 
					<input type="text" class="form-control" id="invName" name="invName" placeholder="Inventory Item Name">
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label class="form-label">Manufacturing Year</label> 
					<input type="text" class="form-control" id="manufact" name="manufact" placeholder="Manufacturing Year">
								
				</div>
				<div class="form-group col-md-6">
					<label class="form-label">Stock Quantity</label> 
					<input type="text" class="form-control" id="qty" name="qty" placeholder="Stock Quantity">
									
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label class="form-label">Latest Repair Date</label> 
					<input type="text" class="form-control" id="repair" name="repair" placeholder="Latest Repair Date">
				</div>
				<div class="form-group col-md-6">
					<label class="form-label">Handled By</label> 
					<input type="text" class="form-control" id="handle" name="handle" placeholder="Handled By">
				</div>
			</div>

			<br><br>
			<div>
				<button class="btn btn-primary mr-3" id="saveInv" name="saveInv" type="button" style ="width:30%; height:100%">Save Inventory</button>
				<button class="btn btn-info" id="clear" type="button" style ="width:30%; height:100%">Clear</button>
				<input type="hidden" class="form-control" id="hidInvIDSave"
					name="hidInvIDSave" value="">
			</div>
		</form>
		<br><br>
		<div class="ml-5">
			<h2 class="mb-3">All Inventory Records</h2>
		</div>
		<div class="col-12 mb-5 table table-responsive container-fluid table-striped row justify-content-center" id="invGrid">

			<%
						Inventory invObj = new Inventory();
						out.print(invObj.readInventory());
			%>

		</div>

	</div>

</body>
</html>
