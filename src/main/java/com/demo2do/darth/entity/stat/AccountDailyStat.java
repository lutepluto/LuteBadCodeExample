/**
 * 
 */
package com.demo2do.darth.entity.stat;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.darth.entity.weixin.Account;

/**
 * @author Downpour
 */
@Entity
@Table(name = "account_daily_stat")
public class AccountDailyStat {

	@Id
	@GeneratedValue
	private Long id;
	
	@Type(type = "date")
	private Date statDate;
	
	private int fansDailyIncrement;
	
	private int fansDailyDecrement;
	
	private int messageFansDailyIncrement;
	
	private int messageDailyIncrement;
	
	@ManyToOne(optional = false)
	private Account account;
	
	/**
	 * The default constructor
	 */
	public AccountDailyStat() {
		
	}
	
	/**
	 * Create empty record
	 * 
	 * @param statDate
	 * @param account
	 */
	public AccountDailyStat(Date statDate, Account account) {
		this.statDate = statDate;
		this.account = account;
		this.fansDailyIncrement = 0;
		this.fansDailyDecrement = 0;
		this.messageFansDailyIncrement = 0;
		this.messageDailyIncrement = 0;
	}

	/**
	 * Returns daily net increment of fans
	 * 
	 * @return
	 */
	@Transient
	public int getFansDailyNetIncrement() {
		return this.fansDailyIncrement - this.fansDailyDecrement;
	}
	
	/**
	 * Returns the date that one week before statDate
	 * 
	 * @return
	 */
	@Transient
	public Date getWeekAgo() {
		return DateUtils.addWeeks(this.statDate, -1);
	}
	
	/**
	 * Returns the date that one month before statDate
	 * 
	 * @return
	 */
	@Transient
	public Date getMonthAgo() {
		return DateUtils.addMonths(this.statDate, -1);
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the statDate
	 */
	public Date getStatDate() {
		return statDate;
	}

	/**
	 * @return the fansDailyIncrement
	 */
	public int getFansDailyIncrement() {
		return fansDailyIncrement;
	}

	/**
	 * @return the fansDailyDecrement
	 */
	public int getFansDailyDecrement() {
		return fansDailyDecrement;
	}
	
	/**
	 * @return the messageFansDailyIncrement
	 */
	public int getMessageFansDailyIncrement() {
		return messageFansDailyIncrement;
	}

	/**
	 * @return the messageDailyIncrement
	 */
	public int getMessageDailyIncrement() {
		return messageDailyIncrement;
	}
	
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	/**
	 * @param fansDailyIncrement the fansDailyIncrement to set
	 */
	public void setFansDailyIncrement(int fansDailyIncrement) {
		this.fansDailyIncrement = fansDailyIncrement;
	}

	/**
	 * @param fansDailyDecrement the fansDailyDecrement to set
	 */
	public void setFansDailyDecrement(int fansDailyDecrement) {
		this.fansDailyDecrement = fansDailyDecrement;
	}

	/**
	 * @param messageFansDailyIncrement the messageFansDailyIncrement to set
	 */
	public void setMessageFansDailyIncrement(int messageFansDailyIncrement) {
		this.messageFansDailyIncrement = messageFansDailyIncrement;
	}
	
	/**
	 * @param messageDailyIncrement the messageDailyIncrement to set
	 */
	public void setMessageDailyIncrement(int messageDailyIncrement) {
		this.messageDailyIncrement = messageDailyIncrement;
	}
	
	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
