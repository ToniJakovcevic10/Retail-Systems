package hrvatski_telekom.hr.interview_assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ShoppingCartExceptions extends RuntimeException{

	public ShoppingCartExceptions(String message) {
		super(message);
	}
	
	public ShoppingCartExceptions(String message, Throwable cause) {
		super(message, cause);
	}
}

