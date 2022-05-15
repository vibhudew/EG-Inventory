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

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
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
					onItemSaveComplete(response.responseText, status);
				}
			});
	
		});

function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved a inventory record.");
			$("#alertSuccess").show();
			$("#divInvGrid").html(resultSet.data);
						
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
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidInvIDSave").val($(this).data("invid"));
	$("#invCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#invName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#manufact").val($(this).closest("tr").find('td:eq(2)').text());
	$("#stockQty").val($(this).closest("tr").find('td:eq(3)').text());
	$("#repair").val($(this).closest("tr").find('td:eq(4)').text());
	$("#handledBy").val($(this).closest("tr").find('td:eq(5)').text());
});

//DELETE============================================
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
					onItemDeleteComplete(response.responseText, status);
				}
			});
		});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted a inventory record.");
			$("#alertSuccess").show();
			$("#divInvGrid").html(resultSet.data);
			
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

//CLIENT-MODEL================================================================
function validateItemForm()
{
//	Inventory Item Code
	if ($("#invCode").val().trim() == "")
	{
		return "Insert Inventory Item Code.";
	}
//	Inventory Item Name
	if ($("#invName").val().trim() == "")
	{
		return "Insert Inventory Item Name.";
	}

//	Manufacturing Year
	if ($("#manufact").val().trim() == "")
	{
		return "Insert Manufacturing Year.";
	}
//	Stock Qty is numerical value
	var tmpQty = $("#stockQty").val().trim();
	if (!$.isNumeric(tmpQty))
	{
		return "Insert a numerical value for Stock Qty.";
	}

	
//	Repair
	if ($("#repair").val().trim() == "")
	{
		return "Insert a date of latest repairing.";
	}
	
	if ($("#handledBy").val().trim() == "")
	{
		return "Insert a empID of db handler.";
	}
	return true;
}

//To get years list for manufact year
window.onload = function () {
        //Reference the DropDownList.
        var ddlYear = document.getElementById("manufact");
 
        //Determine the Current Year.
        var currentYear = (new Date()).getFullYear();
 
        //Loop and add the Year values to DropDownList.
        for (var i = 2000; i <= currentYear; i++) {
            var option = document.createElement("OPTION");
            option.innerHTML = i;
            option.value = i;
            ddlYear.appendChild(option);
        }
 };



