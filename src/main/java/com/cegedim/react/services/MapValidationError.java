package com.cegedim.react.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationError {
	
	public ResponseEntity<Map<String, String>> validate(BindingResult bindingResult) {
		Map<String, String> errorMap= new HashMap<>();
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			//assign field property and default message property to map json object
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());	
		}
		return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);	//return new Map json object of key/value pairs
	}
}
