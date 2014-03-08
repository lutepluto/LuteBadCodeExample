/**
 * 
 */
package com.demo2do.darth.entity.message;

import java.util.Date;

/**
 * @author Downpour
 */
public class Dialogue {
	
	private Long id;
	
	private RequestDialogue requestDialogue;
	
	private ResponseDialogue responseDialogue;
	
	private boolean latest;
	
	private Date createTime;
	
	private Date requestTime;
	
	private Date responseTime;
	
	/**
	 * The default constructor
	 */
	public Dialogue() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the requestDialogue
	 */
	public RequestDialogue getRequestDialogue() {
		return requestDialogue;
	}
	
	/**
	 * @return the responseDialogue
	 */
	public ResponseDialogue getResponseDialogue() {
		return responseDialogue;
	}

	/**
	 * @return the latest
	 */
	public boolean isLatest() {
		return latest;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}
	
	/**
	 * @return the responseTime
	 */
	public Date getResponseTime() {
		return responseTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param requestDialogue the requestDialogue to set
	 */
	public void setRequestDialogue(RequestDialogue requestDialogue) {
		this.requestDialogue = requestDialogue;
	}
	
	/**
	 * @param responseDialogue the responseDialogue to set
	 */
	public void setResponseDialogue(ResponseDialogue responseDialogue) {
		this.responseDialogue = responseDialogue;
	}
	
	/**
	 * @param latest the latest to set
	 */
	public void setLatest(boolean latest) {
		this.latest = latest;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	/**
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
}
