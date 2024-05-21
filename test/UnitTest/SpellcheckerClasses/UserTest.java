package UnitTest.SpellcheckerClasses;

import SpellcheckerClasses.PrivateDictionary;
import SpellcheckerClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(true);
    }

    @Test
    void testIsAdmin() {
        assertTrue(user.getIsAdmin(), "getIsAdmin should return true for an admin user.");
    }

    @Test
    void testSetIsAdmin() {
        user.setIsAdmin(false);
        assertFalse(user.getIsAdmin(), "setIsAdmin should update the admin status.");
    }

    @Test
    void testGetName() {
        assertNull(user.getName(), "Initially, getName should return null.");
    }

    @Test
    void testSetName() {
        user.setName("John Doe");
        assertEquals("John Doe", user.getName(), "setName should update the user's name.");
    }

    @Test
    void testGetPrivateDictionary() {
        PrivateDictionary privateDictionary = user.getPrivateDictionary();
        assertNotNull(privateDictionary, "getPrivateDictionary should return a non-null PrivateDictionary object.");
    }

    // Additional tests can be added to test the interactions with the private dictionary if needed
}
