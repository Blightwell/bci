package cl.bci.user.utils;

import org.springframework.http.HttpStatus;

import cl.bci.user.exception.UserException;

/**
 * The Class UserExceptionUtil.
 */
public class UserExceptionUtil {

	/**
	 * Instantiates a new user exception util.
	 */
	private UserExceptionUtil() {
		// do nothing. . .
	}

	/** The Constant USER_NOT_FOUND. */
	public static final UserException USER_NOT_FOUND;

	/** The Constant USER_FOUND. */
	public static final UserException USER_FOUND;

	/** The Constant INVALID_PASSWORD_FORMAT. */
	public static final UserException INVALID_PASSWORD_FORMAT;

	static {
		USER_NOT_FOUND = new UserException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		USER_FOUND = new UserException(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
		INVALID_PASSWORD_FORMAT = new UserException(HttpStatus.BAD_REQUEST, "La contraseña no cumple con el formato requerido.");
	}
}
