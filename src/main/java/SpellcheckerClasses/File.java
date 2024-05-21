package SpellcheckerClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents a file in the spell checker application.
 * This class is responsible for handling file operations such as opening and reading file content.
 */
public class File {
	private String fileName;
	private String filePath;
	protected String fileContent;
	public Metrics metrics;

	/**
	 * Constructs a File instance with the specified name and path.
	 *
	 * @param name The name of the file.
	 * @param path The path where the file is located.
	 */
	public File(String name, String path) {
		fileName = name;
		filePath = path;
		metrics = new Metrics();
		fileContent = openFile();
	}

	/**
	 * Opens the file and reads its content.
	 *
	 * @return The content of the file as a String.
	 */
	public String openFile() {
		String fileFullPath = filePath + fileName;
		String fileContents = "";

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileFullPath));
			String line = reader.readLine();
			metrics.incrementNumberOfLines();
			metrics.incrementNumberOfCharacters(line.length());

			while (line != null) {
				fileContents += line + "\n";
				line = reader.readLine();
				if (line != null) {
					metrics.incrementNumberOfLines();
					metrics.incrementNumberOfCharacters(line.length());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileContents;
	}

	/**
	 * Saves the file with the specified content.
	 *
	 * @param fileContent The new content of the file.
	 */
	public void saveFile(String fileContent) {
		try {
			// Constructs the full path by combining filePath and fileName
			String fullPath = filePath + fileName;

			// Writes the content to the file, overwriting any existing content
			Files.write(Paths.get(fullPath), fileContent.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			// Optionally, handle the exception according to your needs
		}
	}

	/**
	 * Gets the name of the file.
	 *
	 * @return The file name.
	 */
	public String getName() {
		return this.fileName;
	}

	/**
	 * Gets the path of the file.
	 *
	 * @return The file path.
	 */
	public String getPath() { return this.filePath; }
	
	/**
	 * Sets the name of the file.
	 */
	public void setName(String name) {fileName = name;}
	
	/**
	 * Sets the path of the file.
	 */
	public void setPath(String path) {filePath=path;}

    public String getFileContent() { return this.fileContent; }
	// Other methods here
}
