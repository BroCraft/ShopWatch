package brocraft;

import java.util.EventListener;

import org.bukkit.event.Event;

import com.Acrobot.ChestShop.Events.TransactionEvent;

public class ShopListener implements EventListener {

	public void eventPerformed(Event e) {
		TransactionEvent transaction;
		if(e instanceof TransactionEvent){
			transaction = (TransactionEvent) e;
		} else {
			return;
		}
		String owner = transaction.getOwner();
		Double price = transaction.getPrice();
	}
}
