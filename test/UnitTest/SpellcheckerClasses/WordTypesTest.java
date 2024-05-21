package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.WordTypes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordTypesTest {

    @Test
    void testEnumValues() {
        assertEquals(6, WordTypes.values().length, "WordTypes should have 6 defined values.");

        assertNotNull(WordTypes.WORD, "WordTypes should have a 'WORD' value.");
        assertNotNull(WordTypes.NEWLINE, "WordTypes should have a 'NEWLINE' value.");
        assertNotNull(WordTypes.SPACE, "WordTypes should have a 'SPACE' value.");
        assertNotNull(WordTypes.MARKUP, "WordTypes should have a 'MARKUP' value.");
        assertNotNull(WordTypes.NOTASCI, "WordTypes should have a 'NOTASCI' value.");
        assertNotNull(WordTypes.NUMBER, "WordTypes should have a 'NUMBER' value.");
    }

    // If there are any methods within the WordTypes enum, you can test them here.
}
