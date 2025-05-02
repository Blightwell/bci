package cl.bci.user.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handle validation errors.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		HttpHeaders headers = new HttpHeaders();

		List<String> errors = ex.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", errors);

		return new ResponseEntity<>(body, headers, status);
	}

	/**
	 * Handle exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(UserException.class)
	protected ResponseEntity<Object> handleException(UserException ex) {
		HttpStatus status = ex.getStatus();

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", ex.getMessage());

		return ResponseEntity.status(status).body(body);
	}

}
