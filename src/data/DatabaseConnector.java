package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import org.bukkit.Bukkit;

public class DatabaseConnector {
	public static final String SAVE_FILE_DIR = "plugins\\ShopWatch";
	public static final String SAVE_FILE_NAME = "SWSaveFile.sav";

	public static void saveTransactions(SWDataClass swDataClass) {

		FileOutputStream saveFile;
		ObjectOutputStream save;

		try {
			// Open a file to write to
			saveFile = new FileOutputStream(SAVE_FILE_DIR + "\\" + SAVE_FILE_NAME);
			save = new ObjectOutputStream(saveFile);

			// Save the whole class
			save.writeObject(swDataClass);

			// Close the stream
			saveFile.close();

			// Close the file
			save.close();

			// Catch errors in I/O
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadTransactions(SWDataClass swDataClass) {

		// Create the data objects for us to restore.
		SWDataClass tempData = new SWDataClass();

		// Wrap all in a try/catch block to trap I/O errors.

		// Open file to read from, named SavedObjects.sav.
		FileInputStream saveFile;
		try {
			saveFile = new FileInputStream(SAVE_FILE_DIR + "\\" + SAVE_FILE_NAME);
			ObjectInputStream save = new ObjectInputStream(saveFile);
			tempData = (SWDataClass) save.readObject();
			save.close(); // This also closes saveFile.
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now we do the restore.
		// readObject() returns a generic Object, we cast those back
		// into their original class type.
		// For primitive types, use the corresponding reference class.

		// Close the file.

		// Copy the records from the temporary structure over to the data class
		swDataClass.getTransactions().clear();

		Iterator<Transaction> transactionIterator = tempData.getTransactions()
				.iterator();
		while (transactionIterator.hasNext()) {
			swDataClass.getTransactions().add(transactionIterator.next());
		}

		// All done.
	}
}
