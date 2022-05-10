package servermodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inventory {
	
	//Connect to DB
	public Connection connect()
	{
	 Connection con = null;
	 
	 try
	 {
			 
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root","");
			 
			 //For testing
			 System.out.print("Successfully Connected to Inventory Database");
	 }
	 catch(Exception e)
	 {
		 System.out.println("Not connected to DB");
		 e.printStackTrace();
	 }

	 return con;
	}
	
	//Insert a inventory item
	public String insertInventory(int invID,String code, String name, int qty, String manufact,String repair){ 

		String output = ""; 

		try
		{ 

			Connection con = connect(); 

			if (con == null) 

			{
				return "Error while connecting to the database for inserting."; 
			} 
			
			System.out.print("Testing");

			// create a prepared statement
			String query = " INSERT INTO inventory (`invID`,`invItemCode`, `invItemName`,`stockQty`,`manufactYr`, `latestRepairDate`)"
						+ " values (?, ?, ?, ?, ?)"; 

			PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			// binding values
			preparedStmt.setInt(1, invID); 
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setInt(4, qty);
			preparedStmt.setString(5, manufact);
			preparedStmt.setString(6, repair);	
			preparedStmt.execute();

			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 String newInv = readInventory(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			 
			output = "{\"status\":\"error\", \"data\":\"Error while inserting a inventory item.\"}"; 
			 System.err.println(e.getMessage()); 
			 
		} 
		return output; 
	}

	//View all inventory items
	public String readInventory() {
		
		String output = ""; 
		 
		try
		{ 
				Connection con = connect(); 
				if (con == null) 
				{ 
					return "Error while connecting to the database for reading."; 
				} 
	 
				// Prepare the html table to be displayed
				output = "<table width='800px' border='1'><tr><th>Item Code</th><th>Item Name</th>"
						+ "<th>Stock Quantity</th><th>Manufacturing Year</th><th>LatestRepairDate</th>"
				 		+ "<th>Update</th><th>Remove</th></tr>"; 
	 
				String query = "SELECT * FROM inventory"; 
				Statement stmt = con.createStatement(); 
				ResultSet rs = stmt.executeQuery(query); 
	 
				// iterate through the rows in the result set
				while (rs.next()) 
				{ 
					String invID = Integer.toString(rs.getInt("invID"));
					String code = rs.getString("invItemCode");
					String name = rs.getString("invItemName");
					String qty = Integer.toString(rs.getInt("stockQty"));           
					String manufact = rs.getString("manufactYr");
					String repair = rs.getString("latestRepairDate");
	 
				// Add into the html table
					output += "<tr><td><input id='hidInvIDUpdate' name='hidInvIDUpdate' type='hidden' value='" + invID
							 + "'>" + code + "</td>";  
					output += "<td>" + name + "</td>"; 
					output += "<td>" + qty + "</td>"; 
					output += "<td>" + manufact + "</td>";
					output += "<td>" + repair + "</td>"; 
				
				// buttons
					output += "<td><input name='btnUpdate'"
							+ "type='button' value='Update' "
							+ "class='btnUpdate btn btn-secondary' data-invid='" + invID + "'></td>"
							+ "<td><input name='btnRemove' "
							+ "type='button' value='Remove'"
							+ "class='btnRemove btn btn-danger'"
							+ "data-invid='"
							+ invID + "'>" + "</td></tr>"; 
				} 
	 
				con.close(); 
	 
				// Complete the html table
				output += "</table>"; 
	}
		 catch (Exception e) 
		 { 
			 	output = "Error while reading the inventory."; 
			 	System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}
	
	//Update a inventory item
	public String updateInventory(String invID,String code, String name, String qty, String manufact, String repair)
	{ 
		 String output = ""; 
		 
		 try
		 { 
		 
			 	Connection con = connect(); 
		 
			 	if (con == null) 
			 	{return "Error while connecting to the database for updating."; } 
		 
			 	// create a prepared statement
			 	String query = "UPDATE inventory SET invItemCode = ? , invItemName =?,stockQty=?,manufactYr=? ,latestRepairDate=? WHERE invID =?"; 
		 
			 	PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
			 	// binding values
			 	preparedStmt.setString(1, code);
				preparedStmt.setString(2, name);
				preparedStmt.setInt(3, Integer.parseInt(qty));
				preparedStmt.setString(4, manufact);
				preparedStmt.setString(5, repair);
			 	preparedStmt.setInt(6, Integer.parseInt(invID)); 
		 
			 	// execute the statement
			 	preparedStmt.execute(); 
			 	con.close(); 
		 
			 	String newInv = readInventory(); 
			 	output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\":\"Error while updating a inventory item.\"}"; 
			 System.err.println(e.getMessage());  
		 } 
		 return output; 
	}
	

	public String deleteInventory(String invID)
	{ 
	 	String output = ""; 
	 
	 	try
	 	{ 
	 
	 			Connection con = connect(); 
	 
	 			if (con == null) 
	 			{return "Error while connecting to the database for deleting."; } 
	 
	 			// create a prepared statement
	 			String query = "DELETE FROM inventory WHERE invID =?"; 
	 			
	 			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 			// binding values
	 			preparedStmt.setInt(1, Integer.parseInt(invID)); 
	 
	 			// execute the statement
	 			preparedStmt.execute(); 
	 			con.close(); 
	 
	 			String newInv = readInventory();
				output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";  
	 	} 
	 	catch (Exception e) 
	 	{ 
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting a inventory item.\"}"; 
		 System.err.println(e.getMessage()); 
	 	} 
	 		return output; 
	}
	
	
	
	
	

}
