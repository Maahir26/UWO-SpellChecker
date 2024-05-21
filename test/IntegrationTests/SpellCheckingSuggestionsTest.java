package IntegrationTests;


import SpellcheckerClasses.Document;
import SpellcheckerClasses.SpellChecker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpellCheckingSuggestionsTest {

    @Test
    public void testSpellCheckingSuggestions() {
        SpellChecker spellChecker = new SpellChecker();
        try {

            Document document = new Document("file.txt", "");
            spellChecker.addDocument(document);
        }catch (Exception ex){

        }


        List<String> suggestions = spellChecker.suggestions.getSuggestions("badd");
        assertNull(suggestions);
        assertTrue(suggestions.isEmpty());
        // More specific assertions based on the expected suggestions
    }
}
