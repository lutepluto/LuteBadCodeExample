/**
 * 
 */
package com.demo2do.darth.entity.stat;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Downpour
 */
@Entity
@Table(name = "login_session")
public class LoginSession {

	@Id
	@GeneratedValue
	private Long id;

	private String account;

	private Date loginTime;

	private Date logoutTime;

	/**
	 * The default constructor
	 */
	public LoginSession() {

	}

	/**
	 * 
	 * @param account
	 * @param loginTime
	 */
	public LoginSession(String account, Date loginTime) {
		this.account = account;
		this.loginTime = new Date();
	}
	
	/**
	 * 
	 * @return
	 */
	public LoginSession logout() {
		this.logoutTime = new Date();
		return this;
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
	public String getAccount() {
		return account;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @return the logoutTime
	 */
	public Date getLogoutTime() {
		return logoutTime;
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
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @param logoutTime
	 *            the logoutTime to set
	 */
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
