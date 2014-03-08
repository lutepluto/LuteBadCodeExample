/**
 * 
 */
package com.demo2do.core.web.resolver;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

/**
 * @author Downpour
 */
public class PageArgumentResolver extends ModelAttributeMethodProcessor {

	/**
	 * @param annotationNotRequired
	 */
	public PageArgumentResolver(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#supportsParameter(org.springframework.core.MethodParameter)
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(Page.class);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#createAttribute(java.lang.String, org.springframework.core.MethodParameter, org.springframework.web.bind.support.WebDataBinderFactory, org.springframework.web.context.request.NativeWebRequest)
	 */
	protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
		String currentPage = request.getParameter("page");

		if (StringUtils.isEmpty(currentPage)) {
			currentPage = "1";
		}

		Page page = new Page(Integer.valueOf(currentPage).intValue());
		return page;
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
