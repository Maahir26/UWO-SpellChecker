package SpellcheckerClasses;

/**
 * Represents a piece of text in the spell checker application.
 * This class is used to manage individual words or text elements along with their characteristics such as type, order, and spelling status.
 */
public class Text {

	private int order;
	private String text;
	private WordTypes type;
	private Boolean hasSpellingMistake;
	private Boolean ignoreSpellingMistake;

	/**
	 * Constructs a Text instance with specified word, type, and order.
	 *
	 * @param word  The text of the word.
	 * @param type  The type of the word, as defined in WordTypes.
	 * @param order The order of the word in the document.
	 */
	public Text(String word, WordTypes type, int order) {
		this.text = word;
		this.type = type;
		this.order = order;
		this.ignoreSpellingMistake = false;
		hasSpellingMistake = false;
	}

	/**
	 * Gets the text of the word.
	 *
	 * @return The text of the word.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text of the word.
	 *
	 * @param text The new text for the word.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the type of the word.
	 *
	 * @return The type of the word.
	 */
	public WordTypes getType() {
		return this.type;
	}

	/**
	 * Gets the order of the word in the document.
	 *
	 * @return The order of the word.
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Gets the spelling mistake status of the word.
	 *
	 * @return True if the word has a spelling mistake, false otherwise.
	 */
	public Boolean getHasSpellingMistake() {
		return this.hasSpellingMistake;
	}
	
	/**
	 * Gets the ignore mistake status of the word.
	 *
	 * @return True if the word has a spelling mistake, false otherwise.
	 */
	public Boolean getIgnoreSpellingMistake() {
		return this.ignoreSpellingMistake;
	}
	
	/**
	 * Sets the spelling mistake status of the word.
	 *
	 * @param value True if the word has a spelling mistake, false otherwise.
	 */
	public void setHasSpellingMistake(Boolean value) {
		this.hasSpellingMistake = value;
	}
	
	/**
	 * Sets the ignore mistake status of the word.
	 *
	 * @param value True if the word has a spelling mistake, false otherwise.
	 */
	public void setIgnoreSpellingMistake(Boolean value) {
		this.ignoreSpellingMistake = value;
	}
}
