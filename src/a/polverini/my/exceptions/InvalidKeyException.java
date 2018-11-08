package a.polverini.my.exceptions;

public class InvalidKeyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidKeyException(String key) {
		super(String.format("invalid key %s", key));
	}
	
}