package data;

import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop implements Serializable{

	private Player owner;
	private ItemStack item;
	private boolean hasChest;
	private Chest chest;
	private int inventory;
	private boolean sells;
	private double sellPrice;
	private boolean buys;
	private double buyPrice;
	private static int MAX_ALLOWED_TRANSACTION_SIZE = 64;
	
	/**
	 * Creates a chest that has a physical chest linked to it
	 * @param item
	 * @param chest
	 */
	private Shop(ItemStack item, Chest chest, double sellPrice, double buyPrice){
		this.item = item;
		this.chest = chest;
		this.hasChest = true;
		if(sellPrice > 0){
			sells = true;
			this.sellPrice = sellPrice;
		} else {
			sells = false;
			this.sellPrice = Double.NaN;
		}
		if(buyPrice > 0){
			buys = true;
			this.buyPrice = buyPrice;
		} else {
			buys = false;
			this.buyPrice = Double.NaN;
		}
		
	}
	
	/**
	 * Creates a chest with either a built in inventory, or an admin shop with no inventory
	 * @param item
	 */
	private Shop(ItemStack item, double sellPrice, double buyPrice){
		this.item = item;
		this.hasChest = false;
		this.chest = null;
		if(sellPrice > 0){
			sells = true;
			this.sellPrice = sellPrice;
		} else {
			sells = false;
			this.sellPrice = Double.NaN;
		}
		if(buyPrice > 0){
			buys = true;
			this.buyPrice = buyPrice;
		} else {
			buys = false;
			this.buyPrice = Double.NaN;
		}
	}
	
	public boolean isEmpty(){
		if(inventory == 0){
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean sellItem(){
		if(inventory < item.getAmount() || sells == false){
			return false;
		} else if (hasChest == true){
			chest.getInventory().removeItem(item);
			inventory -= item.getAmount();
			return true;
		} else {
			inventory -= item.getAmount();
			return true;
		}
	}
	
	protected boolean buyItem(){
		if(buys == false){
			return false;
		} else if (hasChest == true){
			chest.getInventory().addItem(item);
			inventory -= item.getAmount();
			return true;
		} else {
			inventory -= item.getAmount();
			return true;
		}
	}

}
