package com.metacube.springapi.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class AttributeValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;
	public AttributeValidationException(String message) {
		this.message = messageExtract(message);
	}
	
	private String messageExtract(String message) {
		int pos1 = message.indexOf("interpolatedMessage");
		int pos2 = message.indexOf("rootBeanClass");
		return "Constraint violation:"+message.substring(pos1, pos2);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
