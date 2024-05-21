package SpellcheckerClasses;

import java.util.HashMap;

/**
 * Represents a dictionary in the spell checker application.
 * This class is responsible for storing and managing words along with their functionalities.
 */
public class Dictionary {
	public HashMap<String, Integer> dictionaryWords;
	private int numWords;

	/**
	 * Constructs a Dictionary instance.
	 */
	public Dictionary() {
		dictionaryWords = new HashMap<>();
		numWords = 0;
	}

	/**
	 * Retrieves all words in the dictionary.
	 *
	 * @return A HashMap containing words and their corresponding indices.
	 */
	public HashMap<String, Integer> getWords() {
		return dictionaryWords;
	}

	/**
	 * Sets the dictionary words.
	 *
	 * @param hashMap A HashMap containing words to be set in the dictionary.
	 */
	public void setWords(HashMap<String, Integer> hashMap) {
		this.dictionaryWords = hashMap;
	}

	/**
	 * Checks if a given word is in the dictionary.
	 *
	 * @param word The word to check in the dictionary.
	 * @return true if the word is in the dictionary, false otherwise.
	 */
	public boolean isInDictionary(String word) {
		return dictionaryWords.containsKey(word);
	}

	/**
	 * Adds a word to the dictionary.
	 *
	 * @param word The word to be added to the dictionary.
	 */
	public void add(String word) {
		dictionaryWords.put(word, numWords++);
	}

	/**
	 * Retrieves the dictionary.
	 *
	 * @return A HashMap representing the dictionary.
	 */
	public HashMap<String, Integer> getDictionary() {
		return this.dictionaryWords;
	}

}
