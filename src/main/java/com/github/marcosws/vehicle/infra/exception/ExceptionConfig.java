package com.github.marcosws.vehicle.infra.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class ExceptionConfig  extends ResponseEntityExceptionHandler{

	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> errorNotFound(Exception e){
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Object> errorBatRequest(Exception e) {
		return ResponseEntity.badRequest().build();
	}

    @ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Object> accessDenied() {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso negado"));
	}
	
}

class Error {
	public String error;

	public Error(String error) {
		this.error = error;
	}
}
