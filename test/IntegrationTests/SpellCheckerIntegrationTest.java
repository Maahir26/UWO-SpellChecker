package IntegrationTests;

import SpellcheckerClasses.Admin;
import SpellcheckerClasses.Dictionary;
import SpellcheckerClasses.SpellChecker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SpellCheckerIntegrationTest {

    @Test
    public void testInitializationAndGlobalDictionaryLoading() {
        Admin admin = Mockito.mock(Admin.class);
        Mockito.when(admin.importWords("/documents/words_alpha.txt"))
                .thenReturn((Dictionary) Arrays.asList("word1", "word2", "word3"));

        SpellChecker spellChecker = new SpellChecker();
        spellChecker.setGlobalDictionary(admin.importWords("/documents/words_alpha.txt"));

        // Assuming getGlobalDictionary() method returns the list of words in the global dictionary
        assertEquals(Arrays.asList("word1", "word2", "word3"), spellChecker.getGlobalDictionary());
    }
}
