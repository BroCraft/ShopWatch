package brocraft;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import data.DatabaseConnector;
import data.ItemReport;
import data.Merchant;
import data.SWDataClass;
import data.Transaction;

/**
 * Main driver class and controller for the ShopWatch plugin. Contains the logic
 * for interacting with the model (database) and views (player window and server
 * console)
 * 
 * @author SudoCheese
 * @author Ampt
 * 
 */
public class ShopWatch extends JavaPlugin {

	/**
	 * Used to store the transactions data structure in memory for quick access
	 */
	private SWDataClass swDataClass;

	/**
	 * This methods takes care of initializing the database and listeners.
	 */
	@Override
	public void onEnable() {
		File dbFile = new File(DatabaseConnector.SAVE_FILE_DIRECTORY + "\\"
				+ DatabaseConnector.SAVE_FILE_NAME);

		swDataClass = new SWDataClass();
		// Check if database file exists
		if (!dbFile.exists()) {
			// No file, so set one up
			getLogger().info("No database file found! Creating a new file.");
			try {
				// Create the directories for ShopWatch
				File directories = new File(
						DatabaseConnector.SAVE_FILE_DIRECTORY);
				directories.mkdirs();

				// Create the save file
				directories.createNewFile();
				dbFile.createNewFile();

				// Notify the logger
				getLogger().info("File created successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			DatabaseConnector.saveTransactions(swDataClass);
		} else {
			DatabaseConnector.loadTransactions(swDataClass);
		}

		// Set up listeners
		ShopListener shopListener = new ShopListener(this);
		LoginListener loginListener = new LoginListener(this);
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(shopListener, this);
		pluginManager.registerEvents(loginListener, this);

		// We made it! Output a happy message.
		getLogger().info("ShopWatch has been enabled!");
	}

	/**
	 * Saves the database to disk (again) for good luck in the event that we are
	 * reloading or something fancy
	 */
	@Override
	public void onDisable() {
		DatabaseConnector.saveTransactions(swDataClass);
		getLogger().info("ShopWatch has been disabled!");
	}

	/**
	 * Saves a transaction to the database
	 * @param shopOwnerName - The owner of the shop
	 * @param transactionValue - The value of the transaction
	 */
	public void sendToDatabase(String shopOwnerName, String itemName, int quantity, double transactionValue) {
		addTransaction(shopOwnerName.toLowerCase(), itemName, quantity, transactionValue);
	}

	/**
	 * This method will perform all the necessary operations that occur when a
	 * player logs in For now this is 1 - Net currency made/lost while offline
	 * ~2. Which shops went empty while offline (future)
	 * 
	 * @param player - Name of the player that just logged in
	 */
	public void playerLoggedIn(String player) {
		notifyPlayerOfTransactions(player.toLowerCase());
	}

	/**
	 * Notifies the player of how much money they made/lost while they were
	 * offline
	 * 
	 * @param playerName
	 */
	private void notifyPlayerOfTransactions(String playerName) {
		// get the player
		Player player = Bukkit.getPlayer(playerName);
		// Check to see if there a merchant file for the player
		System.out.println("You are: " + playerName + " Merchants: " + swDataClass.getMerchants().keySet().toString());
		if (swDataClass.getMerchants().keySet().contains(playerName)) {
			System.out.println("Transactions: " + swDataClass.getMerchants().get(playerName).getTransactions().size());
			// Check if there are new transactions
			if (swDataClass.getMerchants().get(playerName).getTransactions().size() != 0) {
				// Gather the item reports for output
				HashMap<String, ItemReport> itemReports = new HashMap<String, ItemReport>();
								
				Iterator<Transaction> transactionIterator = swDataClass.getMerchants().get(playerName).getTransactions().iterator();
				while (transactionIterator.hasNext()) {
					Transaction nextTransaction = transactionIterator.next();
					String itemName = nextTransaction.getItem();
					if (itemReports.keySet().contains(itemName)) {
						itemReports.get(itemName).addTransaction(nextTransaction);
					} else {
						itemReports.put(itemName, new ItemReport(nextTransaction));
					}
					transactionIterator.remove();
				}
				
				// First, calculate how much the player made since he logged off last
				double netTransactions = 0;
				Iterator<String> itemNameIterator = itemReports.keySet().iterator();
				
				player.sendMessage("Hello, " + playerName + "! While you were gone your shops were busy!");
				
				while (itemNameIterator.hasNext()) {
					String itemName = itemNameIterator.next();
					ItemReport itemReport = itemReports.get(itemName);
					// Output the item report
					player.sendMessage(itemName + " : " + itemReport.getNetTransaction() + " (" + itemReport.getTransactionCount() + " units)");
					netTransactions += itemReport.getNetTransaction();
				}
				
				player.sendMessage("Your total net transactions were: " + netTransactions + " dollars.");
				
			} else {
				// No new transactions
				player.sendMessage("No shop activity to report. Happy Crafting!");
			}
		} else {
			// Create the merchant file for the player
			swDataClass.getMerchants().put(playerName, new Merchant());
			getLogger().info("Added " + playerName + " merchant");
			// Welcome the player to the new shop mod?
			player.sendMessage("Your shops in BroCraft just got an upgrade! Whenever you log in you will see a report from your shop from when you were offline!");
			
		}
	}

	/**
	 * Adds a transaction to the database
	 * 
	 * @param playerName - name of the player who owns the shop
	 * @param value - value of the transaction that is being added
	 */
	private void addTransaction(String playerName, String itemName, int quanitity, double value) {
		
		if (!playerName.equalsIgnoreCase("Shop")) {
			// create a new transaction
			Transaction t = new Transaction(playerName, itemName, quanitity, value);

			// add the transaction to the dataClass
			// check if there is a key for that player
			if (swDataClass.getMerchants().keySet().contains(playerName)) {
				swDataClass.getMerchants().get(playerName).getTransactions().add(t);
			} else {
				// Player is not yet in the database, add them
				Merchant merchant = new Merchant();
				merchant.getTransactions().add(t);
				swDataClass.getMerchants().put(playerName, merchant);
			}
			

			DatabaseConnector.saveTransactions(swDataClass);
		}

	}

//	/**
//	 * Totals up all of the transactions that have occurred since the players
//	 * last login
//	 * 
//	 * @param player
//	 * @return the total value of all transactions that occurred while the player
//	 *         was logged off
//	 */
//	private HashMap<String, ItemReport> getItemReports(String player) {
//		boolean removedRecords = false;
//		HashMap<String, ItemReport> itemReports = new HashMap<String, ItemReport>();
//		
//		// Check to see if we a merchant object for this player
//		if (swDataClass.getMerchants().keySet().contains(player)) {
//			
//			Iterator<Transaction> transactionIterator = swDataClass.getMerchants().get(player).getTransactions().iterator();
//			while (transactionIterator.hasNext()) {
////				runningTotal += transactionIterator.next().getValue();
//				transactionIterator.remove();
//				removedRecords = true;
//			}
//		}
//
//		// If we removed any records from the database, we need to save it again
//		if (removedRecords) {
//			DatabaseConnector.saveTransactions(swDataClass);
//		}
//		return itemReports;
//	}

}
