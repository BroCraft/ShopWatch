package brocraft;

import org.bukkit.plugin.java.JavaPlugin;

public class ShopWatch extends JavaPlugin {
	@Override
    public void onEnable(){
        // TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("ShopWatch has been enabled!");
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("ShopWatch has been disabled!");
    }
    
    public void sendToDatabase(String shopOwnerName, int transactionValue) {
    	getLogger().info("LOG: Player: " + shopOwnerName + " has received: " + transactionValue + " currency!");
    }

}
