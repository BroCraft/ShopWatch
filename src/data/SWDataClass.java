package data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class SWDataClass implements Serializable {
	/**
	 * Used to dump class to a file
	 */
	private static final long serialVersionUID = -1042904485915743989L;
	private HashMap<String, LinkedList<Transaction>> transactions;
	
	public SWDataClass() {
		transactions = new HashMap<String, LinkedList<Transaction>>();
	}

	public HashMap<String, LinkedList<Transaction>> getTransactions() {
		return transactions;
	}

}
