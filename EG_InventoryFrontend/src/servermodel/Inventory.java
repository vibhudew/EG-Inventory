package servermodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inventory {


	private static Connection connect() throws NullPointerException{

		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/eginv", "root", "");

			System.out.println("Successfully connected to inventory database. ");
		} catch (Exception e) {
			System.out.println("Not connected");
			e.printStackTrace();
		}
		
		return con;

	}


	public String readInventory() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed

			output = "<div class=''><table class='table table-hover table-bordered table-striped table-bordered' style='width:80%' style='text-align:center'><thead class='thead-dark'>"
					+ "<th style='padding:10px; text-align:center;'>Inventory ID</th>"
					+ "<th style='padding:10px; text-align:center;'>Inventory Item Code</th>"
					+ "<th style='padding:10px; text-align:center;'>Inventory Item Name</th>"
					+ "<th style='padding:10px; text-align:center;'>Manufacturing Year</th>"
					+ "<th style='padding:10px; text-align:center;'>Stock Quantity</th>"
					+ "<th style='padding:10px; text-align:center;'>Latest Repair Date</th>"
					+ "<th style='padding:10px; text-align:center;'>Handled By</th>"
					+ "<th style='padding:10px; text-align:center;'>Created On</th>"
					+ "<th style='padding:10px; text-align:center;'>Updated On</th>"
					+ "<th style='padding:10px; text-align:center;'>Update</th><th>Remove</th></thead>";

			String query = "SELECT * FROM inventory";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("invID"));
				String code = rs.getString("invCode");
				String name = rs.getString("invName");
				String manufact = rs.getString("manufact");
				String qty = Double.toString(rs.getDouble("stockQty"));
				String repair = rs.getString("repair");
				String handle = rs.getString("handledBy");
				String created = rs.getString("created");
				String updated = rs.getString("updated");



				// Add into the html table
				output += "<tbody><td style='padding:10px; text-align:center;'>" + id + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + code + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + name + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + manufact + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + qty + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + repair+ "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + handle + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + created + "</td>";
				output += "<td style='padding:10px; text-align:center;'>" + updated + "</td>";

				// buttons

				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-invid='" + id + "'></td>" + 
						"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-invid='"
						+ id + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the inventory item details...!";
			System.out.println(e.getMessage());
		}
		return output;
	}

	// insert
	public String insertInventory(String code, String name, String manufact,String qty,String repair,String handle){

		String output = "";

		try {

			Connection con = connect();
			if (con == null) {
				
				return "Error Inserting";
				
			}

			String query = "INSERT INTO inventory (invID , invCode, invName,manufact, stockQty,repair,handledBy,created,updated) VALUES (NULL, ?, ?, ?, ?,?,?,NULL,NULL)";

			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, manufact);
			ps.setDouble(4, Double.parseDouble(qty));
			ps.setString(5, repair);
			ps.setString(6, handle);

			ps.execute();
			con.close();

			String newInv = readInventory();
			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";



		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\": \"Error while inserting a inventory item.\"}";
			System.out.println(e.getMessage());
		}
		System.out.println("Hello NULL Insert");
		return output;
	}


	// update
	public String updateInventory(String id, String code, String name, String manufact,String qty,String repair,String handle)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for updating."; }
			// create a prepared statement
			String query = "UPDATE inventory SET invCode=?,invName=?,manufact=?,stockQty=?,repair=?,handledBy=? WHERE invID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, manufact);
			preparedStmt.setDouble(4, Double.parseDouble(qty));
			preparedStmt.setString(5, repair);
			preparedStmt.setString(6, handle);
			preparedStmt.setInt(7, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newInv = readInventory();

			output = "{\"status\":\"success\", \"data\": \"" + newInv +  "\"}";


		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\": \"Error while updating a inventory item.\"}";

			e.printStackTrace();
		}
		System.out.println("Hello NULL Update");
		return output;

	}

	// delete
	public String deleteInventory(String id) {

		String output = "";

		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for deleting.";
		}

		try {

			String query = "DELETE FROM inventory WHERE invID=?";
			PreparedStatement ps = con.prepareStatement(query);


			ps.setInt(1, Integer.parseInt(id));

			ps.execute();
			con.close();

			String newInv = readInventory();

			output = "{\"status\":\"success\", \"data\": \"" + newInv + "\"}";


		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\": \"Error while deleting a inventory item.\"}";
			System.err.println(e.getMessage());

		}

		return output;

	}








}
