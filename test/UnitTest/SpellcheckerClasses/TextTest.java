package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Text;
import SpellcheckerClasses.WordTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextTest {

    private Text text;

    @BeforeEach
    void setUp() {
        text = new Text("example", WordTypes.WORD, 1);
    }

    @Test
    void testGetText() {
        assertEquals("example", text.getText(), "getText should return the correct text.");
    }

    @Test
    void testSetText() {
        text.setText("changed");
        assertEquals("changed", text.getText(), "setText should update the text.");
    }

    @Test
    void testGetType() {
        assertEquals(WordTypes.WORD, text.getType(), "getType should return the correct type.");
    }

    @Test
    void testGetOrder() {
        assertEquals(1, text.getOrder(), "getOrder should return the correct order.");
    }

    @Test
    void testGetHasSpellingMistake() {
        assertFalse(text.getHasSpellingMistake(), "Initially, getHasSpellingMistake should return false.");
    }

    @Test
    void testSetHasSpellingMistake() {
        text.setHasSpellingMistake(true);
        assertTrue(text.getHasSpellingMistake(), "setHasSpellingMistake should update the spelling mistake status.");
    }

    @Test
    void testGetIgnoreSpellingMistake() {
        assertFalse(text.getIgnoreSpellingMistake(), "Initially, getIgnoreSpellingMistake should return false.");
    }

    @Test
    void testSetIgnoreSpellingMistake() {
        text.setIgnoreSpellingMistake(true);
        assertTrue(text.getIgnoreSpellingMistake(), "setIgnoreSpellingMistake should update the ignore mistake status.");
    }
}
