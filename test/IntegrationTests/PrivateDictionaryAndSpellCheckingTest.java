package IntegrationTests;
import SpellcheckerClasses.Document;
import SpellcheckerClasses.SpellChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrivateDictionaryAndSpellCheckingTest {

    @Test
    public void testPrivateDictionaryManagementAndSpellChecking() {
        SpellChecker spellChecker = new SpellChecker();
        Document document = new Document("file.txt", "");
        spellChecker.addDocument(document);

        spellChecker.user.getPrivateDictionary().add("PiniINTA");
        spellChecker.reconfigureSpellingMistakes();

        assertTrue(spellChecker.user.getPrivateDictionary().isInDictionary("PiniINTA"));
    }

}
