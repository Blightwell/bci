package cl.bci.user.exception;

import org.springframework.http.HttpStatus;

/**
 * The Class UserException.
 */
public class UserException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2288737189044243046L;

	/** The status. */
	private final HttpStatus status;

	/**
	 * Instantiates a new user exception.
	 *
	 * @param status the status
	 * @param error the error
	 */
	public UserException(HttpStatus status, String error) {
		super(error);
		this.status = status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

}
