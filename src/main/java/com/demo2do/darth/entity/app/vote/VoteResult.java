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
@Table(name = "vote_result")
public class VoteResult {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Vote vote;
	
	/**
	 * The default constructor
	 */
	public VoteResult() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
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
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @param vote the vote to set
	 */
	public void setVote(Vote vote) {
		this.vote = vote;
	}

}
