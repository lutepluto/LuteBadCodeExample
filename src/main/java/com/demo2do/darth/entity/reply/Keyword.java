/**
 * 
 */
package com.demo2do.darth.entity.reply;

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
@Table(name = "keyword")
public class Keyword {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ReplyRule replyRule;
	
	/**
	 * The default constructor
	 */
	public Keyword() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the replyRule
	 */
	public ReplyRule getReplyRule() {
		return replyRule;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param replyRule the replyRule to set
	 */
	public void setReplyRule(ReplyRule replyRule) {
		this.replyRule = replyRule;
	}

}
