package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import brocraft.ShopWatch;


public class LoginListener implements Listener {
	
	ShopWatch parent;
	
	public LoginListener(ShopWatch parent){
		this.parent = parent;
	}
	
	@EventHandler
	public void eventPerformed(PlayerJoinEvent e) {
		Player joiner = e.getPlayer();
		String joinerName = joiner.getName();
		parent.playerLoggedIn(joinerName);
	}
}
