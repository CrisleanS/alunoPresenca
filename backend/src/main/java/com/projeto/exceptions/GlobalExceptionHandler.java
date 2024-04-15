package com.projeto.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {

		String reason = "Unknow";
		if (e instanceof ResponseStatusException) {
			ResponseStatusException ex = (ResponseStatusException) e;
			reason = ex.getReason() != null ? ex.getReason() : "Unknow";
		}

		for (StackTraceElement element : e.getStackTrace()) {

			String errorMessage = String.format("%s in %s (Line:%d) \n\nDue: %s",
					e.getClass().getSimpleName(), element.getFileName(), element.getLineNumber(), reason);
			return ResponseEntity.badRequest().body(errorMessage);

		}

		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
