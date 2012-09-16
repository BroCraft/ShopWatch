package brocraft;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import listeners.*;

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

	}

	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
		DatabaseConnector.saveTransactions(swDataClass);
		getLogger().info("ShopWatch has been disabled!");
	}

	public void sendToDatabase(String shopOwnerName, double transactionValue) {
		getLogger().info(
				"LOG: Player: " + shopOwnerName + " has received: "
						+ transactionValue + " currency!");
	}

	public void playerLoggedIn(String player) {
		notifyPlayerOfTransactions(player);
	}

	private void notifyPlayerOfTransactions(String player) {
		int netTransactions = this.getTransactions(player);

		getLogger().info("LOG: Player: " + player + "has logged in!");
	}

	private void addTransaction(String playerName, int value) {
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

			if (t.isRead() == false && t.getPlayerName().equals(player)) {
				runningTotal += t.getValue();
				t.setRead(true);
			}
		}

		return runningTotal;
	}

}
