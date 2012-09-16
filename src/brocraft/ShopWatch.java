package brocraft;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import data.DatabaseConnector;
import data.SWDataClass;
import data.Transaction;

public class ShopWatch extends JavaPlugin {

	private SWDataClass swDataClass;

	@Override
	public void onEnable() {
		File dbFile = new File(DatabaseConnector.SAVE_FILE_NAME);
		if (!dbFile.exists()) {
			getLogger().info("No database file found! Creating a new file.");
			try {
				dbFile.createNewFile();
				getLogger().info("File created successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			swDataClass = new SWDataClass();
		} else {
			DatabaseConnector.loadTransactions(swDataClass);
		}
		ShopListener SL = new ShopListener(this);
		LoginListener LL = new LoginListener(this);
		getLogger().info("ShopWatch has been enabled!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(SL, this);
		pm.registerEvents(LL, this);

	}

	@Override
	public void onDisable(){
		// TODO Insert logic to be performed when the plugin is disabled
		DatabaseConnector.saveTransactions(swDataClass);
		getLogger().info("ShopWatch has been disabled!");
	}

	public void sendToDatabase(String shopOwnerName, double transactionValue) {
		addTransaction(shopOwnerName, transactionValue);
	}
	
	/**
	 * This method will perform all the necessary operations that occur when a player logs in
	 * For now this is
	 * 1.How Much money he/she made/lost while they were offline
	 * ~2. Which shops are empty (Future)
	 * @param player
	 */
	public void playerLoggedIn(String player) {
		notifyPlayerOfTransactions(player);
	}

	
	/**
	 * Notifys the player of how much money they made/lost while they were offline
	 * @param player
	 */
	private void notifyPlayerOfTransactions(String player) {
		//First, calculate how much the player made since he logged off last
		double netTransactions = this.getTransactions(player);
		//get the player
		Player p = Bukkit.getPlayer(player);
		//tell them how much they made
		p.sendMessage("Welcome Back " + player + "!/nYou have made " + netTransactions +" dollars since you last logged off!");
	}

	/**
	 * Adds a transaction to the database
	 * @param playerName the name of the player who owns the shop
	 * @param value the value of the transaction that is being added
	 */
	private void addTransaction(String playerName, double value) {
		//create a new transaction
		Transaction t = new Transaction(playerName, value);
		//add the transaction to the dataClass
		swDataClass.getTransactions().add(t);
		//make sure the database saves the fucking transactions
		DatabaseConnector.saveTransactions(swDataClass);
	}

	/**
	 * Totals up all of the transactions that have occured since the players last login
	 * @param player
	 * @return the total value of all transactions that occured while the player was logged off
	 */
	private double getTransactions(String player) {
		//create a running total
		double runningTotal = 0;
		//get all the transactions
		List<Transaction> transactions = swDataClass.getTransactions();
		//create an iterator for the transactons
		Iterator<Transaction> transactionIterator = transactions.iterator();
		//for each transaction in the list, check the owner. if it matches the player
		//that just logged in, add it to the running total and delete it from the list
		while (transactionIterator.hasNext()) {
			Transaction t = transactionIterator.next();
			if (t.getPlayerName().equals(player)) {
				runningTotal += t.getValue();
				swDataClass.getTransactions().remove(t);
			}
		}
		return runningTotal;
	}

}
