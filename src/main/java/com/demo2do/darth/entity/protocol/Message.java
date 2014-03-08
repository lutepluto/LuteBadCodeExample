/**
 * 
 */
package com.demo2do.darth.entity.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * The base class of Message
 * 
 * @author Downpour
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Message {

	@XmlElement(name = "FromUserName")
	protected String from;

	@XmlElement(name = "ToUserName")
	protected String to;

	@XmlElement(name = "CreateTime")
	protected Long createTime;

	@XmlElement(name = "MsgType")
	protected String type;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
