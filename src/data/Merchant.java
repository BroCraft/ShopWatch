package data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Merchant implements Serializable {
	/**
	 * Used for dumping to file
	 */
	private static final long serialVersionUID = 5544922801001160326L;
	private List<Transaction> transactions;
	private List<Shop> shops;
	
	public Merchant() {
		this.transactions = new LinkedList<Transaction>();
		this.shops = new LinkedList<Shop>();
	}
	
	/**
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}
	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	/**
	 * @return the emptyShops, which is an empty list if no shops are empty
	 */
	public List<Shop> getEmptyShops() {
		List<Shop> emptyShops = new LinkedList<Shop>();
		for(Shop s: shops){
			if(s.isEmpty()){
				emptyShops.add(s);
			}
		}
		return emptyShops;
	}
	

}
