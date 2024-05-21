package SpellcheckerClasses;

/**
 * Represents a user in the spellchecker system.
 * Each user has a private dictionary, a name, and an admin status.
 */
public class User {
    private boolean isAdmin;
    private PrivateDictionary priavteDictionary;
    private String name;

    /**
     * Constructor to initialize a User object.
     *
     * @param isAdmin Indicates whether the user has admin privileges.
     */
    public User(Boolean isAdmin) {
        priavteDictionary = new PrivateDictionary();
        this.isAdmin = isAdmin;
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to set for this user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the admin status of the user.
     *
     * @return true if the user is an admin, false otherwise.
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin status of the user.
     *
     * @param isAdmin The admin status to set for this user.
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Gets the private dictionary associated with the user.
     *
     * @return The private dictionary of the user.
     */
    public PrivateDictionary getPrivateDictionary() {
        return priavteDictionary;
    }

    // Additional methods or logic related to 'privateDictionary' can be added if needed.
}
