package com.santhosh.inventory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryClient {

	public static void main(String[] args) {

		Inventory inventory = new InventoryImpl();

		inventory.createInventory("inventory.txt");
		inventory.generateReport();

		ExecutorService executorService = Executors.newFixedThreadPool(3);

		executorService.execute(new Runnable() {
			int counter = 0;
			public void run() {
				for (int i = 1; i <= 20 && counter <=20 ; i++) {
					inventory.checkOutItems(i, 50);
					if (i > 5)
						i = 1;
					counter++;
				}
			}
		});

		executorService.execute(new Runnable() {
			public void run() {
				inventory.updateStocks(2, 20);
			}
		});

		inventory.generateReport();
		executorService.shutdown();
	}
}
