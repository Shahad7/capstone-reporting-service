package com.capstone.ust.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoRecordsFoundForUserException.class)
	public ResponseEntity<?> handleNoRecordsFoundForUserException(NoRecordsFoundForUserException ex){
		return ResponseEntity.status(404).body(ex.getMessage());
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException ex){
		return ResponseEntity.status(404).body(ex.getMessage());
	}

}
