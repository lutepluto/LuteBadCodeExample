package com.demo2do.darth.web.controller.exception;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
	
	private String message;

	private String targetURL;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, String targetURL) {
		super(message);
		this.targetURL = targetURL;
	}
	
	public String getTargetURL() {
		return this.targetURL;
	}

}
