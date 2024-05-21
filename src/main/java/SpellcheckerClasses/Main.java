package SpellcheckerClasses;

import java.util.ArrayList;
import java.util.List;
/**
 * The main class for the spell checker application.
 * Demonstrates the usage of various components of the application such as document handling, spell checking, and dictionary management.
 */
public class Main {
	/**
	 * The entry point of the spell checker application.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		// Initialize the main program
		SpellChecker spellcheck = new SpellChecker();

		// Read in global dictionary
		Admin admin = new Admin(true);
		spellcheck.setGlobalDictionary(admin.importWords("/documents/words_alpha.txt"));

		// Create document and do actions on document
		String documentName = "file.txt";
		Document doc1 = new Document(documentName, "");

		spellcheck.addDocument(doc1);
		spellcheck.getDocumentByName(documentName).metrics.printMetrics();

		// Add some words to private dictionary
		// We need to recheck each document for spelling mistakes when we do this
		spellcheck.user.getPrivateDictionary().add("PiniINTA");
		spellcheck.reconfigureSpellingMistakes();

		spellcheck.user.getPrivateDictionary().add("Tristannn");
		spellcheck.reconfigureSpellingMistakes();

		spellcheck.user.getPrivateDictionary().removeWord("Tristannn");
		spellcheck.reconfigureSpellingMistakes();

		//Reset private dictionary
		spellcheck.user.getPrivateDictionary().reset();
		spellcheck.user.getPrivateDictionary().getWords();

//		spellcheck.getDocumentByName(documentName).ToStringDecompiled();

		// Get suggestions for a mispelled words
//		Text item = spellcheck.getDocumentByName(documentName).getMispelledWords().get(0);
//		List<String> threeSuggestions = spellcheck.suggestions.getSuggestions(item.getText());
//		System.out.println("Suggestion 1:" + threeSuggestions.get(0));
//		System.out.println("Suggestion 2:" + threeSuggestions.get(1));
//		System.out.println("Suggestion 3:" + threeSuggestions.get(2));
//
//		Text item2 = spellcheck.getDocumentByName(documentName).getMispelledWords().get(1);
//		threeSuggestions = spellcheck.suggestions.getSuggestions(item2.getText());
//		System.out.println("\nSuggestion 1:" + threeSuggestions.get(0));
//		System.out.println("Suggestion 2:" + threeSuggestions.get(1));
//		System.out.println("Suggestion 3:" + threeSuggestions.get(2));

		// Infinite loop through misspelled words in a document and make suggestions
		for (;;) {
			int i = 0;
			for (Text word : spellcheck.getDocumentByName(documentName).getMispelledWords()) {
				List<String> textSuggestions = spellcheck.suggestions.getSuggestions(word.getText());
				System.out.println("Word:" + word.getText());
				System.out.println("Suggestion 1:" + textSuggestions.get(0));
				System.out.println("Suggestion 2:" + textSuggestions.get(1));
				System.out.println("Suggestion 3:" + textSuggestions.get(2));

				// Skip
				if (i == 1) {
					System.out.println("Skip");
					i++;
				}
				// Manually correct by input
				else if (i == 0) {
					System.out.println("Manual");
					i++;
					String input = "word";
					spellcheck.getDocumentByName(documentName).corrections.manuallyCorrect(input, word);
					spellcheck.reconfigureSpellingMistakes();
				}
				// Suggestion correction
				else if (i == 2) {
					System.out.println("suggestion");
					i++;
					spellcheck.getDocumentByName(documentName).corrections.manuallyCorrect(textSuggestions.get(0),
							word);
					spellcheck.reconfigureSpellingMistakes();
				}
				// Ignore rest of application
				else if (i == 3) {
					System.out.println("Ignore");
					spellcheck.getDocumentByName(documentName).corrections.ignoreRestOfApplication(word);
					spellcheck.reconfigureSpellingMistakes();
					i++;
				}
				// Delete word
				else if (i == 4) {
					System.out.println("delete");
					spellcheck.getDocumentByName(documentName).corrections.deleteWord(word);
					spellcheck.reconfigureSpellingMistakes();
					i = 0;
				}

			}

			if (spellcheck.getDocumentByName(documentName).getMispelledWords().size() == 0)
				break;
		}

		spellcheck.getDocumentByName(documentName).metrics.printMetrics();
		
		// Save file
		spellcheck.getDocumentByName(documentName).saveFile();

		// Delete a document
		spellcheck.deleteDocument(spellcheck.getDocumentByName(documentName));
		System.out.println("End of program");
		
		
	}

}
