package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import brocraft.ShopWatch;

public class LogoutListener implements Listener {
ShopWatch parent;
	
	public LogoutListener(ShopWatch parent){
		this.parent = parent;
	}
	
	@EventHandler
	public void eventPerformed(PlayerQuitEvent e) {
		
	}
}
