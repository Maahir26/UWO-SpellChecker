package SpellcheckerClasses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents the suggestion engine for the spell checker application.
 * This class provides various methods to generate suggestions for potentially misspelled words.
 */
public class Suggestions {
	
	// TODO this entire class
	public Dictionary globalDictionary;
	
	/**
	 * Constructs a Suggestions instance with a reference to the global dictionary.
	 *
	 * @param globalDictionary The global dictionary used for generating suggestions.
	 */
	public Suggestions(Dictionary globalDictionary) {
		this.globalDictionary = globalDictionary;
	}
	
	/**
	 * Generates a list of suggestions for a given word.
	 *
	 * @param word The word for which suggestions are needed.
	 * @return A list of suggested corrections.
	 */
	public List<String> getSuggestions(String word) {
	    List<String> suggestions = new ArrayList<>();

	    // Add suggestion from misCaptilization if available
	    if (misCaptilization(word) != null) {
	        suggestions.add(misCaptilization(word));
	    }

	    // Add suggestion from removePrefix if available
//	    if (removePrefix(word) != null) {
//	        suggestions.add(removePrefix(word));
//	    }

	    // Add suggestions from otherSuggestions
	    List<String> otherSuggestions = getSuggestionsClose(word);
	    for (String suggestion : otherSuggestions) {
	        if (suggestions.size() >= 3) {
	            break; // Stop if we already have 3 suggestions
	        }
	        if (suggestion != null) {
	            suggestions.add(suggestion);
	        }
	    }

	    // Fill in with "No Suggestions" if fewer than 3 suggestions
	    while (suggestions.size() < 3) {
	        suggestions.add("No Suggestions");
	    }

	    return suggestions;
	}

	/**
	 * Analyzes a word for double characters and suggests corrections.
	 *
	 * @param word The word to analyze.
	 * @return A corrected word if a double character is detected, or the original word otherwise.
	 */
	public String doubleWord(String word) {
		// Check if there's a double word if there is add to list
		// This is going to be hard
		return word;
	}
	
	/**
	 * Suggests a correction for a word with potential capitalization issues.
	 *
	 * @param word The word to analyze.
	 * @return A corrected word with proper capitalization, or null if no correction is needed.
	 */
	public String misCaptilization(String word) {
		// Check if there's a miscapitlization, if there is then fix it and add to list
		if (globalDictionary.isInDictionary(word.toLowerCase())) {
			return word.toLowerCase();
		} else {
			return null;
		}
	}
	
	/**
	 * Suggests a correction for a word by removing a potential prefix.
	 *
	 * @param word The word to analyze.
	 * @return A corrected word with the prefix removed, or null if no correction is needed.
	 */
	public String removePrefix(String word) {
		if (word == null || word.length() < 4) {
			return null; // or return word; if you prefer
		}

		if (globalDictionary.isInDictionary(word.toLowerCase().substring(1))) {
			return word.substring(1);
		} else if (globalDictionary.isInDictionary(word.toLowerCase().substring(2))) {
			return word.substring(2);
		} else if (globalDictionary.isInDictionary(word.toLowerCase().substring(3))) {
			return word.substring(3);
		}
		return null;
	}
	
	/**
	 * Suggests a correction for a word by removing a potential suffix.
	 *
	 * @param word The word to analyze.
	 * @return A corrected word with the suffix removed, or null if no correction is needed.
	 */
	public String removeSuffix(String word) {
		if (word == null || word.length() < 4) {
			return null; // or return word; if you prefer
		}

		if (globalDictionary.isInDictionary(word.toLowerCase().substring(0, word.length() - 1))) {
			return word.substring(0, word.length() - 1);
		} else if (globalDictionary.isInDictionary(word.toLowerCase().substring(0, word.length() - 2))) {
			return word.substring(0, word.length() - 2);
		} else if (globalDictionary.isInDictionary(word.toLowerCase().substring(0, word.length() - 3))) {
			return word.substring(0, word.length() - 3);
		}
		return null;
	}
	
	/**
	 * Finds words in the dictionary that are closely related to the given word.
	 * This method is intended to implement an algorithm that identifies words with close resemblance to the input word,
	 * such as words with minor spelling differences.
	 *
	 * Note: This method is currently a placeholder and returns the input word unchanged.
	 * Future implementation will include logic to find and return closely related words.
	 *
	 * @param word The word to find close matches for.
	 * @return A word closely related to the given word. Currently returns the input word.
	 */
	public String findWordsThatAreClose(String word) {
		// A more difficult implementation to find a word

		return word; // Return the word unchanged for now
	}
	
	/**
	 * Finds words in the dictionary that are close to the given word.
	 *
	 * @param word The word to find close matches for.
	 * @return A list of words that are close to the given word.
	 */
	private List<String> getSuggestionsClose(String word) {
		List<String> suggestions = new ArrayList<>();
		int minDistance = Integer.MAX_VALUE;

		for (String dictWord : globalDictionary.getDictionary().keySet()) {
			int distance = levenshteinDistance(word, dictWord);

			if (distance < minDistance) {
				minDistance = distance;
				suggestions.clear();
				suggestions.add(dictWord);
			} else if (distance == minDistance) {
				suggestions.add(dictWord);
			}
		}

		return suggestions;
	}
	
	/**
	 * Calculates the Levenshtein distance between two strings.
	 *
	 * @param x The first string.
	 * @param y The second string.
	 * @return The Levenshtein distance between the two strings.
	 */
	private int levenshteinDistance(String x, String y) {
		int[][] dp = new int[x.length() + 1][y.length() + 1];

		for (int i = 0; i <= x.length(); i++) {
			for (int j = 0; j <= y.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = Math
							.min(Math.min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
									dp[i - 1][j] + 1), dp[i][j - 1] + 1);
				}
			}
		}

		return dp[x.length()][y.length()];
	}
	
	/**
	 * Calculates the cost of substituting one character for another.
	 *
	 * @param a The first character.
	 * @param b The second character.
	 * @return The cost of substitution.
	 */
	private int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

}