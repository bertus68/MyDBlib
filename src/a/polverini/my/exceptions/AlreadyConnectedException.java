package a.polverini.my.exceptions;

public class AlreadyConnectedException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AlreadyConnectedException() {
		super("already connected!");
	}
	
}