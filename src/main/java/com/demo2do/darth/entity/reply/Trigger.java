/**
 * 
 */
package com.demo2do.darth.entity.reply;

/**
 * @author Downpour
 */
public enum Trigger {
	
	SUBSCRIBE(true),
	
	KEYWORDS(false),
	
	LBS(false),
	
	DEFAULT(true),
	
	INBOX(true);

	boolean single;
	
	/**
	 * The constructor using fields
	 * 
	 * @param single
	 */
	Trigger(boolean single) {
		this.single = single;
	}

	/**
	 * @return the single
	 */
	public boolean isSingle() {
		return single;
	}
	
	
	
}
