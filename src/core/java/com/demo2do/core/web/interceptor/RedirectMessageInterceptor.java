/**
 * 
 */
package com.demo2do.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * @author Downpour
 */
public class RedirectMessageInterceptor extends HandlerInterceptorAdapter {
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (handler instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) handler;

			// find InfoMessage Annotation
			InfoMessage infoMessage = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), InfoMessage.class);

			if (infoMessage != null) {
				RequestContextUtils.getOutputFlashMap(request).put("infoMessage", infoMessage.value());
			}
		}

		super.postHandle(request, response, handler, modelAndView);
	}
}
