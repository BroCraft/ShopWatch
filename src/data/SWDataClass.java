package data;

import java.util.LinkedList;
import java.util.List;

public class SWDataClass {
	private List<Transaction> transactions;
	
	public SWDataClass() {
		transactions= new LinkedList<Transaction>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}
