$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ===========================================
$(document).on("click", "#btnSave", function(event)
{ 
		// Clear alerts---------------------
		 $("#alertSuccess").text(""); 
		 $("#alertSuccess").hide(); 
		 $("#alertError").text(""); 
		 $("#alertError").hide(); 

		// Form validation-------------------
		var status = validateInvForm(); 
		if (status != true) 
		 { 
			 $("#alertError").text(status); 
			 $("#alertError").show(); 
		 	return; 
		 } 

		// If valid------------------------
		var type = ($("#hidInvIDSave").val() == "") ? "POST" : "PUT"; 
 
 		$.ajax( 
 		{ 
			url : "InventoryAPI", 
			type : type, 
			data : $("#formInventory").serialize(), 
			dataType : "text", 
			complete : function(response, status) 
			{ 
				 onInvSaveComplete(response.responseText, status); 
			} 
 		}); 
});

function onInvSaveComplete(response, status)
{ 
		if (status == "success") 
 		{ 
 			var resultSet = JSON.parse(response); 
 
 			if (resultSet.status.trim() == "success") 
 			{ 
 				$("#alertSuccess").text("Successfully saved a inventory item."); 
 				$("#alertSuccess").show(); 
 				
 				$("#divInventoryGrid").html(resultSet.data); 
 			} else if (resultSet.status.trim() == "error") 
 			{ 
				 $("#alertError").text(resultSet.data); 
 				$("#alertError").show(); 
 			} 
 		} else if (status == "error") 
 		{ 
 			$("#alertError").text("Error while saving."); 
 			$("#alertError").show(); 
 		} else
 		{ 
 			$("#alertError").text("Unknown error while saving.."); 
 			$("#alertError").show(); 
 		} 
 		 
 		 	$("#hidInvIDSave").val(""); 
 			$("#formInventory")[0].reset(); 
}

$(document).on("click", ".btnUpdate", function(event)
{ 
		$("#hidInvIDSave").val($(this).data("invid"));
		$("#invID").val($(this).closest("tr").find('td:eq(0)').text());  
 		$("#txtCode").val($(this).closest("tr").find('td:eq(1)').text()); 
 		$("#txtName").val($(this).closest("tr").find('td:eq(2)').text()); 
 		$("#numQty").val($(this).closest("tr").find('td:eq(3)').text()); 
 		$("#ddlYear").val($(this).closest("tr").find('td:eq(4)').text()); 
 		$("#repairDate").val($(this).closest("tr").find('td:eq(5)').text());
});

$(document).on("click", ".btnRemove", function(event)
{ 
 	$.ajax( 
 	{ 
 		url : "InventoryAPI", 
 		type : "DELETE", 
 		data : "invID=" + $(this).data("invid"),
 		dataType : "text", 
 		complete : function(response, status) 
 		{ 
 			onInvDeleteComplete(response.responseText, status); 
 		} 
 	}); 
});

function onInvDeleteComplete(response, status)
{ 
	if (status == "success") 
 	{ 
 		var resultSet = JSON.parse(response); 
 
 		if (resultSet.status.trim() == "success") 
 		{ 
 			$("#alertSuccess").text("Successfully deleted a inventory item."); 
			$("#alertSuccess").show();
			 
 			$("#divInventoryGrid").html(resultSet.data); 
 		} else if (resultSet.status.trim() == "error") 
 		{ 
 			$("#alertError").text(resultSet.data); 
 			$("#alertError").show(); 
 		}
 		 
 	} else if (status == "error") 
 	{ 
 		$("#alertError").text("Error while deleting."); 
 		$("#alertError").show(); 
 	} else
	{ 
 		$("#alertError").text("Unknown error while deleting.."); 
 		$("#alertError").show(); 
 	} 
}

// CLIENT-MODEL================================================================
function validateInventoryForm() 
{ 
	// CODE
	if ($("#txtCode").val().trim() == "") 
	 { 
	 		return "Insert item code."; 
	 } 
	
	// ITEM NAME
	if ($("#txtName").val().trim() == "") 
	 { 
	 		return "Select item name."; 
	 }

	// QTY
	var tmpQty = $("#numQty").val().trim(); 
	if (!$.isNumeric(tmpQty)) 
	 { 
	 		return "Insert a numerical value for stock qty"; 
	 }
	 
	 // MANUFACT
	if ($("#ddlYear").val().trim() == "") 
	 { 
	 		return "Insert year of manufacturing"; 
	 } 
	 
	 // REPAIR Date-------------------------------
	if ($("#repairDate").val().trim() == "") 
	 { 
	 		return "Insert latest reair Date."; 
	 } 

	
	return true; 
}

 		
