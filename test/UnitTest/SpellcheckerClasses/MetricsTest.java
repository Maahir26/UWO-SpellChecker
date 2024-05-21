package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {

    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @Test
    void incrementAndVerifyNumberOfCharacters() {
        metrics.incrementNumberOfCharacters(5);
        assertEquals(5, metrics.getNumberOfCharacters(), "Number of characters should be incremented by 5.");
    }

    @Test
    void incrementAndVerifyNumberOfLines() {
        metrics.incrementNumberOfLines();
        assertEquals(1, metrics.getNumberOfLines(), "Number of lines should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfWords() {
        metrics.incrementNumberOfWords();
        assertEquals(1, metrics.getNumberOfWords(), "Number of words should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfMisspelledWords() {
        metrics.incrementNumberOfMisspelledWords();
        assertEquals(1, metrics.getNumberOfMisspelledWords(), "Number of misspelled words should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfDoubleWords() {
        metrics.incrementNumberOfDoubleWords();
        assertEquals(1, metrics.getNumberOfDoubleWords(), "Number of double words should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfWordsFixedByManual() {
        metrics.incrementNumberOfWordsFixedByManual();
        assertEquals(1, metrics.getNumberOfWordsFixedByManual(), "Number of words fixed manually should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfWordsFixedByMisspelling() {
        metrics.incrementNumberOfWordsFixedByMisspelling();
        assertEquals(1, metrics.getNumberOfWordsFixedByMisspelling(), "Number of misspelled words fixed should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfWordsFixedByDouble() {
        metrics.incrementNumberOfWordsFixedByDouble();
        assertEquals(1, metrics.getNumberOfWordsFixedByDouble(), "Number of double words fixed should be incremented by 1.");
    }

    @Test
    void incrementAndVerifyNumberOfWordsDeleted() {
        metrics.incrementNumberOfWordsDeleted();
        assertEquals(1, metrics.getNumberOfWordsDeleted(), "Number of words deleted should be incremented by 1.");
    }
}
