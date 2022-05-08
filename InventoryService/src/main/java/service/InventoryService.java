package service;

import java.util.ArrayList;

import model.Inventory;

public interface InventoryService {

	String insertInventory(Inventory inv);

	ArrayList<Inventory> readInventory();

	ArrayList<Inventory> readInventory(int specificID);

	String updateInventory(int invID, Inventory inv);

	String deleteInventory(int invID);

}
