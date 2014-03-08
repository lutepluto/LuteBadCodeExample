/**
 * 
 */
package com.demo2do.darth.entity.remote;

import java.util.Map;


/**
 * @author Downpour
 */
public class LoginOutput {
	
	private boolean valid;
	
	private String token;
	
	private String url;
	
	private Map<String, String> cookies;
	
	/**
	 * The default constructor
	 */
	public LoginOutput() {
		
	}
	
	/**
	 * 
	 * @param loginResult
	 * @param cookies
	 */
	public void initialize(LoginResult loginResult, Map<String, String> cookies) {
		this.valid = loginResult.isValid();
		this.token = loginResult.getToken();
		this.url = loginResult.getErrMsg();
		this.cookies = cookies;
	}
	
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the cookies
	 */
	public Map<String, String> getCookies() {
		return cookies;
	}
	
	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
}
