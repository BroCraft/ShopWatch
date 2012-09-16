package brocraft;

import listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopWatch extends JavaPlugin {
	@Override
    public void onEnable(){
        ShopListener SL = new ShopListener(this);
        LoginListener LL = new LoginListener(this);
		getLogger().info("ShopWatch has been enabled!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(SL, this);
		pm.registerEvents(LL, this);
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("ShopWatch has been disabled!");
    }
    
    public void sendToDatabase(String shopOwnerName, double transactionValue) {
    	getLogger().info("LOG: Player: " + shopOwnerName + " has received: " + transactionValue + " currency!");
    }
    
    public void playerLoggedIn(String player){
    	notifyPlayerOfTransactions(player);
    }
    
    private void notifyPlayerOfTransactions(String player){
    	getLogger().info("LOG: Player: " + player + " has logged in!");
    	Bukkit.getPlayer(player).sendMessage("Hello " + player + "! Thanks for Logging in!");
    }

}
