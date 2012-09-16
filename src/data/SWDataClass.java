package data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SWDataClass implements Serializable {
	/**
	 * Used to dump class to a file
	 */
	private static final long serialVersionUID = -1042904485915743989L;
	private List<Transaction> transactions;
	
	public SWDataClass() {
		transactions= new LinkedList<Transaction>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}
