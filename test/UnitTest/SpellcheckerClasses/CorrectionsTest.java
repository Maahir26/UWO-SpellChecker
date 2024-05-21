package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Corrections;
import SpellcheckerClasses.Text;
import SpellcheckerClasses.WordTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class CorrectionsTest {

    private Corrections corrections;
    private List<Text> decompiledWords;
    private Text testWord1, testWord2;

    @BeforeEach
    void setUp() {
        decompiledWords = new ArrayList<>();
        testWord1 = new Text("test", WordTypes.WORD, 1);
        testWord2 = new Text("sample", WordTypes.WORD,2);
        decompiledWords.add(testWord1);
        decompiledWords.add(testWord2);
        corrections = new Corrections(decompiledWords);
    }

    @Test
    void manuallyCorrect() {
        corrections.manuallyCorrect("corrected", testWord1);
        assertEquals("corrected", testWord1.getText(), "The word should be corrected.");
        assertFalse(testWord1.getHasSpellingMistake(), "The word should no longer be marked as a spelling mistake.");
    }

    @Test
    void ignoreRestOfApplication() {
        corrections.ignoreRestOfApplication(testWord1);
        assertTrue(testWord1.getIgnoreSpellingMistake(), "The word should be set to ignore for rest of application.");
        assertFalse(testWord1.getHasSpellingMistake(), "The word should no longer be marked as a spelling mistake.");
    }

    @Test
    void ignoreWord() {
        corrections.ignoreWord(testWord2);
        assertTrue(testWord2.getIgnoreSpellingMistake(), "The word should be set to ignore.");
        assertFalse(testWord2.getHasSpellingMistake(), "The word should no longer be marked as a spelling mistake.");
    }

    @Test
    void deleteWord() {
        corrections.deleteWord(testWord2);
        assertFalse(decompiledWords.contains(testWord2), "The word should be deleted from decompiledWords.");
    }
}
