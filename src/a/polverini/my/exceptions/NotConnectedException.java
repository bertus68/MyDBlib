package a.polverini.my.exceptions;

public class NotConnectedException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotConnectedException() {
		super("not connected!");
	}
	
}