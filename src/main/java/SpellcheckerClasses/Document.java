package SpellcheckerClasses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Represents a document in the spell checker application.
 * This class handles decompiling, saving, and managing text corrections in a document.
 */
public class Document extends File {
	private List<Text> decompiledWords;
	public Corrections corrections;
	private Stack<Change> changeHistory;
	private Stack<String> changeType;
	
	/**
	 * Constructs a Document instance with the specified name and path.
	 *
	 * @param name The name of the document.
	 * @param path The path where the document is located.
	 */
	public Document(String name, String path) {
		super(name, path); // Call the superclass constructor
		decompiledWords = new ArrayList<>();
		decompileDocument();
		corrections = new Corrections(decompiledWords);
		this.changeHistory = new Stack<>();
		this.changeType = new Stack<>();

	}
	
	/**
	 * (Re)Constructs a Document instance with the specified document object.
	 *
	 * @param doc The document object.
	 */
	public Document(Document doc) {
		super(doc.getName(), doc.getPath()); // Call the superclass constructor
		this.changeHistory = new Stack<>();
		this.changeType = new Stack<>();
		decompiledWords = new ArrayList<>();
		decompileDocument();
		corrections = new Corrections(decompiledWords);

	}

	public Document(String name, String path, boolean dontDecompile) {
		super(name, path); // Call the superclass constructor
		decompiledWords = new ArrayList<>();
		corrections = new Corrections(decompiledWords);
		this.changeHistory = new Stack<>();
		this.changeType = new Stack<>();

	}
	/**
	 * Decompiles the content of the document into manageable Text objects.
	 */
	public void decompileDocument() {
		//Here
		if (fileContent != null && !fileContent.isEmpty()) {
			// Split the file content into words and add them to the list
			String[] words = fileContent.split("[ ,.;!?\\n\\r]+"); // Split by any whitespace
			int counter = 0;
			for (int i = 0; i < words.length; i++) {
				if(words[i] == " "){
					decompiledWords.add(new Text(" ", WordTypes.SPACE, ++counter));
				}
				if (words[i].contains("\n")) {
					String[] parts = words[i].split("\n", 2);
					decompiledWords.add(decideWordType(parts[0], ++counter));
					decompiledWords.add(new Text("\n", WordTypes.NEWLINE, ++counter));
				} else {
					String word = words[i];
					decompiledWords.add(decideWordType(word, ++counter));
					if (i != words.length - 1) {
						decompiledWords.add(new Text(" ", WordTypes.SPACE, ++counter));
					}
				}

			}
		}
	}
	
	/**
	 * Decides the type of word and creates a Text object for it.
	 *
	 * @param word  The word to analyze.
	 * @param order The order of the word in the document.
	 * @return A Text object representing the word.
	 */
	public Text decideWordType(String word, int order) {

		if (word == " ")
			return new Text(word, WordTypes.SPACE, order);

		String markupR = "<[^>]+>";
		Pattern pattern = Pattern.compile(markupR);
		Matcher matcher = pattern.matcher(word);

		if (matcher.find()) {
			return new Text(word, WordTypes.MARKUP, order);
		}

		String markupAsci = "[^\\x00-\\x7F]+";
		pattern = Pattern.compile(markupAsci);
		matcher = pattern.matcher(word);

		if (matcher.find()) {
			return new Text(word, WordTypes.NOTASCI, order);
		}

		String markupNumber = "\\d";
		pattern = Pattern.compile(markupNumber);
		matcher = pattern.matcher(word);

		if (matcher.find()) {
			return new Text(word, WordTypes.NUMBER, order);
		}

		metrics.incrementNumberOfWords();
		return new Text(word, WordTypes.WORD, order);
	}
	
	/**
	 * Saves the current state of the document to a file.
	 */
	public void saveFile() {
		String fileFullPath = this.getPath() + this.getName();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileFullPath))) {
			for (Text word : decompiledWords) {
				writer.write(word.getText());
			}
		} catch (IOException e) {
			System.err.println("An error occurred while writing to the file: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves a list of misspelled words in the document.
	 *
	 * @return A List of Text objects representing misspelled words.
	 */
	public List<Text> getMispelledWords() {
		List<Text> misspelledWords = new ArrayList<Text>();
		for (Text word : decompiledWords) {
			if (word.getHasSpellingMistake() == true && word.getType() == WordTypes.WORD)
				misspelledWords.add(word);
		}
		return misspelledWords;
	}
	
	/**
	 * Retrieves all words in the document.
	 *
	 * @return A List of all Text objects in the document.
	 */
	public List<Text> getAllWords() {
		return decompiledWords;
	}
	
	/**
	 * Converts the document's content to a String.
	 *
	 * @return A String representing the document's content.
	 */
	public String toStringFile(){
		String file = "";
		for (Text text : decompiledWords) {
			file += text.getText();
		}
		return file;
	}
	
	/**
	 * Prints the decompiled words for testing purposes.
	 */
	public void ToStringDecompiled() {
		for (Text text : decompiledWords) {
			System.out.println(text.getOrder() + " Type:" + text.getType() + " Text:" + text.getText()
					+ " Incorrect Spelling:" + text.getHasSpellingMistake());
		}
	}
	// Method to add a Text object to the list of decompiled words.
	public void addToDecompiledWords(Text text){
		this.decompiledWords.add(text);
	}
	// Inner class representing a change made to a Text object.
	private class Change {
		Text oriTxt;
		int pos;
		// Constructor for the Change class.
		Change(Text oriTxt, int position) {
			this.oriTxt = oriTxt;
			this.pos = position;
		}
	}
	// Method to modify the text of a decompiled word at a specified position.
	public void modifyText(int pos, String newText, String change) {
		if (pos >= 0 && pos < decompiledWords.size()) {
			Text oriText = decompiledWords.get(pos);
			changeHistory.push(new Change(new Text(oriText.getText(), oriText.getType(), oriText.getOrder()), pos));
			changeType.push(change);


			oriText.setText(newText);
		}
	}
	// Method to undo the last modification by reverting the text at the specified position to its previous state
	public void undo() {
		if (!changeHistory.isEmpty()) {

			Change lastChange = changeHistory.pop();
			Text oriText = lastChange.oriTxt;
			int pos = lastChange.pos;

			if (changeType.pop().equals("DELETE")) {
				oriText.setText(oriText.getText() + " ");
				decompiledWords.set(pos, oriText);
			}
			else {
				if (pos >= 0 && pos < decompiledWords.size()) {
					decompiledWords.set(pos, oriText);
				}
			}
		}
	}

}