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
		File dbFile = new File(DatabaseConnector.SAVE_FILE_DIR + "\\" + DatabaseConnector.SAVE_FILE_NAME);
		
		swDataClass = new SWDataClass();
		// Check if database file exists
		if (!dbFile.exists()) {
			// No file, so set one up
			getLogger().info("No database file found! Creating a new file.");
			try {
				// Create the directories for ShopWatch
				File dirs = new File(DatabaseConnector.SAVE_FILE_DIR);
				dirs.mkdirs();
				
				// Create the save file
				dirs.createNewFile();
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
		
		// 
		ShopListener SL = new ShopListener(this);
		LoginListener LL = new LoginListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(SL, this);
		pm.registerEvents(LL, this);
		
		// We made it! Output a successful load.
		getLogger().info("ShopWatch has been enabled!");

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

	public void playerLoggedIn(String player) {
		notifyPlayerOfTransactions(player);
	}

	private void notifyPlayerOfTransactions(String player) {
		int netTransactions = this.getTransactions(player);
		getLogger().info("LOG: Player: " + player + "has logged in!");
		Player p = Bukkit.getPlayer(player);
		p.sendMessage("Welcome Back " + player + "!/nYou have made " + netTransactions +" dollars since you last logged off!");
	}

	private void addTransaction(String playerName, double value) {
		Transaction t = new Transaction(playerName, value);
		swDataClass.getTransactions().add(t);
		DatabaseConnector.saveTransactions(swDataClass);
	}

	private int getTransactions(String player) {
		int runningTotal = 0;
		List<Transaction> transactions = swDataClass.getTransactions();
		Iterator<Transaction> transactionIterator = transactions.iterator();

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
