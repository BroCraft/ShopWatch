package listeners;

import java.util.EventListener;
import brocraft.*;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.Type;

public class ShopListener implements Listener {
	
	ShopWatch parent;
	
	public ShopListener(ShopWatch parent){
		this.parent = parent;
	}

	@EventHandler
	public void eventPerformed(TransactionEvent e) {
		String owner = e.getOwner();
		if(Bukkit.getPlayer(owner).isOnline()){
			return;
		}
		Double price = e.getPrice();
		if(e.getTransactionType() == Type.SELL){
			price *= -1.0;
		}
		parent.sendToDatabase(owner, price);
	}
}
