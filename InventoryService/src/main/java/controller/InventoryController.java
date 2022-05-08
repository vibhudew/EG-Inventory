package controller;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.google.gson.Gson;
import model.Inventory;
import service.InventoryService;
import service.InventoryServiceImpl;

@Path("/Inventory")
public class InventoryController {

	InventoryService invService = new InventoryServiceImpl();

	ArrayList<Inventory> invObjs = new ArrayList<>();

	//Insert 
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createInventory(@FormParam("invItemCode") String code,@FormParam("invItemName") String name,
			@FormParam("stockQty") int qty, @FormParam("manufactYr") String manufact, @FormParam("latestRepairDate") String repair,
			@FormParam("handledBy") String handle){



		String output = "";


		//Validations 
		if(!code.startsWith("INV")) {
			output += "Error : invItemCode must be started with INV.";
		}

		if(name.length() < 5) {
			output = "Inventory Item Name contains at least 3 characters.";
		}

		if(name.isEmpty() || code.isEmpty() || manufact.isEmpty() || repair.isEmpty()) {
			output = "All given input fields must be inserted.";
		}

		if (output == "") {
			output = invService.insertInventory(new Inventory(code,name,qty,manufact,repair,handle));	
		}


		return output;
	}


	//View a specific record
	@GET
	@Path("/{invID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewSpecificInventory(@PathParam("invID") int invID) {
		Gson gson = new Gson();

		invObjs = invService.readInventory(invID);
		String jsonString  = gson.toJson(invObjs);

		return jsonString;
	}

	//View all records
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewAllInventory() {

		Gson gson = new Gson();

		invObjs = invService.readInventory();

		String jsonString  = gson.toJson(invObjs);

		return jsonString;
	}

	//Update
	@PUT
	@Path("/{invID}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String editInventory(@PathParam("invID") int invID , 
			@FormParam("invItemCode") String code,
			@FormParam("invItemName") String name,
			@FormParam("stockQty") int qty,
			@FormParam("manufactYr") String manufact,
			@FormParam("latestRepairDate") String repair,@FormParam("handledBy") String handle) {


		String output = "";


		//Validations 
		if(!code.startsWith("INV")) {
			output += "Error : invItemCode must be started with INV.";
		}

		if(name.length() < 5) {
			output = "Inventory Item Name contains at least 3 characters.";
		}

		if(name.isEmpty() || code.isEmpty() || manufact.isEmpty() || repair.isEmpty()) {
			output = "All given input fields must be inserted.";
		}

		if (output == "") {
			output = invService.updateInventory(invID, new Inventory(code,name,qty,manufact,repair,handle));	
		}

		return output;
	}

	//Delete
	@DELETE
	@Path("/{invID}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeInventory(@PathParam("invID") int invID) {

		String response = invService.deleteInventory(invID);
		return response;
	}


}
