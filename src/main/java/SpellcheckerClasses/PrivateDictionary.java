package SpellcheckerClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a private dictionary in the spell checker application.
 * This class extends the Dictionary class, providing additional functionalities specific to a user's private dictionary.
 */
public class PrivateDictionary extends Dictionary {

	/**
	 * Resets the private dictionary, clearing all its contents.
	 */
	public void reset() {
		dictionaryWords.clear();
	}

	/**
	 * Removes a word from the private dictionary.
	 *
	 * @param word The word to be removed from the dictionary.
	 */
	public void removeWord(String word) {
		if (this.isInDictionary(word)) {
			dictionaryWords.remove(word);
		}
	}

	/**
	 * Retrieves all words in the private dictionary.
	 *
	 * @return A list of words in the private dictionary.
	 */
	public List<String> getAllWords() {
		return new ArrayList<>(dictionaryWords.keySet());
	}
}
