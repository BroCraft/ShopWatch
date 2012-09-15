package listeners;

import java.util.EventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import brocraft.ShopWatch;

import com.Acrobot.ChestShop.Events.TransactionEvent;

public class LoginListener implements EventListener {
	
	ShopWatch parent;
	
	public LoginListener(ShopWatch parent){
		this.parent = parent;
	}
	
	public void eventPerformed(Event e) {
		PlayerJoinEvent joinedEvent;
		if (e instanceof PlayerJoinEvent){
			joinedEvent = (PlayerJoinEvent) e;
		} else {
			return;
		}
		Player joiner = joinedEvent.getPlayer();
		String joinerName = joiner.getName();
		parent.playerLoggedIn(joinerName);
	}
}
