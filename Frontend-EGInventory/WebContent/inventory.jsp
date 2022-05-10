<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="servermodel.Inventory"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inventory Records</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/inventory.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-8">

				<h1 class="m-3">Inventory Records</h1>

				<form id="formInventory" name="formInventory">

					
					<!-- INVENTORY ID -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblID">Inventory ID: </span>
						</div>
						<input type="text" id="invID" name="invID"  >
					</div>
					<br>
					
					<!-- ITEM CODE -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblCode">Item Code: </span>
						</div>
						<input type="text" id="txtCode" name="txtCode">
					</div>
					<br>

					<!-- ITEM NAME -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">Item Name: </span>
						</div>
						<input type="text" id="txtName" name="txtName">
					</div>
					<br>

					<!-- ITEM STOCK QTY -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblQty">Stock Quantity:
							</span>
						</div>
						<input type="text" id="numQty" name="numQty">
					</div>
					<br>

					<!-- Manufact YEAR -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblManufact">Manufacturing
								Year: </span>
						</div>
						<input type="text" id="ddlYear" name="ddlYear">
					</div>
					<br>

					<!-- Latest RepairDate -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblRepair">Latest
								Repair Date: </span>
						</div>
						<input type="text" id="repairDate" name="repairDate" placeholder="YYYY-MM-DD">

					</div>

					<br> 
					
					<input type="button" id="btnSave" name="btnSave" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidInvIDSave" name="hidInvIDSave" value="">
				</form>
				<br>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divInventoryGrid">
					<%
 						Inventory invObj = new Inventory(); 
 						out.print(invObj.readInventory()); 
 					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>