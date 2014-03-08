/**
 * 
 */
package com.demo2do.darth.entity.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.weixin.Account;

/**
 * @author Downpour
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Material {

	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	private MaterialType type;
	
	@ManyToOne(optional=false, cascade=CascadeType.ALL)
	private Account account;
	
	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReplyRule> replyRules;
	
	/**
	 * The default constructor
	 */
	public Material() {
		
	}
	
	public Material initialize(Account account, MaterialType type) {
		this.account = account;
		this.type = type;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the type
	 */
	public MaterialType getType() {
		return type;
	}
	
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * @return the replyRules
	 */
	public List<ReplyRule> getReplyRules() {
		return replyRules;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MaterialType type) {
		this.type = type;
	}
	
	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
	/**
	 * @param replyRules the replyRules to set
	 */
	public void setReplyRules(List<ReplyRule> replyRules) {
		this.replyRules = replyRules;
	}
	
}
