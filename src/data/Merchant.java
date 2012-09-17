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
	private List<String> emptyShops;
	
	public Merchant() {
		this.transactions = new LinkedList<Transaction>();
		this.emptyShops = new LinkedList<String>();
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
	 * @return the emptyShops
	 */
	public List<String> getEmptyShops() {
		return emptyShops;
	}
	/**
	 * @param emptyShops the emptyShops to set
	 */
	public void setEmptyShops(List<String> emptyShops) {
		this.emptyShops = emptyShops;
	}
	
	

}
