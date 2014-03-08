/**
 * 
 */
package com.demo2do.darth.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * @author Downpour
 */
public class PostLogoutHandler extends SimpleUrlLogoutSuccessHandler {
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		if(authentication != null) {
			
			// TODO record logout time here
			
		}
		
		super.onLogoutSuccess(request, response, authentication);
	}

}
