package a.polverini.my.exceptions;

public class UnexpectedTypeException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnexpectedTypeException(String name, String type) {
		super(String.format("unexpected %s %s", name, type));
	}
	
}