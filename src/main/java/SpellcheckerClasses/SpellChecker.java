package SpellcheckerClasses;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core spell checker functionality in the spell checker application.
 * This class is responsible for managing documents, dictionaries, and performing spell check operations.
 */
public class SpellChecker {
    public Dictionary globalDictionary; // Placeholder, should be a specific dictionary type
    public User user;
    private List<Document> documents;
    public Suggestions suggestions;

    /**
     * Constructs a default SpellChecker instance.
     */
    public SpellChecker() {
        globalDictionary = new Dictionary();
        user = new User(true);
        documents = new ArrayList<>();
    }

    /**
     * Constructs a SpellChecker instance specifying if the user is an admin.
     *
     * @param admin A boolean value indicating if the user is an admin.
     */
    public SpellChecker(Boolean admin) {
        globalDictionary = new Dictionary();
        user = new User(admin);
        documents = new ArrayList<>();
    }

    /**
     * Adds a document to the spell checker and checks it for spelling mistakes.
     *
     * @param doc The document to be added.
     */
    public void addDocument(Document doc) {
        // Check for spelling mistakes
        checkForSpellingMistakes(doc);
        documents.add(doc);
    }

    /**
     * Deletes a document from the spell checker.
     *
     * @param doc The document to be deleted.
     */
    public void deleteDocument(Document doc) {
        documents.remove(doc);
    }

    // Method to read from a config file which will be stored dictionaries and stuff
    public void readToPreviousStateFile() {
        // TODO: Implementation goes here

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/state/previousState.xml"))) {
            writer.write("<documents>\n");

            for (Document doc : documents) {
                writer.write(String.format("\t<document name=\"%s\" path=\"%s\">\n", doc.getName(), doc.getPath()));
                writer.write("\t\t<words>\n");

                for (Text word : doc.getAllWords()) {
                    writer.write(String.format("\t\t\t<word order=\"%d\" text=\"%s\" type=\"%s\" hasSpellingMistake=\"%b\" ignoreSpellingMistake=\"%b\"/>\n",
                            word.getOrder(), word.getText(), word.getType(), word.getHasSpellingMistake(), word.getIgnoreSpellingMistake()));
                }

                writer.write("\t\t</words>\n");
                writer.write("\t</document>\n");
            }

            writer.write("</documents>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readFromPreviousStateFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/state/previousState.xml"))) {
            String line;
            Document currentDoc = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("<document ")) {
                    String name = extractAttribute(line, "name");
                    String path = extractAttribute(line, "path");
                    currentDoc = new Document(name, path, true);
                } else if (line.startsWith("</document>")) {
                    documents.add(currentDoc);
                    currentDoc = null;
                } else if (line.startsWith("<word ") && currentDoc != null) {
                    int order = Integer.parseInt(extractAttribute(line, "order"));
                    WordTypes type = getWordTypeFromAttribute(line);
                    String text;
                    if (type == WordTypes.NEWLINE) {
                        text = "\n"; // Assign the newline character
                    } else {
                        text = extractAttribute(line, "text");
                    }
                    boolean hasSpellingMistake = Boolean.parseBoolean(extractAttribute(line, "hasSpellingMistake"));
                    boolean ignoreSpellingMistake = Boolean.parseBoolean(extractAttribute(line, "ignoreSpellingMistake"));

                    Text word = new Text(text, type, order);
                    word.setHasSpellingMistake(hasSpellingMistake);
                    word.setIgnoreSpellingMistake(ignoreSpellingMistake);
                    currentDoc.addToDecompiledWords(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractAttribute(String line, String attributeName) {
        String prefix = attributeName + "=\"";
        int startIndex = line.indexOf(prefix) + prefix.length();
        int endIndex = line.indexOf("\"", startIndex);
        if (startIndex > -1 && endIndex > -1) {
            return line.substring(startIndex, endIndex);
        }
        return ""; // Attribute not found
    }

    private WordTypes getWordTypeFromAttribute(String line) {
        String typeStr = extractAttribute(line, "type").toUpperCase();
        try {
            return WordTypes.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            System.err.println("Warning: Unknown WordType '" + typeStr + "' encountered.");
            return null; // or a default value, if you have one
        }
    }


    /**
     * Retrieves a document by its name.
     *
     * @param name The name of the document to retrieve.
     * @return The document if found, null otherwise.
     */
    public Document getDocumentByName(String name) {
        for (Document doc : documents) {
            if (doc.getName() == name)
                return doc;
        }
        return null;
    }

    /**
     * Gets a list of all documents in the spell checker.
     *
     * @return A list of documents.
     */
    public List<Document> getDocuments() {
        return this.documents;
    }

    /**
     * Gets the global dictionary used by the spell checker.
     */
    public Dictionary getGlobalDictionary() {
        return this.globalDictionary;
    }

    /**
     * Converts the first letter of the given word to lower case.
     *
     * @return Given word with first letter convert to lower case.
     */
    public String toLowerFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            // Return the original word if it's null or empty
            return word;
        }

        String first = word.substring(0, 1);
        String firstinLower = first.toLowerCase();
        String middle = word.substring(1);

        return firstinLower + middle;
    }


    /**
     * Sets the global dictionary used by the spell checker.
     *
     * @param globalDic The global dictionary to be set.
     */
    public void setGlobalDictionary(Dictionary globalDic) {
        suggestions = new Suggestions(globalDic);
        this.globalDictionary = globalDic;
    }

    /**
     * Checks a document for spelling mistakes.
     *
     * @param doc The document to check for spelling mistakes.
     */
    public void checkForSpellingMistakes(Document doc) {
        doc.metrics.resetNumberOfMispelledWords();
        for (Text text : doc.getAllWords()) {
            if (text.getType() == WordTypes.WORD) {
                if (this.getGlobalDictionary().isInDictionary(toLowerFirstLetter(text.getText()))
                        || user.getPrivateDictionary().isInDictionary(text.getText())) {
                    text.setHasSpellingMistake(false);
                } else {
                    if (text.getIgnoreSpellingMistake() == false) {
                        text.setHasSpellingMistake(true);
                        doc.metrics.incrementNumberOfMisspelledWords();
                    }
                }
            }
        }
    }

    /**
     * Reconfigures spelling mistakes for all documents in the spell checker.
     */
    public void reconfigureSpellingMistakes() {
        for (Document doc : documents) {
            checkForSpellingMistakes(doc);
        }
    }

}