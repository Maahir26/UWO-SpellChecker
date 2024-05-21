package IntegrationTests;
import SpellcheckerClasses.Document;
import SpellcheckerClasses.SpellChecker;
import org.junit.jupiter.api.Test;

public class DocumentCorrectionsAndFileOperationsTest {

    @Test
    public void testDocumentCorrectionsAndFileOperations() {
        SpellChecker spellChecker = new SpellChecker();
        Document document = new Document("file.txt", "");
        spellChecker.addDocument(document);

        spellChecker.getDocumentByName("file.txt").saveFile();
        spellChecker.deleteDocument(spellChecker.getDocumentByName("file.txt"));

    }

}
