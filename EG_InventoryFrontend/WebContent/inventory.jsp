<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="servermodel.Inventory"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inventory Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/inventory.js"></script>
</head>
<body>
	<div align="center">
		<br>
		<br>
		<h2 class="mb-3">Create a Inventory Record</h2>
		<form id="formInventory" name="formInventory"
			class="justify-content-center" style="width: 60%">

			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>

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
					<!-- <input type="text" class="form-control" id="manufact" name="manufact" placeholder="Manufacturing Year"> -->
					
					<select class="form-control" id="manufact" name="manufact">
							<option value="0">--Select Manufacturing Year--</option>
					</select>

				</div>
				<div class="form-group col-md-6">
					<label class="form-label">Stock Quantity</label> 
					<input class="form-control" type="number" step="any" id="stockQty" name="stockQty"  min=1>

				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-6">
					<label class="form-label">Latest Repair Date</label> 
					<!--  <input type="text" class="form-control" id="repair" name="repair" placeholder="Latest Repair Date"> -->
					
					<input class="form-control" type="date" id="repair" name="repair" min="2015-01-01">
				</div>
				<div class="form-group col-md-6">
					<label class="form-label">Handled By</label> 
					<input type="text" class="form-control" id="handledBy" name="handledBy" placeholder="Handled By">
				</div>
			</div>


			<br>
			<div>
				<button class="btn btn-primary mr-3" id="btnSave" name="btnSave" type="button" style="width: 30%; height: 100%">Save Inventory</button>
				<button class="btn btn-info" id="clear" type="button" style="width: 30%; height: 100%">Clear</button>
				<input type="hidden" class="form-control" id="hidInvIDSave" name="hidInvIDSave" value="">
			</div>
		</form>
		<br> <br>
		<div class="ml-5">
			<h2 class="mb-3">All Inventory Records</h2>
		</div>


		<br>
		<div id="divInvGrid"
			class="col-12 mb-5 table table-responsive container-fluid table-striped row justify-content-center">
			<%
			Inventory invObj = new Inventory();
			out.print(invObj.readInventory());
			%>
		</div>

	</div>

</body>
</html>