package SpellcheckerClasses;

/**
 * Defines the types of elements that can be encountered in a text being processed by the spellchecker.
 * This enumeration categorizes each element based on its nature, such as a word, a newline character, etc.
 */
public enum WordTypes {
    /** Represents a standard word. */
    WORD,

    /** Represents a newline character, indicating the end of a line. */
    NEWLINE,

    /** Represents a space character, typically used to separate words. */
    SPACE,

    /** Represents markup elements, possibly used in formatted texts. */
    MARKUP,

    /** Represents characters that are not part of the ASCII character set. */
    NOTASCI,

    /** Represents numerical values. */
    NUMBER;
}
