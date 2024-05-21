package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SpellCheckerTest {

    private SpellChecker spellChecker;
    private Document mockDocument;

    @BeforeEach
    void setUp() {
        spellChecker = new SpellChecker();
        mockDocument = new Document("testDoc", "/path/to/testDoc");
        // Populate mockDocument with some Text objects
        // For example, mockDocument.addToDecompiledWords(new Text("example", WordTypes.WORD, 1));
    }

    @Test
    void testAddDocument() {
        spellChecker.addDocument(mockDocument);
        assertEquals(1, spellChecker.getDocuments().size(), "Document should be added to the spell checker.");
    }

    @Test
    void testDeleteDocument() {
        spellChecker.addDocument(mockDocument);
        spellChecker.deleteDocument(mockDocument);
        assertTrue(spellChecker.getDocuments().isEmpty(), "Document should be removed from the spell checker.");
    }

    @Test
    void testGetDocumentByName() {
        spellChecker.addDocument(mockDocument);
        Document retrievedDoc = spellChecker.getDocumentByName("testDoc");
        assertNotNull(retrievedDoc, "Should retrieve the document with the given name.");
    }

    @Test
    void testCheckForSpellingMistakes() {
        // This test requires mocking or setting up the globalDictionary and/or the user's privateDictionary.
        // Since we can't mock, we need to set up these dictionaries with known words and verify the behavior.
    }

    @Test
    void testReconfigureSpellingMistakes() {
        // Similar to testCheckForSpellingMistakes, this test requires a setup where you can verify changes
        // in the spelling mistake status of words in documents.
    }

    // Additional tests for other methods can be added here...
}
