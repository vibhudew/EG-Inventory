<%@page import="servermodel.Inventory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inventory Records</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-8">

				<h1 class="m-3">Inventory Records</h1>

				<form id="formInventory" name="formInventory">

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
						<input type="number" id="numQty" name="numQty" min=1>
					</div>
					<br>

					<!-- Manufact YEAR -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblManufact">Manufacturing
								Year: </span>
						</div>

						<select id="ddlYear" name="ddlYear">
							<option value="0">--Select Manufacturing Year--</option>
						</select>

					</div>
					<br>

					<!-- Latest RepairDate -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblRepair">Latest
								Repair Date: </span>
						</div>
						<input type="date" id="repairDate" name="repairDate"
							min="2015-01-01">

					</div>
					

					<br> <input type="button" id="btnSave" name="btnSave" value="Save" class="btn btn-primary"> 
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