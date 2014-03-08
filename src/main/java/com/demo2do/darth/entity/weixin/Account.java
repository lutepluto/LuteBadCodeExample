/**
 * 
 */
package com.demo2do.darth.entity.weixin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.demo2do.darth.entity.material.Material;
import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.entity.stat.AccountStat;

@Entity
@Table(name = "account")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** biolife365 */
	private String code;

	/** @012huoliquankai */
	private String password;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Development development;

	/** whether the account is active */
	private boolean active;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReplyRule> replyRules;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Material> materials;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "user_account", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<User> admins;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@PrimaryKeyJoinColumn
	private AccountStat accountStat;
	
	@Transient
	private boolean checked;

	/**
	 * The default constructor
	 */
	public Account() {

	}

	/**
	 * Determine whether the account has admin users linked
	 * 
	 * @return
	 */
	public boolean isWithAdminUsers() {
		return CollectionUtils.isNotEmpty(this.admins);
	}
	
	/**
	 * Initialize account with profile, region, development and accountStat
	 * 
	 * @param profile
	 * @param development
	 * @param accountStat
	 * @return
	 */
	public void initialize(Profile profile, Development development, AccountStat accountStat) {
		this.profile = profile;
		this.development = development;
		this.accountStat = accountStat;
		this.replyRules = new ArrayList<ReplyRule>();
		this.active = true;
	}
	
	/**
	 * Create admin user and link with account
	 * 
	 * @param admin
	 */
	public void createAdmin(User admin) {
		this.admins = new ArrayList<User>(1);
		this.admins.add(admin.initialize(this.code, this.password, true));
	}
	
	/**
	 * Add replyRule
	 * 
	 * @param replyRule
	 */
	public void addReplyRule(ReplyRule replyRule) {
		this.replyRules.add(replyRule);
	}
	
	/**
	 * Change password
	 * 
	 * @param password
	 */
	public void changePassword(String password) {
		this.password = password;
	}
	
	/**
	 * Change account status
	 * 
	 * @return
	 */
	public Account changeStatus() {
		this.active = !this.active;
		return this;
	}
	
	public Account changeDevelopment(Development development) {
		this.setDevelopment(development);
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @return the development
	 */
	public Development getDevelopment() {
		return development;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * @return the replyRules
	 */
	public List<ReplyRule> getReplyRules() {
		return replyRules;
	}
	
	/**
	 * @return the materials
	 */
	public List<Material> getMaterials() {
		return materials;
	}

	/**
	 * @return the admins
	 */
	public List<User> getAdmins() {
		return admins;
	}

	/**
	 * @return the accountStat
	 */
	public AccountStat getAccountStat() {
		return accountStat;
	}
	
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	/**
	 * @param development the development to set
	 */
	public void setDevelopment(Development development) {
		this.development = development;
	}
	
	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @param replyRules the replyRules to set
	 */
	public void setReplyRules(List<ReplyRule> replyRules) {
		this.replyRules = replyRules;
	}
	
	/**
	 * @param materials the materials to set
	 */
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	/**
	 * @param admins
	 *            the admins to set
	 */
	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}
	
	/**
	 * @param accountStat the accountStat to set
	 */
	public void setAccountStat(AccountStat accountStat) {
		this.accountStat = accountStat;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
