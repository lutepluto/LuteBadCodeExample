/**
 * 
 */
package com.demo2do.darth.entity.material;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Downpour
 */
@Entity
@Table(name = "material_text")
@PrimaryKeyJoinColumn(name = "id")
public class TextMaterial extends Material {

	@Type(type = "text")
	private String content;
	
	/**
	 * The default constructor
	 */
	public TextMaterial() {
		
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
	 * @param content
	 */
	public void editContent(String content) {
		this.content = content;
	}
	
}
