package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    private File file;

    @BeforeEach
    void setUp() {
        // Assuming the file path and name are set correctly
        file = new File("test.txt", "/path/to/");
    }

    @Test
    void getName() {
        assertEquals("test.txt", file.getName(), "File name should be 'test.txt'.");
    }

    @Test
    void getPath() {
        assertEquals("/path/to/", file.getPath(), "File path should be '/path/to/'.");
    }

    @Test
    void setName() {
        file.setName("newName.txt");
        assertEquals("newName.txt", file.getName(), "File name should be updated to 'newName.txt'.");
    }

    @Test
    void setPath() {
        file.setPath("/new/path/");
        assertEquals("/new/path/", file.getPath(), "File path should be updated to '/new/path/'.");
    }

    // Tests for openFile and saveFile would ideally involve mocking the filesystem or using a temporary file.
}
