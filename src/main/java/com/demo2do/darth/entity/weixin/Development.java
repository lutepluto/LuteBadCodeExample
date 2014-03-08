/**
 * 
 */
package com.demo2do.darth.entity.weixin;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * @author Downpour
 */
@Embeddable
@Access(AccessType.FIELD)
public class Development {

	/** http://weixin.demo2do.com/biolife365 */
	private String url;

	/** biolife365 */
	private String token;

	private String appId;

	private String appSecret;

	/**
	 * The default constructor
	 */
	public Development() {

	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @param appSecret
	 *            the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String createDefaultUrl(String prefixURL, String encodedSuffixURL) {
		
		this.setUrl(prefixURL + encodedSuffixURL);
		return this.getUrl();
		
	}
}
