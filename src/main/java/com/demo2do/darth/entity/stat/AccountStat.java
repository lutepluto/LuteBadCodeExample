/**
 * 
 */
package com.demo2do.darth.entity.stat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.demo2do.darth.entity.weixin.Account;

/**
 * @author Downpour
 */
@Entity
@Table(name = "account_stat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountStat {

	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "account"))
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Account account;

	private int fansNumber;

	private int fansDailyIncrement;
	
	private int fansDailyDecrement;
	
	private int fansWeeklyIncrement;

	private int fansWeeklyDecrement;

	private int fansMonthlyIncrement;

	private int fansMonthlyDecrement;

	private int messageNumber;
	
	private int messageDailyIncrement;

	private int messageWeeklyIncrement;

	private int messageMonthlyIncrement;
	
	private int messageFansDailyIncrement;
	
	private int messageFansWeeklyIncrement;
	
	private int messageFansMonthlyIncrement;
	

	/**
	 * The default constructor
	 */
	public AccountStat() {

	}

	/**
	 * The constructor with corresponding account and fansNumber, all other stat data will be set as 0
	 * 
	 * @param account
	 * @param fansNumber
	 * @return
	 */
	public AccountStat(Account account, int fansNumber) {
		this.account = account;
		this.fansNumber = fansNumber;
		this.fansDailyIncrement = 0;
		this.fansDailyDecrement = 0;
		this.fansWeeklyIncrement = 0;
		this.fansWeeklyDecrement = 0;
		this.fansMonthlyIncrement = 0;
		this.fansMonthlyDecrement = 0;
		this.messageNumber = 0;
		this.messageDailyIncrement = 0;
		this.messageWeeklyIncrement = 0;
		this.messageMonthlyIncrement = 0;
		this.messageFansDailyIncrement = 0;
		this.messageFansWeeklyIncrement = 0;
		this.messageFansMonthlyIncrement = 0;
	}
	
	/**
	 * Calculate AccountStat from AccountDailyStats( today, weekAgo and monthAgo )
	 * 
	 * @param today
	 * @param weekAgo
	 * @param monthAgo
	 * @return
	 */
	public AccountStat calculate(AccountDailyStat today, AccountDailyStat weekAgo, AccountDailyStat monthAgo) {
		this.fansNumber += today.getFansDailyNetIncrement();
		this.fansDailyIncrement += today.getFansDailyIncrement();
		this.fansDailyDecrement += today.getFansDailyDecrement();
		this.fansWeeklyIncrement = this.fansWeeklyIncrement + today.getFansDailyIncrement() - weekAgo.getFansDailyIncrement();
		this.fansWeeklyDecrement = this.fansWeeklyDecrement + today.getFansDailyDecrement() - weekAgo.getFansDailyDecrement();
		this.fansMonthlyIncrement = this.fansMonthlyIncrement + today.getFansDailyIncrement() - monthAgo.getFansDailyIncrement();
		this.fansMonthlyDecrement = this.fansMonthlyDecrement + today.getFansDailyDecrement() - monthAgo.getFansDailyDecrement();
		this.messageNumber += today.getMessageDailyIncrement();
		this.messageDailyIncrement += today.getMessageDailyIncrement();
		this.messageWeeklyIncrement = this.messageWeeklyIncrement + today.getMessageDailyIncrement() - weekAgo.getMessageDailyIncrement();
		this.messageMonthlyIncrement = this.messageMonthlyIncrement + today.getMessageDailyIncrement() - monthAgo.getMessageDailyIncrement();
		this.messageFansDailyIncrement += today.getMessageFansDailyIncrement();
		this.messageFansWeeklyIncrement = this.messageFansWeeklyIncrement + today.getMessageFansDailyIncrement() - weekAgo.getMessageFansDailyIncrement();
		this.messageFansMonthlyIncrement = this.messageFansMonthlyIncrement + today.getMessageFansDailyIncrement() - monthAgo.getMessageFansDailyIncrement();
		return this;
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
	 * Returns daily net increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansDailyNetIncrementTrend() {
		return this.getFansDailyNetIncrement() > 0 ? "up" : (this.getFansDailyNetIncrement() < 0 ? "down" : "right");
	}
	
	/**
	 * Returns daily increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansDailyIncrementTrend() {
		return this.getFansDailyIncrement() > 0 ? "up" : (this.getFansDailyIncrement() < 0 ? "down" : "right");
	}

	/**
	 * Returns daily decrement trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansDailyDecrementTrend() {
		return this.getFansDailyDecrement() > 0 ? "up" : (this.getFansDailyDecrement() < 0 ? "down" : "right");
	}

	/**
	 * Returns weekly net increment of fans
	 * 
	 * @return
	 */
	@Transient
	public int getFansWeeklyNetIncrement() {
		return this.fansWeeklyIncrement - this.fansWeeklyDecrement;
	}

	/**
	 * Returns weekly net increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansWeeklyNetIncrementTrend() {
		return this.getFansWeeklyNetIncrement() > 0 ? "up" : (this.getFansWeeklyNetIncrement() < 0 ? "down" : "right");
	}
	
	/**
	 * Returns weekly increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansWeeklyIncrementTrend() {
		return this.getFansWeeklyIncrement() > 0 ? "up" : (this.getFansWeeklyIncrement() < 0 ? "down" : "right");
	}

	/**
	 * Returns weekly decrement trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansWeeklyDecrementTrend() {
		return this.getFansWeeklyDecrement() > 0 ? "up" : (this.getFansWeeklyDecrement() < 0 ? "down" : "right");
	}
	
	/**
	 * Returns monthly net increment of fans
	 * 
	 * @return
	 */
	@Transient
	public int getFansMonthlyNetIncrement() {
		return this.fansMonthlyIncrement - this.fansMonthlyDecrement;
	}

	/**
	 * Returns monthly net increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansMonthlyNetIncrementTrend() {
		return this.getFansMonthlyNetIncrement() > 0 ? "up" : (this.getFansMonthlyNetIncrement() < 0 ? "down" : "right");
	}
	
	/**
	 * Returns monthly increment trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansMonthlyIncrementTrend() {
		return this.getFansMonthlyIncrement() > 0 ? "up" : (this.getFansMonthlyIncrement() < 0 ? "down" : "right");
	}

	/**
	 * Returns monthly decrement trend
	 * 
	 * @return
	 */
	@Transient
	public String getFansMonthlyDecrementTrend() {
		return this.getFansMonthlyDecrement() > 0 ? "up" : (this.getFansMonthlyDecrement() < 0 ? "down" : "right");
	}
	
	/**
	 * Returns daily message trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageDailyTrend() {
		return this.messageDailyIncrement > 0 ? "up" : "right";
	}
	
	/**
	 * Returns weekly message trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageWeeklyTrend() {
		return this.messageWeeklyIncrement > 0 ? "up" : "right";
	}
	
	/**
	 * Returns monthly message trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageMonthlyTrend() {
		return this.messageMonthlyIncrement > 0 ? "up" : "right";
	}

	/**
	 * Returns daily message fans trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageFansDailyTrend() {
		return this.messageFansDailyIncrement > 0 ? "up" : "right";
	}
	
	/**
	 * Returns weekly message fans trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageFansWeeklyTrend() {
		return this.messageFansWeeklyIncrement > 0 ? "up" : "right";
	}
	
	/**
	 * Returns monthly message fans trend
	 * 
	 * @return
	 */
	@Transient
	public String getMessageFansMonthlyTrend() {
		return this.messageFansMonthlyIncrement > 0 ? "up" : "right";
	}
	
	/**
	 * Returns account name
	 * 
	 * @return
	 */
	@Transient
	public String getAccountName() {
		if(account!=null && account.getProfile()!=null)
			return account.getProfile().getName();
		
		return "";
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return the fansNumber
	 */
	public int getFansNumber() {
		return fansNumber;
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
	 * @return the fansWeeklyIncrement
	 */
	public int getFansWeeklyIncrement() {
		return fansWeeklyIncrement;
	}

	/**
	 * @return the fansWeeklyDecrement
	 */
	public int getFansWeeklyDecrement() {
		return fansWeeklyDecrement;
	}

	/**
	 * @return the fansMonthlyIncrement
	 */
	public int getFansMonthlyIncrement() {
		return fansMonthlyIncrement;
	}

	/**
	 * @return the fansMonthlyDecrement
	 */
	public int getFansMonthlyDecrement() {
		return fansMonthlyDecrement;
	}

	/**
	 * @return the messageNumber
	 */
	public int getMessageNumber() {
		return messageNumber;
	}
	
	/**
	 * @return the messageDailyIncrement
	 */
	public int getMessageDailyIncrement() {
		return messageDailyIncrement;
	}

	/**
	 * @return the messageWeeklyIncrement
	 */
	public int getMessageWeeklyIncrement() {
		return messageWeeklyIncrement;
	}

	/**
	 * @return the messageMonthlyIncrement
	 */
	public int getMessageMonthlyIncrement() {
		return messageMonthlyIncrement;
	}

	/**
	 * @return the messageFansDailyIncrement
	 */
	public int getMessageFansDailyIncrement() {
		return messageFansDailyIncrement;
	}
	
	/**
	 * @return the messageFansWeeklyIncrement
	 */
	public int getMessageFansWeeklyIncrement() {
		return messageFansWeeklyIncrement;
	}
	
	/**
	 * @return the messageFansMonthlyIncrement
	 */
	public int getMessageFansMonthlyIncrement() {
		return messageFansMonthlyIncrement;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @param fansNumber
	 *            the fansNumber to set
	 */
	public void setFansNumber(int fansNumber) {
		this.fansNumber = fansNumber;
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
	 * @param fansWeeklyIncrement
	 *            the fansWeeklyIncrement to set
	 */
	public void setFansWeeklyIncrement(int fansWeeklyIncrement) {
		this.fansWeeklyIncrement = fansWeeklyIncrement;
	}

	/**
	 * @param fansWeeklyDecrement
	 *            the fansWeeklyDecrement to set
	 */
	public void setFansWeeklyDecrement(int fansWeeklyDecrement) {
		this.fansWeeklyDecrement = fansWeeklyDecrement;
	}

	/**
	 * @param fansMonthlyIncrement
	 *            the fansMonthlyIncrement to set
	 */
	public void setFansMonthlyIncrement(int fansMonthlyIncrement) {
		this.fansMonthlyIncrement = fansMonthlyIncrement;
	}

	/**
	 * @param fansMonthlyDecrement
	 *            the fansMonthlyDecrement to set
	 */
	public void setFansMonthlyDecrement(int fansMonthlyDecrement) {
		this.fansMonthlyDecrement = fansMonthlyDecrement;
	}

	/**
	 * @param messageNumber
	 *            the messageNumber to set
	 */
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}
	
	/**
	 * @param messageDailyIncrement the messageDailyIncrement to set
	 */
	public void setMessageDailyIncrement(int messageDailyIncrement) {
		this.messageDailyIncrement = messageDailyIncrement;
	}

	/**
	 * @param messageWeeklyIncrement
	 *            the messageWeeklyIncrement to set
	 */
	public void setMessageWeeklyIncrement(int messageWeeklyIncrement) {
		this.messageWeeklyIncrement = messageWeeklyIncrement;
	}

	/**
	 * @param messageMonthlyIncrement
	 *            the messageMonthlyIncrement to set
	 */
	public void setMessageMonthlyIncrement(int messageMonthlyIncrement) {
		this.messageMonthlyIncrement = messageMonthlyIncrement;
	}

	/**
	 * @param messageFansDailyIncrement the messageFansDailyIncrement to set
	 */
	public void setMessageFansDailyIncrement(int messageFansDailyIncrement) {
		this.messageFansDailyIncrement = messageFansDailyIncrement;
	}
	
	/**
	 * @param messageFansWeeklyIncrement the messageFansWeeklyIncrement to set
	 */
	public void setMessageFansWeeklyIncrement(int messageFansWeeklyIncrement) {
		this.messageFansWeeklyIncrement = messageFansWeeklyIncrement;
	}
	
	/**
	 * @param messageFansMonthlyIncrement the messageFansMonthlyIncrement to set
	 */
	public void setMessageFansMonthlyIncrement(int messageFansMonthlyIncrement) {
		this.messageFansMonthlyIncrement = messageFansMonthlyIncrement;
	}
}
