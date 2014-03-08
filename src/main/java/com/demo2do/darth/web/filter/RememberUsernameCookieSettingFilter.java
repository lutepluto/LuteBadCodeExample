/**
 * 
 */
package com.demo2do.darth.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.demo2do.darth.Constant;

/**
 * @author Downpour
 */
public class RememberUsernameCookieSettingFilter extends OncePerRequestFilter {
	
	private static final int DEFAULT_COOKIE_MAX_AGE = 60 * 60 * 24 * 60;
	
	private static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	
	private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
	
	private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
	
	private int maxAge = DEFAULT_COOKIE_MAX_AGE;

	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	
		if (!requiresAuthentication(request, response)) {
			filterChain.doFilter(request, response);
            return;
        }
		
		if (logger.isDebugEnabled()) {
            logger.debug("Starting RememberUsernameCookieSettingFilter");
        }
		
		Cookie cookie = WebUtils.getCookie(request, Constant.REMEMBER_ACCT);
		
		if(this.needRememberUsername(request)) {
			
			String username = obtainUsername(request);
			
			if(cookie != null) {
				cookie.setValue(username);
			} else {
				cookie = new Cookie(Constant.REMEMBER_ACCT, username);
				cookie.setMaxAge(this.maxAge);
			}
			
			response.addCookie(cookie);
			
		} else {
			
			if(cookie != null) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			
		}
		
		
		filterChain.doFilter(request, response);
		
	}
	
	/**
	 * @param filterProcessesUrl the filterProcessesUrl to set
	 */
	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	/**
	 * @param usernameParameter the usernameParameter to set
	 */
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}
	
	/**
	 * @param maxAge the maxAge to set
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	
	/**
     * Indicates whether this filter should attempt to process a login request for the current invocation.
     * <p>
     * It strips any parameters from the "path" section of the request URL (such
     * as the jsessionid parameter in
     * <em>http://host/myapp/index.html;jsessionid=blah</em>) before matching
     * against the <code>filterProcessesUrl</code> property.
     * <p>
     * Subclasses may override for special requirements, such as Tapestry integration.
     *
     * @return <code>true</code> if the filter should attempt authentication, <code>false</code> otherwise.
     */
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }
	
	/**
     * Enables subclasses to override the composition of the username, such as by including additional values
     * and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }
	
    /**
     * 
     * @param request
     * @return
     */
    protected boolean needRememberUsername(HttpServletRequest request) {
    	return request.getParameter("j_remember_acct") != null;
    }
}
