package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.PrivateDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class PrivateDictionaryTest {

    private PrivateDictionary privateDictionary;

    @BeforeEach
    void setUp() {
        privateDictionary = new PrivateDictionary();
        privateDictionary.add("test");
        privateDictionary.add("sample");
    }

    @Test
    void testReset() {
        privateDictionary.reset();
        assertTrue(privateDictionary.getWords().isEmpty(), "Dictionary should be empty after reset.");
    }

    @Test
    void testRemoveWord() {
        privateDictionary.removeWord("test");
        assertFalse(privateDictionary.isInDictionary("test"), "Word 'test' should be removed from the dictionary.");
    }

    @Test
    void testGetAllWords() {
        List<String> words = privateDictionary.getAllWords();
        assertTrue(words.contains("test") && words.contains("sample"), "Dictionary should contain 'test' and 'sample'.");
        assertEquals(2, words.size(), "Dictionary should have 2 words.");
    }

    @Test
    void testRemoveNonExistentWord() {
        privateDictionary.removeWord("nonexistent");
        assertEquals(2, privateDictionary.getAllWords().size(), "Removing a non-existent word should not change the dictionary size.");
    }
}
