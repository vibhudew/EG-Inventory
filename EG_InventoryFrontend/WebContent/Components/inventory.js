//hide alert
$(document).ready(function()
		{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#clear", function(event) {

	$("#formInventory")[0].reset();
	$("#alertError").hide();
});

$(document).on("click", "#saveInv", function(event) {

	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	var type = ($("#hidInvIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url: "InventoryAPI",
		type: type,
		data: $("#formInventory").serialize(),
		dataType: "text",
		complete: function(response, status) {
			onItemSaveComplete(response.responseText, status);

			$("#alertSuccess").fadeTo(2000, 500).slideUp(500, function() {
				$("#alertSuccess").slideUp(500);
			});
		}
	});

});

function onItemSaveComplete(response, status) {

	if (status == "success") {

		//console.log(response);
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully saved a inventory record.");
			$("#alertSuccess").show();
			$("#invGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {

			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if (status == "error") {

		$("#alertError").text("Error while saving.");
		$("#alertError").show();

	} else {

		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidInvIDSave").val("");
	$("#formInventory")[0].reset();
}

//Update
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidInvIDSave").val($(this).data("invid"));
	$("#invID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#invCode").val($(this).closest("tr").find('td:eq(1)').text());
	$("#invName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#manufact").val($(this).closest("tr").find('td:eq(3)').text());
	$("#qty").val($(this).closest("tr").find('td:eq(4)').text());
	$("#repair").val($(this).closest("tr").find('td:eq(5)').text());
	$("#handle").val($(this).closest("tr").find('td:eq(6)').text());
	

});

//remove
$(document).on("click", ".btnRemove", function(event) {


	$.ajax({
		url: "InventoryAPI",
		type: "DELETE",
		data: "invID=" + $(this).data("invid"),
		dataType: "text",
		complete: function(response, status) {
			onItemDeleteComplete(response.responseText, status);
			
			$("#alertSuccess").fadeTo(2000, 500).slideUp(500, function() {
				$("#alertSuccess").slideUp(500);
			});

		}
	});
});

function onItemDeleteComplete(response, status) {

	if (status == "success") {

		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted a inventory record.");
			$("#alertSuccess").show();
			$("#invGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {

			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {

		$("#alertError").text("Error while deleting.");
		$("#alertError").show();

	} else {

		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//client model
function validateItemForm() {

	if ($("#invCode").val().trim() == "") {
		return "Enter inventory item code.";
	}

	if ($("#invName").val().trim() == "") {
		return "Enter inventory item name.";
	}
	
	

	if ($("#manufact").val().trim() == "") {
		return "Enter year of manufacuring.";
	}
	
	var tmpQty = $("#qty").val().trim();
	if (!$.isNumeric(tmpQty))
	{
		return "Insert a numerical value for stock quantity.";
	}

	
	if ($("#repair").val().trim() == "") {
		return "Enter latest repair date.";
	}
	
	if ($("#handle").val().trim() == "") {
		return "Enter employee ID of db handler.";
	}
	return true;
}