package com.santhosh.inventory;

public interface Inventory {

	
	public void createInventory(String fileName);
	
	public boolean checkOutItems(int id, int quantity);
	
	public boolean updateStocks(int id, int quantity);
	
	public void generateReport();
}
