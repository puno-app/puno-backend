package app.puno.backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

	public record Success(String success) {}
	public record Failure(String fail) {}

	public static ResponseEntity<?> ok(String message) {
		return ResponseEntity.ok(new Success(message));
	}

	public static ResponseEntity<?> fail(String fail, HttpStatus status) {
		return ResponseEntity.status(status).body(new Failure(fail));
	}

}
