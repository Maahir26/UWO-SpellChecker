package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Document;
import SpellcheckerClasses.Text;
import SpellcheckerClasses.WordTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class DocumentTest {

    private Document document;

    @BeforeEach
    void setUp() {
        document = new Document("testDoc", "/path/to/testDoc");
        // You might need to mock the fileContent or provide it through a setter for testing.
    }

    @Test
    void testDecideWordType() {
        Text result = document.decideWordType("test", 1);
        assertEquals(WordTypes.WORD, result.getType(), "Word type should be WORD for normal text.");

        result = document.decideWordType("<markup>", 2);
        assertEquals(WordTypes.MARKUP, result.getType(), "Word type should be MARKUP for markup text.");

        // Add more assertions for other types like NOTASCI, NUMBER, etc.
    }

    @Test
    void testGetMispelledWords() {
        document.addToDecompiledWords(new Text("correct", WordTypes.WORD, 1));
        Text misspelledWord = new Text("incorrrect", WordTypes.WORD, 2);
        misspelledWord.setHasSpellingMistake(true);
        document.addToDecompiledWords(misspelledWord);

        List<Text> misspelledWords = document.getMispelledWords();
        assertTrue(misspelledWords.contains(misspelledWord), "List should contain the misspelled word.");
        assertEquals(1, misspelledWords.size(), "There should be only one misspelled word.");
    }

    @Test
    void testToStringFile() {
        document.addToDecompiledWords(new Text("Hello", WordTypes.WORD, 1));
        document.addToDecompiledWords(new Text(" ", WordTypes.SPACE, 2));
        document.addToDecompiledWords(new Text("world", WordTypes.WORD, 3));

        String result = document.toStringFile();
        assertEquals("Hello world", result, "toStringFile should return the concatenated text of the document.");
    }

    // More tests for other methods...

}
