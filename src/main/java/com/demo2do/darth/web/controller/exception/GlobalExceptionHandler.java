package com.demo2do.darth.web.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.InfoMessage;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public String handleBusinessException(BusinessException ex, HttpServletRequest request) {
		
		if(ex.getMessage() != null) {
			request.setAttribute("errorMessage", ex.getMessage());
		}
		
		
		
		return ex.getTargetURL();
		
	}
}
