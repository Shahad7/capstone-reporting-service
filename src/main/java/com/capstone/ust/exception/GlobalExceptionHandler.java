package com.capstone.ust.exception;

import org.springframework.http.HttpStatus;
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

	@ExceptionHandler(CurrentMonthRecordAlreadyExists.class)
	public ResponseEntity<?> handleCurrentMonthRecordAlreadyExists(CurrentMonthRecordAlreadyExists ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> GeneralException(Exception ex){
		return ResponseEntity.status(404).body(ex.getMessage());
	}



}
