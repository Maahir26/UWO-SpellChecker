package IntegrationTests;
import SpellcheckerClasses.Document;
import SpellcheckerClasses.SpellChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentProcessingAndMetricsTest {
    @Test
    public void testDocumentProcessingAndMetrics() {
        SpellChecker spellChecker = new SpellChecker();
        Document document = new Document("file.txt", "");

        spellChecker.addDocument(document);
        spellChecker.getDocumentByName("file.txt").metrics.printMetrics();

        // Assuming getMetrics() returns the metrics of the document
        assertNotNull(spellChecker.getDocumentByName("file.txt").metrics.getNumberOfCharacters());
        // Add more specific assertions based on expected metrics
    }

}
