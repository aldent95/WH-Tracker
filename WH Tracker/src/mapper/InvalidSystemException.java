package mapper;

/**
 * Custom error class for handling errors where a valid system could not be
 * found
 * 
 * @author Alex
 *
 */
@SuppressWarnings("serial")
public class InvalidSystemException extends Exception {
	public InvalidSystemException(String msg) {
		super(msg);
	}
}
