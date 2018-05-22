package com.metacube.springapi.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class XmlParseFileException  extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	String message;
	public XmlParseFileException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
  
}
