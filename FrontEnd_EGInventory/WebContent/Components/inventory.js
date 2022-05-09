
$(document).ready(function() 
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
});


// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateInventoryForm(); 
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
 onInventorySaveComplete(response.responseText, status); 
 } 
 }); 

}); 

function onInventorySaveComplete(response, status) 
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
 $("#alertError").text("Error while saving a inventory item."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving a inventory item.."); 
 $("#alertError").show(); 
 } 
 
 $("#hidInvIDSave").val(""); 
 $("#formInventory")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
 $("#hidInvIDSave").val($(this).data("invid")); 
 $("#txtCode").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#txtName").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#numQty").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#ddlYear").val($(this).closest("tr").find('td:eq(3)').text()); 
 $("#ddlDate").val($(this).closest("tr").find('td:eq(3)').text()); 

});



// CLIENT-MODEL=================================================================
function validateInventoryForm() 
{ 
	
// CODE
if ($("#txtCode").val().trim() == "") 
 { 
 return "Enter item code."; 
 }	
		
// NAME
if ($("#txtName").val().trim() == "") 
 { 
 return "Insert item name."; 
 } 
 
 // QTY
if ($("#numQty").val() == "0") 
 { 
 return "Insert qty"; 
 }
 

// YEAR
if ($("#ddlYear").val() == "0") 
 { 
 return "Select year."; 
 } 
 
 
 // YEAR
if ($("#ddlDate").val() == "0") 
 { 
 return "Select date."; 
 } 
 
return true; 
}

window.onload = function () {
        //Reference the DropDownList.
        var ddlYear = document.getElementById("ddlYear");
 
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

 
 //DELETE Opearation--------------------------
 $(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "InventoryAPI", 
 type : "DELETE", 
 data : "invItemID=" + $(this).data("invid"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onInventoryDeleteComplete(response.responseText, status); 
 } 
 }); 
});

function onInventoryDeleteComplete(response, status) 
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
 $("#alertError").text("Error while deleting a inventory item."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}