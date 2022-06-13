package com.cts.SalaryManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.cts.SalaryManagement.Message.ResponseMessage;

@ControllerAdvice
public class FileUploadException {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException e) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
	}

}
