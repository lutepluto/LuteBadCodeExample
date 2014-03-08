/**
 * 
 */
package com.demo2do.darth.entity.weixin;

/**
 * @author Downpour
 */
public enum AccountType {

	SUBSCRIBE("subscribe"),

	SERVICE("service");

	private String code;

	AccountType(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
}
