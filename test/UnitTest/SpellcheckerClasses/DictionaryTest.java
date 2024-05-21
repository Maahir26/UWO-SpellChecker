package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Dictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

class DictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new Dictionary();
    }

    @Test
    void testAddAndIsInDictionary() {
        assertFalse(dictionary.isInDictionary("test"), "Word should not be in dictionary initially.");

        dictionary.add("test");
        assertTrue(dictionary.isInDictionary("test"), "Word should be in dictionary after being added.");
    }

    @Test
    void testGetWords() {
        dictionary.add("hello");
        dictionary.add("world");

        HashMap<String, Integer> words = dictionary.getWords();
        assertTrue(words.containsKey("hello") && words.containsKey("world"), "Dictionary should contain added words.");
    }

    @Test
    void testSetWords() {
        HashMap<String, Integer> newWords = new HashMap<>();
        newWords.put("new", 0);
        newWords.put("words", 1);

        dictionary.setWords(newWords);
        assertTrue(dictionary.isInDictionary("new") && dictionary.isInDictionary("words"), "Dictionary should contain the newly set words.");
        assertFalse(dictionary.isInDictionary("hello"), "Dictionary should not contain words that were not set.");
    }

    @Test
    void testGetDictionary() {
        dictionary.add("sample");
        HashMap<String, Integer> words = dictionary.getDictionary();
        assertNotNull(words, "getDictionary should return a non-null HashMap.");
        assertEquals(1, words.size(), "Dictionary should have one word.");
        assertTrue(words.containsKey("sample"), "Dictionary should contain the added word.");
    }

}
