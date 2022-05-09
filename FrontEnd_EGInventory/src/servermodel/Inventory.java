package servermodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Inventory {

	//parameters for DB connection
	private static final String USERNAME = "root";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/electrogrid";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String PASSWORD = "";
	private static Connection con = null;
	private static String query = "";
	private static PreparedStatement preparedStmt = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	String output = "";

	//Connect to DB
	private Connection connect() throws SQLException {

		if (con != null && !con.isClosed()) {
			return con;
		}
		else {
			try {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(URL,USERNAME,PASSWORD);

				System.out.println("Successfully Connected to Inventory Database");

			} catch (Exception e) {
				e.printStackTrace();
			}

			return con;
		}
	}

	//Insert a inventory item
	public String insertInventory(String code,String name,String qty,String manufact,String repair) {
			
		try {
			con  = connect();

			if (con == null ) {
				output = "Error while connecting to the database";
				return output;
			}

			//Query
			query = "INSERT INTO `inventory` (`invID`, `invItemCode`, `invItemName`,`stockQty`,"
					+ "`manufactYr`, `latestRepairDate`)"
					+ " VALUES (NULL, ?, ?, ?, ?, ?)";

			preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, Integer.parseInt(qty));
			preparedStmt.setString(4, manufact);
			preparedStmt.setString(5, repair);	
			preparedStmt.execute();

			con.close();

			String newInv = readInventory(); 
			
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting a inventory item.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;
	}


	//View all inventory items
	public String readInventory() {
		
		//Connection
		try {
			con = connect();

			if(con == null) {
				System.err.println("Error while connecting to the database");
				return null;
			}
			
			 // Prepare the html table to be displayed
			 output = "<table border=\"1\"><tr><th>Item Code</th><th>Item Name</th>"
			 		+ "<th>Stock Quantity</th><th>Manufacturing Year</th><th>LatestRepairDate</th>"
			 		+ "<th>Update</th><th>Remove</th></tr>"; 

			//Query
			query = "SELECT * FROM inventory ";

			//Execute
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//Get all results
			while(rs.next()) {
				String invID = Integer.toString(rs.getInt("invID"));
				String code = rs.getString("invItemCode");
				String name = rs.getString("invItemName");
				String qty = Integer.toString(rs.getInt("stockQty"));           
				String manufact = rs.getString("manufactYr");
				String repair = rs.getString("latestRepairDate");

				// Add into the html table
				 output += "<tr><td>"+ code + "</td>"; 
				 output += "<td>" + name + "</td>"; 
				 output += "<td>" + qty + "</td>"; 
				 output += "<td>" + manufact + "</td>"; 
				 output += "<td>" + repair + "</td>"; 
				
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-invid='" + invID +"'></td>"
				 		+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger' data-invid='\" + invID +\"'>"
				 		+ "</td></tr>";

			}
			con.close();
			
			 // Complete the html table
			 output += "</table>"; 


		}catch(Exception e) {
			System.err.println("Error getting data " + e.getMessage());
		}
		
		return output;
	}

	//View a inventory item
	public String readInventory(int specificID) {
		
		//Connection
		try {
			con = connect();

			if(con == null) {
				System.err.println("Error while connecting to the database");
				return null;
			}
			
			 // Prepare the html table to be displayed
			 output = "<table border=\"1\"><tr><th>Item Code</th><th>Item Name</th>"
			 		+ "<th>Stock Quantity</th><th>Manufacturing Year</th><th>LatestRepairDate</th>"
			 		+ "<th>Update</th><th>Remove</th></tr>"; 

			//Query
			query = "SELECT * FROM inventory WHERE invID = " + specificID;

			//Execute
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//Get all results
			while(rs.next()) {
				String code = rs.getString("invItemCode");
				String name = rs.getString("invItemName");
				String qty = Integer.toString(rs.getInt("stockQty"));           
				String manufact = rs.getString("manufactYr");
				String repair = rs.getString("latestRepairDate");

				// Add into the html table
				 output += "<tr><td>" + code + "</td>"; 
				 output += "<td>" + name + "</td>"; 
				 output += "<td>" + qty + "</td>"; 
				 output += "<td>" + manufact + "</td>"; 
				 output += "<td>" + repair + "</td>"; 
				
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-invid='\" + invID +\"'></td>"
				 		+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger' data-invid='\" + invID +\"'>"
				 		+ "</td></tr>";
				

			}
			con.close();
			
			// Complete the html table
			 output += "</table>"; 


		}catch(Exception e) {
			System.err.println("Error getting data " + e.getMessage());
		}
		return output;
	}


	//Update a inventory item
	public String updateInventory(String invID,String code, String name, String qty, String manufact, String repair) {
		try {
			con  = connect();

			if (con == null ) {
				output = "Error while connectiong to the database";
				return output;
			}

			//Query
			query = "UPDATE inventory SET invItemCode = ? , invItemName =?,stockQty=?,manufactYr=?,"
					+ "latestRepairDate=?,handledBy=? WHERE invID =" +invID;


			preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, Integer.parseInt(qty));
			preparedStmt.setString(4, manufact);
			preparedStmt.setString(5, repair);
			
			preparedStmt.executeUpdate();

			con.close();

			String newInv = readInventory(); 
			
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating a inventory item.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;
	}

	//Delete a record
	public String deleteInventory(String invID) {
		try {
			con = connect();

			if(con == null) {
				output = "Error while connecting to the database";
				return output;
			}

			//Query
			query = "DELETE FROM inventory WHERE invID = " + invID;
			
			preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(invID));
			preparedStmt.execute(); 
			con.close(); 

			String newInv = readInventory(); 
			
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";


		}catch(Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting a inventory item.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}
}
