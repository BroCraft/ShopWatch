package data;

public class Transaction {
	private int id;
	private String playerName;
	private double value;
	private boolean read;
	
	public Transaction(String playerName, double value) {
		this.playerName = playerName;
		this.value = value;
		this.read = false;
	}
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the read
	 */
	public boolean isRead() {
		return read;
	}
	/**
	 * @param read the read to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}
}
