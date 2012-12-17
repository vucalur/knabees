package pl.edu.agh.bo.knabees.communication;

public class FileParsingException extends RuntimeException {
	public FileParsingException() {
		super("Incorrect input file format");
	}
}
