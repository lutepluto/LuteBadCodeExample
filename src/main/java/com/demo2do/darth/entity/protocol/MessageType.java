/**
 * 
 */
package com.demo2do.darth.entity.protocol;

/**
 * @author Downpour
 */
public enum MessageType {

	TEXT("text"),
	
	IMAGE("image"),
	
	LINK("link"),
	
	LOCATION("location"),
	
	EVENT("event"),

	MUSIC("music"),

	NEWS("news");

	private String key;

	/**
	 * The default constructor
	 */
	MessageType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

}
