/**
 * 
 */
package com.demo2do.darth.entity.stat;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.demo2do.darth.entity.security.User;

/**
 * @author Downpour
 */
@Entity
@Table(name = "user_stat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserStat {

	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	private Long id;

	private Date createTime;

	private Date lastSession;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;

	/**
	 * The default constructor
	 */
	public UserStat() {

	}

	/**
	 * The constructor using corresponding user ( default UserStat )
	 * 
	 * @param user
	 */
	public UserStat(User user) {
		this.createTime = new Date();
		this.user = user;
	}

	/**
	 * 
	 * @param lastSession
	 * @return
	 */
	public UserStat updateLastSession(Date lastSession) {
		this.lastSession = lastSession;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @return the lastSession
	 */
	public Date getLastSession() {
		return lastSession;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param lastSession
	 *            the lastSession to set
	 */
	public void setLastSession(Date lastSession) {
		this.lastSession = lastSession;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
