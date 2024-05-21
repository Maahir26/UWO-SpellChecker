package SpellcheckerClasses;
/**
 * Represents the metrics for spell checking in the spell checker application.
 * This class is responsible for tracking various statistics such as the number of characters, lines, words, and types of corrections made.
 */
public class Metrics {
	private int numberOfCharacters;	
	private int numberOfLines;
	private int numberOfWords;
	private int numberOfMisspelledWords;
	private int numberOfDoubleWords;//
	private int numberOfWordsFixedByManual;//
	private int numberOfWordsFixedByMisspelling;//
	private int numberOfWordsFixedByDouble;//
	private int numberOfWordsDeleted;//
	
	/**
	 * Constructs a Metrics instance, initializing all metrics to zero.
	 */
	public Metrics() {
		this.numberOfCharacters = 0;
		this.numberOfLines = 0;
		this.numberOfWords = 0;
		this.numberOfMisspelledWords = 0;
		this.numberOfDoubleWords = 0;
		this.numberOfWordsFixedByManual = 0;
		this.numberOfWordsFixedByMisspelling = 0;
		this.numberOfWordsFixedByDouble = 0;
		this.numberOfWordsDeleted = 0;
	}

	/**
	 * Increments the number of characters by the specified amount.
	 *
	 * @param characters The number of characters to add to the count.
	 */
	public void incrementNumberOfCharacters(int characters) {
		numberOfCharacters += characters;
	}
	
	/**
	 * Increments the number of lines.
	 */
	public void incrementNumberOfLines() {
		numberOfLines++;
	}
	
	/**
	 * Increments the number of words.
	 */
	public void incrementNumberOfWords() {
		numberOfWords++;
	}
	
	/**
	 * Increments the number of misspelled words.
	 */
	public void incrementNumberOfMisspelledWords() {
		numberOfMisspelledWords++;
	}
	
	/**
	 * Increments the number of double words.
	 */
	public void incrementNumberOfDoubleWords() {
		numberOfDoubleWords++;
	}
	
	/**
	 * Increments the number of words fixed manually.
	 */
	public void incrementNumberOfWordsFixedByManual() {
		numberOfWordsFixedByManual++;
	}
	
	/**
	 * Increments the number of misspelled words fixed.
	 */
	public void incrementNumberOfWordsFixedByMisspelling() {
		numberOfWordsFixedByMisspelling++;
	}
	
	/**
	 * Increments the number of double words fixed.
	 */
	public void incrementNumberOfWordsFixedByDouble() {
		numberOfWordsFixedByDouble++;
	}
	
	/**
	 * Increments the number of words deleted.
	 */
	public void incrementNumberOfWordsDeleted() {
		numberOfWordsDeleted++;
	}

	/**
	 * Gets the number of characters.
	 *
	 * @return The number of characters.
	 */
	public int getNumberOfCharacters() {
		return numberOfCharacters;
	}
	
	/**
	 * Gets the number of lines.
	 *
	 * @return The number of lines.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}
	
	/**
	 * Gets the number of words.
	 *
	 * @return The number of words.
	 */
	public int getNumberOfWords() {
		return numberOfWords;
	}
	
	/**
	 * Gets the number of misspelled words.
	 *
	 * @return The number of misspelled words.
	 */
	public int getNumberOfMisspelledWords() {
		return numberOfMisspelledWords;
	}
	
	/**
	 * Gets the number of double words.
	 *
	 * @return The number of double words.
	 */
	public int getNumberOfDoubleWords() {
		return numberOfDoubleWords;
	}
	
	/**
	 * Gets the number of words fixed manually.
	 *
	 * @return The number of words fixed manually.
	 */
	public int getNumberOfWordsFixedByManual() {
		return numberOfWordsFixedByManual;
	}
	
	/**
	 * Gets the number of misspelled words fixed.
	 *
	 * @return The number of misspelled words fixed.
	 */
	public int getNumberOfWordsFixedByMisspelling() {
		return numberOfWordsFixedByMisspelling;
	}
	
	/**
	 * Gets the number of double words fixed.
	 *
	 * @return The number of double words fixed.
	 */
	public int getNumberOfWordsFixedByDouble() {
		return numberOfWordsFixedByDouble;
	}
	
	/**
	 * Gets the number of words deleted.
	 *
	 * @return The number of words deleted.
	 */
	public int getNumberOfWordsDeleted() {
		return numberOfWordsDeleted;
	}

	/**
	 * Prints the current metrics to the console.
	 * This method displays all tracked metrics in a readable format.
	 */
	public void resetNumberOfMispelledWords(){ this.numberOfMisspelledWords = 0;}
	/**
	 * Prints the current metrics to the console.
	 * This method displays all tracked metrics in a readable format.
	 */
	public void printMetrics() {
		System.out.println("numberOfCharacters:" + numberOfCharacters);
		System.out.println("numberOfLines:" + numberOfLines);
		System.out.println("numberOfWords:" + numberOfWords);
		System.out.println("numberOfMisspelledWords:" + numberOfMisspelledWords);
		System.out.println("numberOfWordsFixedByManual:" + numberOfWordsFixedByManual);
		System.out.println("numberOfWordsFixedByMisspelling:" + numberOfWordsFixedByMisspelling);
		System.out.println("numberOfWordsFixedByDouble:" + numberOfWordsFixedByDouble);
		System.out.println("numberOfWordsDeleted:" + numberOfWordsDeleted);

	}
}