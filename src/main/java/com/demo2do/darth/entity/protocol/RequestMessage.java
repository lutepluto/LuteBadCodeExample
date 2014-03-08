/**
 * 
 */
package com.demo2do.darth.entity.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Downpour
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestMessage extends Message {

	@XmlElement(name = "MsgId")
	protected Long id;

	@XmlElement(name = "Content")
	protected String content;
	
	@XmlElement(name = "PicUrl")
	protected String pictureUrl;
	
	@XmlElement(name = "Location_X")
	protected Double locationX;
	
	@XmlElement(name = "Location_Y")
	protected Double locationY;
	
	@XmlElement(name = "Scale")
	protected Integer scale;
	
	@XmlElement(name = "Label")
	protected String label;
	
	@XmlElement(name = "Title")
	protected String title;
	
	@XmlElement(name = "Description")
	protected String description;
	
	@XmlElement(name = "Url")
	protected String url;
	
	@XmlElement(name = "Event")
	protected String event;
	
	@XmlElement(name = "EventKey")
	protected String eventKey;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * @param pictureUrl the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	/**
	 * @return the locationX
	 */
	public Double getLocationX() {
		return locationX;
	}

	/**
	 * @param locationX the locationX to set
	 */
	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	/**
	 * @return the locationY
	 */
	public Double getLocationY() {
		return locationY;
	}

	/**
	 * @param locationY the locationY to set
	 */
	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	/**
	 * @return the scale
	 */
	public Integer getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * @param eventKey the eventKey to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
