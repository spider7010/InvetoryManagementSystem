package com.santhosh.inventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryImpl implements Inventory {

	private Map<Item, Integer> inventoryMap = new ConcurrentHashMap<>();

	private Map<Integer, Stock> itemsMap = new ConcurrentHashMap<>();


	@Override
	public void createInventory(String fileName) {

		try(BufferedReader br = new BufferedReader(new FileReader(new File("inventory.txt")))) {
			System.out.println("Creating inventory");
			String line = null;
			while ((line = br.readLine()) != null) {

				String itemsArr[] = line.split(" ");
				int id = Integer.valueOf(itemsArr[0]);
				String name = itemsArr[1];
				double cost = Double.valueOf(itemsArr[2]);
				int quantity = Integer.valueOf(itemsArr[3]);

				Item item = new Item(id, name, cost);

				Stock stock = new Stock(item, quantity);

				inventoryMap.put(item, quantity);

				itemsMap.put(id, stock);

			}
			br.close();
		} catch (Exception e) {
			System.err.println("Caught exception while creting inventory");
		}
	}

	@Override
	public boolean checkOutItems(int id, int quantity) {

		Stock stock = itemsMap.get(id);

		if (stock == null) {
			System.err.println("Sorry..! No item availble with this id " + id);
			return false;
		}
		if (inventoryMap.get(stock.getItem()) < quantity) {
			System.err.println("Stock not available with id " + id);
			return false;
		}
		stock.setQuantity(stock.getQuantity() - quantity);

		inventoryMap.put(stock.getItem(), (stock.getQuantity()));

		itemsMap.put(id, stock);

		return true;
	}

	@Override
	public boolean updateStocks(int id, int quantity) {

		Stock stock = itemsMap.get(id);

		if (stock == null) {
			System.err.println("Sorry..! No item availble with this id " + id);
			return false;
		}
		if (inventoryMap.get(stock.getItem()) < quantity) {
			System.err.println("Stock not available with id " + id);
			return false;
		}
		stock.setQuantity(stock.getQuantity() + quantity);

		inventoryMap.put(stock.getItem(), (stock.getQuantity()));

		itemsMap.put(id, stock);

		return true;
	}

	@Override
	public void generateReport() {

		System.out.println("==================Generating Report=================");
		
		for (Map.Entry<Item, Integer> entry : inventoryMap.entrySet()) {
			
			Item item = entry.getKey();
			int quantity = entry.getValue();
			
			System.out.print(item.getId()+" ");
			System.out.print(item.getName()+" ");
			System.out.print(item.getCost()+" ");
			System.out.print(quantity+" ");
			System.out.println();
		}
	}
}
