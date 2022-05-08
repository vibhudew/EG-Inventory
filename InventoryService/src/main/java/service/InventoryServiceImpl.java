package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;

import model.Inventory;

public class InventoryServiceImpl implements InventoryService {

	//parameters for DB connection
	private static final String USERNAME = "root";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/electrogriddb";
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
	@Override
	public String insertInventory(Inventory inv) {
		try {
			con  = connect();

			if (con == null ) {
				output = "Error while connecting to the database";
				return output;
			}

			//Query
			query = "INSERT INTO `inventory` (`invID`, `invItemCode`, `invItemName`,`stockQty`,"
					+ "`manufactYr`, `latestRepairDate`,`handledBy`,`createdTime`,`updatedTime`)"
					+ " VALUES (NULL, ?, ?, ?, ?, ?, ?, NULL, NULL)";

			preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, inv.getInvItemCode());
			preparedStmt.setString(2, inv.getInvItemName());
			preparedStmt.setInt(3, inv.getStockQty());
			preparedStmt.setString(4, inv.getManufactYr());
			preparedStmt.setString(5, inv.getLatestRepairDate());
			preparedStmt.setString(6, inv.getHandledBy());	
			preparedStmt.execute();

			con.close();

			output = "Inserted Successfully";
			query = "";

		} catch (Exception e) {
			output = "Error while inserting a inventory item";
			System.err.println(e.getMessage());
		}

		return output;
	}


	//View all inventory items
	@Override
	public ArrayList<Inventory> readInventory() {
		//inventory attributes
		int invID = 0;
		String code = "";
		String name = "" ;
		int qty = 0;
		String manufact = "";
		String repair = "";
		String handle ="";
		String created = "";
		String updated = "";

		//Inventory List
		ArrayList<Inventory> invList = new ArrayList<Inventory>();

		//Connection
		try {
			con = connect();

			if(con == null) {
				System.err.println("Error while connecting to the database");
				return null;
			}

			//Query
			query = "SELECT * FROM inventory ";

			//Execute
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//Get all results
			while(rs.next()) {
				invID = rs.getInt("invID");
				code = rs.getString("invItemCode");
				name = rs.getString("invItemName");
				qty = rs.getInt("stockQty");
				manufact = rs.getString("manufactYr");
				repair = rs.getString("latestRepairDate");
				handle = rs.getString("handledBy");
				created = rs.getString("createdTime");
				updated = rs.getString("updatedTime");

				//Add to list
				invList.add(new Inventory(invID,code,name,qty,manufact,repair,handle,created,updated));

			}
			con.close();


		}catch(Exception e) {
			System.err.println("Error getting data " + e.getMessage());
		}
		return invList;
	}

	//View a inventory item
	@Override
	public ArrayList<Inventory> readInventory(int specificID) {
		//inventory attributes
		int invID = 0;
		String code = "";
		String name = "" ;
		int qty = 0;
		String manufact = "";
		String repair = "";
		String handle ="";
		String created = "";
		String updated = "";

		//Inventory List
		ArrayList<Inventory> invList = new ArrayList<Inventory>();

		//Connection
		try {
			con = connect();

			if(con == null) {
				System.err.println("Error while connecting to the database");
				return null;
			}

			//Query
			query = "SELECT * FROM inventory WHERE invID = " + specificID;

			//Execute
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			//Get all results
			while(rs.next()) {
				invID = rs.getInt("invID");
				code = rs.getString("invItemCode");
				name = rs.getString("invItemName");
				qty = rs.getInt("stockQty");
				manufact = rs.getString("manufactYr");
				repair = rs.getString("latestRepairDate");
				handle = rs.getString("handledBy");
				created = rs.getString("createdTime");
				updated = rs.getString("updatedTime");

				//Add to list
				invList.add(new Inventory(invID,code,name,qty,manufact,repair,handle,created,updated));

			}
			con.close();


		}catch(Exception e) {
			System.err.println("Error getting data " + e.getMessage());
		}
		return invList;
	}


	//Update a inventory item
	@Override
	public String updateInventory(int invID,Inventory inv) {
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

			preparedStmt.setString(1, inv.getInvItemCode());
			preparedStmt.setString(2, inv.getInvItemName());
			preparedStmt.setInt(3, inv.getStockQty());
			preparedStmt.setString(4, inv.getManufactYr());
			preparedStmt.setString(5, inv.getLatestRepairDate());
			preparedStmt.setString(6, inv.getHandledBy());	
			preparedStmt.executeUpdate();

			con.close();

			output = "Updated Successfully";
			query = "";

		} catch (Exception e) {
			output = "Error while updating a inventory item";
			System.err.println(e.getMessage());
		}

		return output;
	}

	//Delete a record
	@Override
	public String deleteInventory(int invID) {
		try {
			con = connect();

			if(con == null) {
				output = "Error while connecting to the database";
				return output;
			}

			//Query
			query = "DELETE FROM inventory WHERE invID = " + invID;
			stmt = con.createStatement();
			int result = stmt.executeUpdate(query);

			if(result > 0) {
				output = "Deleted successfully";
			}
			else {
				output = "Inventory Item not found";
			}

		}catch(Exception e) {
			output = "Error deleting data " + e.getMessage();
			System.err.println(output);
			return output;
		}
		return output;
	}
}
