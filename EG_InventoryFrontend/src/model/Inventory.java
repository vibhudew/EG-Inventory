package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inventory {
	//DB Connection
	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eginv", "root", "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Read method
	public String readInventory()
	{
		String output = "";

		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for Reading";
			}

			//html table 
			output = "<table border='1'><tr><th>Inventory Item Code</th><th>Inventory Item Name</th><th>Manufact</th>" 
					+ "<th>Qty</th><th>Repair</th><th>Handled By</th><th>Created On</th><th>Updated On</th><th>Update</th><th>Remove</th></tr>";

			output = "<div class=''><table class='table table-hover table-bordered table-striped table-bordered' style='width:100%' style='text-align:center'><thead class='thead-dark'>"
					+ "<th style='padding:10px; text-align:center;'>Inventory Item Code</th>"
					+ "<th style='padding:10px; text-align:center;'>Inventory Item Name</th>"
					+ "<th style='padding:10px; text-align:center;'>Year of Manufacturing</th>"
					+ "<th style='padding:10px; text-align:center;'>Stock Quantity</th>"
					+ "<th style='padding:10px; text-align:center;'>Latest Repair Date</th>"
					+ "<th style='padding:10px; text-align:center;'>Handled By</th>"
					+ "<th style='padding:10px; text-align:center;'>Created On</th>"
					+ "<th style='padding:10px; text-align:center;'>Updated On</th>"
					+ "<th style='padding:10px; text-align:center;'>Update</th><th>Remove</thead></tr>";
			String query = "select * from inventory";

			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(query);

			// iterate through the result set
			while (result.next())
			{
				String id = Integer.toString(result.getInt("invID"));
				String code = result.getString("invCode");
				String name = result.getString("invName");
				String manufact = result.getString("manufact");
				String qty = Integer.toString(result.getInt("stockQty"));
				String repair = result.getString("repair");
				String handle = result.getString("handledBy");
				String created = result.getString("created");
				String updated = result.getString("updated");
				

				// Add into the html table							
				output += "<tbody style='padding:10px; text-align:center;'><td ><input id='hidInvIDUpdate' name='hidInvIDUpdate' type='hidden' value='" + id + "'>" + code + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + manufact + "</td>";
				output += "<td>" + qty + "</td>";		
				output += "<td>" + repair + "</td>";	
				output += "<td>" + handle + "</td>";	
				output += "<td>" + created + "</td>";
				output += "<td>" + updated + "</td>";	
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-invid='" + id + "'></td>" + 
						"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-invid='"
						+ id + "'>" + "</td></tbody>";
			}
			con.close();

			// Closing the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error occured when reading the inventory service.";
			System.err.println(e.getMessage());
		}

		return output;
	}


	//Insert method
	public String insertInventory(String code, String name, String manufact, String qty, String repair, String handle)
	{

		String output = "";

		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for inserting.";
			}

			// Sql query
			String query = " insert into inventory (`invCode`,`invName`,`manufact`,`stockQty`,`repair`,`handledBy`)" + " values (?, ?, ?, ? ,? ,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, manufact);
			preparedStmt.setInt(4, Integer.parseInt(qty));
			preparedStmt.setString(5, repair);
			preparedStmt.setString(6, handle);
			
			// execute the preparedStatement
			preparedStmt.execute();
			
			con.close();
			
			String newInv = readInventory();			
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";
			
		}
		catch (Exception e)
		{
			
			output = "{\"status\":\"error\", \"data\":\"Error while inserting a inventory record.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	//update method
	public String updateInventory(String id, String code, String name, String manufact, String qty, String repair, String handle)
	{
		
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error occured when connecting to the DB for updating.";
			}
			
			// sql query
			String query = "UPDATE inventory SET invCode=?,invName=?,manufact=?,stockQty=?,repair=?,handledBy=? WHERE invID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, code );
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, manufact);
			preparedStmt.setInt(4, Integer.parseInt(qty));
			preparedStmt.setString(5, repair);
			preparedStmt.setString(6, handle);
			preparedStmt.setInt(7, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newInv = readInventory();
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating a inventory record.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//delete method
	public String deleteInventory(String id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error occured when connecting to the DB for deleting.";
			}
			// sql query
			String query = "delete from inventory where invID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newInv = readInventory();
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting a inventory record.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
