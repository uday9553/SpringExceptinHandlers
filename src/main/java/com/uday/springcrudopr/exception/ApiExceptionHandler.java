package com.uday.springcrudopr.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;

//import com.netflix.hystrix.exception.HystrixRuntimeException;

import javassist.tools.web.BadHttpRequest;

@RestControllerAdvice
public class ApiExceptionHandler  extends ResponseEntityExceptionHandler {
	

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception) {
		return errorResponse(exception);
	}

	@ExceptionHandler({ JsonParseException.class })
	public ResponseEntity<ErrorResponse> methodArgumentMismatched(MethodArgumentTypeMismatchException exception) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setErrorMessage(exception.getName() + " should be of type "
				+ exception.getRequiredType().getName() + " " + exception.getMostSpecificCause().getMessage());

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorResponse.setReason("Invalid argument type");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ErrorResponse> methodArgumentMismatch(MethodArgumentTypeMismatchException exception) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setErrorMessage(exception.getName() + " should be of type "
				+ exception.getRequiredType().getName() + " " + exception.getMostSpecificCause().getMessage());

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorResponse.setReason("Invalid argument type");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> constrainViolationException(ConstraintViolationException exception) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setErrorMessage(exception.getConstraintViolations().stream()
				.map(c -> c.getPropertyPath() + " " + c.getMessage()).collect(Collectors.joining()));

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorResponse.setReason("Constraint Violation");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ErrorResponse> errorResponse(BaseException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(exception.getMessage());
		errorResponse.setStatus(exception.getHttpStatus().getReasonPhrase());
		errorResponse.setReason(exception.getUserMessage());
		errorResponse.setSystemMessage(exception.getSystemMessage());
		return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
	}

//	@ExceptionHandler({ InvalidDataAccessResourceUsageException.class })
//	public ResponseEntity<ErrorResponse> sqlException(InvalidDataAccessResourceUsageException exception) {
//		ErrorResponse errorResponse = new ErrorResponse();
//		errorResponse.setErrorMessage(exception.getMessage());
//		errorResponse.setStatus(exception.getMostSpecificCause().getMessage());
//		errorResponse.setReason("Bad request to database");
//		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@ExceptionHandler({ EntityPersistException.class })
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody ErrorResponse persistnaceException(EntityPersistException entityPersistException) {
		return new ErrorResponse(entityPersistException.getUserMessage(), entityPersistException.getSystemMessage(),
				entityPersistException.getHttpStatus().getReasonPhrase());
	}

	@ExceptionHandler({ HttpClientErrorException.class })
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public @ResponseBody ErrorResponse clientExceptions(HttpClientErrorException exception) {
		String code = HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
		return new ErrorResponse(exception.getResponseBodyAsString(), code);
	}
	
//	@ExceptionHandler({ HystrixRuntimeException.class })
//	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
//	public @ResponseBody ErrorResponse hystrixException(HystrixRuntimeException exception) {
//		return new ErrorResponse("LSSI Service not available, please try later - " + exception.getFailureType(), HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
//	}

	

	@ExceptionHandler({ BadHttpRequest.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse badRequestException(BadHttpRequest exception) {
		String code = HttpStatus.BAD_REQUEST.getReasonPhrase();
		return new ErrorResponse(exception.getLocalizedMessage(), code);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println(ex.getLocalizedMessage());
		String code = HttpStatus.BAD_REQUEST.getReasonPhrase();
		List<ErrorResponse> errorList = new ArrayList<>(1);
		Map<String, String> fieldByMessage = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
		if (!fieldByMessage.entrySet().isEmpty()) {
			//return ResponseEntity.badRequest().body(fieldByMessage);
			errorList.add(new ErrorResponse(fieldByMessage.toString(),ex.getMessage(), code));
			return ResponseEntity.badRequest().body(errorList);
		} else {
			
			errorList.add(new ErrorResponse(ex.getLocalizedMessage(), code));
			return ResponseEntity.badRequest().body(errorList);
		}
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.getReasonPhrase());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.getReasonPhrase());
		return ResponseEntity.badRequest().body(errorResponse);
	}
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse internalError(Exception exception) {
		System.out.println("internalserver");
		String code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		return new ErrorResponse(exception.getLocalizedMessage(), code);
	}
}
