/**
 * 
 */
package com.demo2do.darth.entity.security;


/**
 * @author Downpour
 */
public enum RoleCode {

	ROLE_ADMIN("admin"),

	ROLE_WEIXIN("weixin");

	private String key;

	/**
	 * 
	 * @param key
	 */
	RoleCode(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String aliasOf(String key) {
		for (RoleCode roleCode : RoleCode.values()) {
			if (roleCode.getKey().equalsIgnoreCase(key)){
				return roleCode.name();
			}
		}
		return null;
	}

}
