/**
 * 
 */
package com.demo2do.core.web.resolver;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

/**
 * @author Downpour
 */
public class SecurityArgumentResolver extends ModelAttributeMethodProcessor {

	/**
	 * @param annotationNotRequired
	 */
	public SecurityArgumentResolver(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#supportsParameter(org.springframework.core.MethodParameter)
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Secure.class);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#createAttribute(java.lang.String, org.springframework.core.MethodParameter, org.springframework.web.bind.support.WebDataBinderFactory, org.springframework.web.context.request.NativeWebRequest)
	 */
	protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

		// Get authentication from SecurityContextHolder
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			
			Secure secure = parameter.getParameterAnnotation(Secure.class);
			
			if(StringUtils.isEmpty(secure.property())) {
				return authentication.getPrincipal();
			} else {
				
				try {
	                BeanWrapperImpl wrapper = new BeanWrapperImpl(authentication.getPrincipal());
	                return wrapper.getPropertyValue(secure.property());
	            } catch (BeansException e) {
	            	e.printStackTrace();
	            }
			}
			
		}

		return super.createAttribute(attributeName, parameter, binderFactory, request);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#bindRequestParameters(org.springframework.web.bind.WebDataBinder, org.springframework.web.context.request.NativeWebRequest)
	 */
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}

}
