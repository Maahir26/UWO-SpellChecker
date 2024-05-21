package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.Admin;
import SpellcheckerClasses.Dictionary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminTest {


    Admin admin;
    Dictionary dictionary;

    @BeforeEach
    void setUp() {
        admin = new Admin(true);
        dictionary = new Dictionary();
    }

    @AfterEach
    void tearDown() {
        // Perform cleanup if necessary
    }

    @Test
    @DisplayName("Test importWords with valid file")
    void testImportWordsWithValidFile() {
        // Assuming 'words.txt' is a valid resource file in the resources folder
        String testFilePath = "src/main/resources/words.txt";

        // Mocking an InputStream to simulate reading from a file
        String mockFileContent = "word1 \n word2 \n word3";
        InputStream originalIn = System.in; // backup System.in to restore it later
        System.setIn(new ByteArrayInputStream(mockFileContent.getBytes()));

        Dictionary result = admin.importWords(testFilePath);

        System.setIn(originalIn); // Restore original System.in

        assertTrue(result.isInDictionary("word1"), "Dictionary should contain 'word1'");
        assertTrue(result.isInDictionary("word2"), "Dictionary should contain 'word2'");
        assertTrue(result.isInDictionary("word3"), "Dictionary should contain 'word3'");
    }

    @Test
    @DisplayName("Test importWords with non-existent file")
    void testImportWordsWithNonExistentFile() {
        Dictionary result = null;
        try{
            String testFilePath = "/nonexistent.txt";
            result = admin.importWords(testFilePath);
        }catch (Exception ex){

        }

        // Assuming the dictionary should be empty if the file doesn't exist
        assertTrue(result == null, "Dictionary should be empty");
    }

    @Test
    @DisplayName("Test importWords with empty file")
    void testImportWordsWithEmptyFile() {
        // Simulating an empty file
        String testFilePath = "/empty.txt";
        InputStream originalIn = System.in; // backup System.in to restore it later
        System.setIn(new ByteArrayInputStream("".getBytes()));

        Dictionary result = admin.importWords(testFilePath);

        System.setIn(originalIn); // Restore original System.in

        assertTrue(result.getWords().size() == 0, "Dictionary should be empty for an empty file");
    }
}