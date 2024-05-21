package SpellcheckerClasses;

import java.io.*;

/**
 * Represents an admin user for the spell checker application.
 * Admin has additional privileges such as importing words into the dictionary.
 */
public class Admin extends User {
	/**
	 * Constructs an Admin instance.
	 *
	 * @param isAdmin Boolean indicating whether the user is an admin.
	 */
	public Admin(Boolean isAdmin) {
		super(isAdmin);
	}

	/**
	 * Imports words into a dictionary from a file.
	 *
	 * @param filePath The file path of the dictionary file.
	 * @return A Dictionary object populated with words from the file.
	 */
	public Dictionary importWords(String filePath) {
		Dictionary dictionary = new Dictionary();

		BufferedReader reader = null;

		try {
			// Use getClass().getResourceAsStream() to get the file from the resources folder
			InputStream is = getClass().getResourceAsStream(filePath);
			reader = new BufferedReader(new InputStreamReader(is));

			String line = reader.readLine();
			while (line != null) {
				dictionary.add(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		return dictionary;
	}

}
