package com.sap.ibso.ato.training.tools.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, String> handleObjectNotFoundException(ObjectNotFoundException ex) {
		logger.warn(ex.getMessage(), ex);
		return convertException(ex);
	}

	@ExceptionHandler(RequestInconsistentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleRequestInconsistentException(RequestInconsistentException ex) {
		logger.warn(ex.getMessage(), ex);
		return convertException(ex);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String, String> handleAllExceptions(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return convertException(ex);
	}

	private Map<String, String> convertException(Exception ex) {
		Map<String, String> map = new HashMap();
		map.put("exception", ex.getClass().getCanonicalName());
		return map;
	}


}
