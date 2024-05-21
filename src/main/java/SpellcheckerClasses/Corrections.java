package SpellcheckerClasses;

import java.util.List;

/**
 * Represents the correction functionality in the spell checker application.
 * This class is responsible for handling various types of corrections to the text.
 */
public class Corrections {

	// private Metrics metrics;
	private List<Text> decompiledWords;

	/**
	 * Constructs a Corrections instance with a list of decompiled words.
	 *
	 * @param decompiledWords A list of Text objects representing decompiled words.
	 */
	public Corrections(List<Text> decompiledWords) {
		this.decompiledWords = decompiledWords;
	}

	/**
	 * Corrects a specified word manually.
	 *
	 * @param correction The corrected form of the word.
	 * @param word       The Text object representing the word to be corrected.
	 */
	public void manuallyCorrect(String correction, Text word) {
		for(Text words: decompiledWords) {
			if(words.getOrder() == word.getOrder()){
				words.setHasSpellingMistake(false);
				words.setText(correction);
				break;
			}
		}

	}

	/**
	 * Ignores the specified word for the rest of the application session.
	 *
	 * @param word The Text object representing the word to be ignored.
	 */
	public void ignoreRestOfApplication(Text word) {
		for(Text words: decompiledWords) {
			if(words.getText().equals(word.getText())){
				words.setIgnoreSpellingMistake(true);
				words.setHasSpellingMistake(false);
				break;
			}
		}
	}
	
	/**
	 * Ignores the specified word.
	 *
	 * @param word The Text object representing the word to be ignored.
	 */
	public void ignoreWord(Text word) {
		for(Text words: decompiledWords) {
			if(words.getOrder() == word.getOrder()){
				words.setIgnoreSpellingMistake(true);
				words.setHasSpellingMistake(false);
				break;
			}
		}
	}

	/**
	 * Deletes a specified word from the list of decompiled words.
	 *
	 * @param word The Text object representing the word to be deleted.
	 */
	public void deleteWord(Text word) {
		decompiledWords.remove(word);
	}

}
