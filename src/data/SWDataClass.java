package data;

import java.io.Serializable;
import java.util.HashMap;

public class SWDataClass implements Serializable {
	/**
	 * Used to dump class to a file
	 */
	private static final long serialVersionUID = -1042904485915743989L;
	private HashMap<String, Merchant> merchants;
	
	public SWDataClass() {
		merchants = new HashMap<String, Merchant>();
	}

	public HashMap<String, Merchant> getMerchants() {
		return merchants;
	}

}
