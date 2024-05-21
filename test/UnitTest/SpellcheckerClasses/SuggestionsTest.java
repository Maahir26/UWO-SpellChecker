package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Dictionary;
import SpellcheckerClasses.Suggestions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class SuggestionsTest {

    private Suggestions suggestions;
    private Dictionary mockDictionary;

    @BeforeEach
    void setUp() {
        mockDictionary = new Dictionary();
        // Assuming Dictionary class has a method to add words
        mockDictionary.add("example");
        mockDictionary.add("test");
        mockDictionary.add("word");

        suggestions = new Suggestions(mockDictionary);
    }

    @Test
    void getSuggestionsForMisspelledWord() {
        // Add the word 'exampel' to the dictionary to simulate a misspelled word
        mockDictionary.add("exampel");
        List<String> result = suggestions.getSuggestions("exampel");
        assertTrue(result.contains("example"), "Should suggest 'example' for 'exampel'");
    }

    @Test
    void misCaptilizationSuggestion() {
        String result = suggestions.misCaptilization("EXAMPLE");
        assertEquals("example", result, "Should suggest 'example' for 'EXAMPLE'");
    }

    @Test
    void removePrefixSuggestion() {
        mockDictionary.add("ample");
        String result = suggestions.removePrefix("example");
        assertEquals("ample", result, "Should suggest 'ample' for 'example'");
    }

    @Test
    void removeSuffixSuggestion() {
        mockDictionary.add("exam");
        String result = suggestions.removeSuffix("example");
        assertEquals("exam", result, "Should suggest 'exam' for 'example'");
    }

    @Test
    void findWordsThatAreClose() {
        // Since this method is a placeholder, test it accordingly
        String result = suggestions.findWordsThatAreClose("examp");
        assertEquals("examp", result, "Should return the same word 'examp'");
    }

    // Additional tests for other methods can be added here...
}
