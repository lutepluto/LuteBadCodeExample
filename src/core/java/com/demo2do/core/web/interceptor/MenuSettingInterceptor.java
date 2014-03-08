/**
 * 
 */
package com.demo2do.core.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Determine system menu display
 * 
 * @author Downpour
 */
public class MenuSettingInterceptor extends HandlerInterceptorAdapter {
	
	private static final Log logger = LogFactory.getLog(MenuSettingInterceptor.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(logger.isDebugEnabled()) {
			logger.debug("MenuInterceptor - To determine system menu display .. ");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Select Menu and Submenu when the authentication passed
		if (authentication != null && authentication.isAuthenticated()) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;

			// Find menu annotation on class level
			MenuSetting menuSetting = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), MenuSetting.class);

			// Only handle those controllers with menu annotation
			if (menuSetting != null) {
				request.setAttribute("activeMenu", menuSetting.value());
			}

			// Find menu annotation on class level
			menuSetting = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MenuSetting.class);

			// Only handle those methods with menu annotation
			if (menuSetting != null) {
				request.setAttribute("activeSubmenu", menuSetting.value());
			}
		}

		return super.preHandle(request, response, handler);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Select Menu and Submenu when the authentication passed
		if (authentication != null && authentication.isAuthenticated()) {

			Map<String, Object> model = modelAndView.getModel();

			// overwrite active menu if it is overwrite by controller
			if (model.containsKey("activeMenu")) {
				request.setAttribute("activeMenu", model.get("activeMenu"));
			}

			// overwrite active submenu if it is overwrite by controller
			if (model.containsKey("activeSubmenu")) {
				request.setAttribute("activeSubmenu", model.get("activeSubmenu"));
			}

		}

		super.postHandle(request, response, handler, modelAndView);
	}
}
