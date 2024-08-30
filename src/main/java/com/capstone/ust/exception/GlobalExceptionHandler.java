package com.capstone.ust.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

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
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation constraint violation : "+ex.getMessage());
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		List<String> errorMessages = new ArrayList<String>();
		ex.getAllErrors().forEach(error->{
			errorMessages.add(error.getDefaultMessage());
		});
		HashMap<String,Object> errorResponse = new HashMap();
		errorResponse.put("message", "Invalid method arguements");
		errorResponse.put("errors",errorMessages);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> GeneralException(Exception ex){
		return ResponseEntity.status(404).body(ex);
	}



}
