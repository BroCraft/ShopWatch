package data;

public class ItemReport {
	private String itemName;
	private int transactionCount;
	private double netTransaction;
	
	public ItemReport(Transaction transaction) {
		this.itemName = transaction.getItem();
		this.addTransaction(transaction);
		this.transactionCount = 0;
		this.netTransaction = 0.0;
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactionCount += transaction.getQuantity();
		this.netTransaction += transaction.getValue();
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the transactionCount
	 */
	public int getTransactionCount() {
		return transactionCount;
	}
	/**
	 * @param transactionCount the transactionCount to set
	 */
	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}
	/**
	 * @return the netTransaction
	 */
	public double getNetTransaction() {
		return netTransaction;
	}
	/**
	 * @param netTransaction the netTransaction to set
	 */
	public void setNetTransaction(double netTransaction) {
		this.netTransaction = netTransaction;
	}

}
