/**
 * 
 */
package com.demo2do.darth.entity.app.vote;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Downpour
 */
@Entity
@Table(name = "vote_item")
public class Item {

	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Vote vote;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the vote
	 */
	public Vote getVote() {
		return vote;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param vote the vote to set
	 */
	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	
}
