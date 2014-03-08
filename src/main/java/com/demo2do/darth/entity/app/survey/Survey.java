/**
 * 
 */
package com.demo2do.darth.entity.app.survey;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo2do.darth.entity.app.Status;
import com.demo2do.darth.entity.weixin.Account;

/**
 * @author Downpour
 */
@Entity
@Table(name = "survey")
public class Survey {

	@Id
	@GeneratedValue
	private Long id;
	
	private String topic;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Account account;
	
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	private String createTime;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
