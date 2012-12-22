package pl.edu.agh.bo.knabees.communication;

public class FileParsingException extends RuntimeException {
	private static final long serialVersionUID = -3933316411204927069L;

	public FileParsingException() {
		super("Incorrect input file format");
	}
}
