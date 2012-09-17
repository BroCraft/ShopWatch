package listeners;

import brocraft.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.Type;

public class ShopListener implements Listener {

	ShopWatch parent;

	public ShopListener(ShopWatch parent) {
		this.parent = parent;
	}

	@EventHandler
	public void eventPerformed(TransactionEvent e) {
		String playerName = e.getOwner();
		
//		e.getItem();
		
		// Get all of the online players
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		boolean isOnline = false;
		for (int i = 0; !isOnline && i < onlinePlayers.length; i++) {
			// Check to see if the owner of the shop is in the list of online players
			if (onlinePlayers[i].getName().equalsIgnoreCase(playerName)) {
				isOnline = true;
			}
		}
		
		// Only store the transaction if the shop owner is offline
		if (!isOnline) {
			Double transactionValue = e.getPrice();
			if (e.getTransactionType() == Type.SELL) {
				transactionValue *= -1.0;
			}
			parent.sendToDatabase(playerName, transactionValue);
		}
	}
}
