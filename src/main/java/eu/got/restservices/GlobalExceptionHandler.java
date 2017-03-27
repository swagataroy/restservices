package eu.got.restservices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorDetail> handle(FileNotFoundException rnfe) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(rnfe.getMessage());
		errorDetail.setDeveloperMessage(rnfe.getClass().getName());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
	}
	
	
	/*
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handle(FileNotFoundException
			rnfe, HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    //If you don't flush the response it goes into a loop
		response.flushBuffer();
		
		logger.info("Inside GlobalExceptionHandler FileNotFoundException" );
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(rnfe.getMessage());
		errorDetail.setDeveloperMessage(rnfe.getClass().getName());

		return new ResponseEntity<>(errorDetail, headers, HttpStatus.NOT_FOUND);
		//return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
	
*/

	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<?> handle(IOException ex, HttpServletResponse response) throws IOException {
//		HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Inside GlobalExceptionHandler IOException" );
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass().getName());

		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
	
   

}
