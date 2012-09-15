package listeners;

import java.util.EventListener;
import brocraft.*;
import org.bukkit.event.Event;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.Type;

public class ShopListener implements EventListener {
	ShopWatch parent;
	public ShopListener(ShopWatch parent){
		this.parent = parent;
	}

	public void eventPerformed(Event e) {
		TransactionEvent transaction;
		if(e instanceof TransactionEvent){
			transaction = (TransactionEvent) e;
		} else {
			return;
		}
		String owner = transaction.getOwner();
		Double price = transaction.getPrice();
		if(transaction.getTransactionType() == Type.SELL){
			price *= -1.0;
		}
		parent.sendToDatabase(owner, price);
	}
}
