/**
 * 
 */
package com.demo2do.darth.entity.reply;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo2do.darth.entity.material.Material;
import com.demo2do.darth.entity.weixin.Account;

/**
 * @author Downpour
 */
@Entity
@Table(name = "reply_rule")
public class ReplyRule {

	@Id
	@GeneratedValue
	private Long id;
	
	/** The name of the rule */
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "trigger_source")
	private Trigger trigger;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Account account;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Material material;
	
	@Column(name = "is_active")
	private boolean active;
	
	/**
	 * The default constructor
	 */
	public ReplyRule() {
		
	}

	/**
	 * The constructor using trigger and account
	 * 
	 * @param trigger
	 * @param account
	 */
	public ReplyRule(Trigger trigger, Account account) {
		this.trigger = trigger;
		this.account = account;
		this.active = false;
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
	 * @return the trigger
	 */
	public Trigger getTrigger() {
		return trigger;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * @return the materials
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
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
	 * @param trigger the trigger to set
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
	/**
	 * @param materials the materials to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
