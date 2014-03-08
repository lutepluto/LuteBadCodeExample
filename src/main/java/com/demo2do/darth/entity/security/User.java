/**
 * 
 */
package com.demo2do.darth.entity.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.demo2do.core.security.details.SecurityUserDetails;
import com.demo2do.darth.Constant;
import com.demo2do.darth.entity.stat.UserStat;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.entity.weixin.AccountType;

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements SecurityUserDetails {

	private static final long serialVersionUID = 3524916260917080865L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String password;

	private String authority;

	@Column(name = "is_primary")
	private boolean primary;
	
	@Column(name = "is_active")
	private boolean active;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@PrimaryKeyJoinColumn
	private UserStat userStat;
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Account.class, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "user_account", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Account> accounts;
	
	@Transient
	private Account account;
	
	@Transient
	private Map<String, Set<String>> resources = new HashMap<String, Set<String>>();

	/** The index url for a user */
	@Transient
	private String indexURL;

	@Transient
	private Long loginSessionKey;
	
	/**
	 * The default constructor
	 */
	public User() {

	}
	
	/**
	 * Create user with roleCode
	 * 
	 * @param roleCode
	 */
	public User(RoleCode roleCode) {
		this.authority = roleCode.name();
		this.active = true;
		this.userStat = new UserStat(this);
	}
	
	/**
	 * Create user with authority
	 * 
	 * @param name
	 * @param password
	 * @param authority
	 */
	public User(String name, String password, String authority) {
		this.name = name;
		this.password = password;
		this.authority = authority;
		this.primary = false;
		this.active = true;
		this.userStat = new UserStat(this);
	}

	/**
	 * Create default user with authority
	 * 
	 * @param name
	 * @param password
	 * @param authority
	 * @param primary
	 */
	public User(String name, String password, String authority, boolean primary) {
		this.name = name;
		this.password = password;
		this.authority = authority;
		this.primary = primary;
		this.active = true;
		this.userStat = new UserStat(this);
	}
	
	/**
	 * Initialize user with name, password and primary flag
	 * 
	 * @param name
	 * @param password
	 * @param primary
	 */
	public User initialize(String name, String password, boolean primary) {
		this.name = name;
		this.password = password;
		this.primary = primary;
		return this;
	}
	
	/**
	 * Initialize resources
	 * 
	 * @param resources
	 */
	public void initResources(Map<String, List<String>> resources) {
		
		for(Iterator<Map.Entry<String, List<String>>> iterator = resources.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, List<String>> entry = iterator.next();
			this.addResource(entry.getKey(), entry.getValue());
		}
		
		// get first url as the indexURL
		this.indexURL = resources.get(Constant.URL_RESOURCE).iterator().next();
	
	}
	
	/**
	 * Add resource from key and value
	 * 
	 * @param key
	 * @param values
	 */
	public void addResource(String key, List<String> values) {
		if(this.resources.containsKey(key)) {
			this.resources.get(key).addAll(values);
		} else {
			this.resources.put(key, new LinkedHashSet<String>(values));
		}
	}

	/**
	 * Add account for the current user
	 * 
	 * @param account
	 */
	public void addAccount(Account account) {
		
		if(CollectionUtils.isEmpty(this.accounts)) {
			this.accounts = new ArrayList<Account>();
		}
		
		this.accounts.add(account);
	}
	
	/**
	 * Reset accounts for the current user
	 * 
	 * @param accounts
	 * @return
	 */
	public User resetAccounts(List<Account> accounts) {
		this.accounts = accounts;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	@Transient
	public Account getFirstAccount() {
		return CollectionUtils.isNotEmpty(this.accounts) ? this.getAccounts().get(0) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	@Transient
	public boolean isWithAccount() {
		return this.getAccounts() != null ? (this.getAccounts().size() > 0) : false;
	}

	/**
	 * Determine whether the user has only one account linked
	 * 
	 * @return
	 */
	@Transient
	public boolean isWithSingleAccount() {
		return this.getAccounts() != null ? (this.getAccounts().size() == 1) : false;
	}

	/**
	 * Determine whether the user has more than one account linked
	 * 
	 * @return
	 */
	@Transient
	public boolean isWithMultiAccounts() {
		return this.getAccounts() != null ? (this.getAccounts().size() > 1) : false;
	}
	
	/**
	 * Determine whether there is active account
	 * 
	 * @return
	 */
	@Transient
	public boolean isWithActiveAccount() {
		return this.account != null;
	}
	
	/**
	 * 
	 * @param loginSession
	 */
	public void createLoginSession(Long loginSessionKey) {
		this.loginSessionKey = loginSessionKey;
	}
	
	/**
	 * Change password
	 * 
	 * @param encodingPassword
	 * @return
	 */
	public User putEncodingPassword(String encodingPassword) {
		this.password = encodingPassword;
		return this;
	}
	
	/**
	 * Change user status
	 * 
	 * @return
	 */
	public User changeStatus(boolean status) {
		this.active = status;
		return this;
	}
	
	/**
	 * 
	 * @param account
	 */
	public void selectActiveAccount(Account account) {
		this.account = account;
	}

	/**
	 * Get resource from key
	 * 
	 * @param key
	 * @return
	 */
	@Transient
	public Set<String> getResource(String key) {
		return this.resources.get(key);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(this.authority);
		
		if(this.isWithActiveAccount()) {
			AccountType accountType = this.getAccount().getProfile().getType();
			authorities.add(new SimpleGrantedAuthority(this.authority + "_" + accountType.name()));
		}
		
		return authorities;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.core.security.SecurityUserDetails#hasAnyPrincipalRole(java.lang.String[])
	 */
	public boolean hasAnyPrincipalRole(String... roles) {
		Set<String> roleSet = AuthorityUtils.authorityListToSet(this.getAuthorities());

        for (String role : roles) {
            if (roleSet.contains(role)) {
                return true;
            }
        }
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.core.security.details.SecurityUserDetails#hasResource(java.lang.String, java.lang.String)
	 */
	public boolean hasResource(String type, String key) {
		return this.resources.containsKey(type) && this.resources.get(type).contains(key);
	}

	/**
	 * 
	 * @return
	 */
	@Transient
	public String[] getAuthorityCodes() {
		return StringUtils.split(this.authority);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return this.active;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return this.active;
	}
	
	public boolean isUserLocked() {
		return this.getAuthority().equals("ROLE_WEIXIN") && this.isWithSingleAccount()
				&& !this.getAccounts().get(0).isActive();
	}
	
	/**
	 * @return the loginSessionKey
	 */
	public Long getLoginSessionKey() {
		return loginSessionKey;
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
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	
	/**
	 * @return the primary
	 */
	public boolean isPrimary() {
		return primary;
	}
	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the userStat
	 */
	public UserStat getUserStat() {
		return userStat;
	}
	
	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}
	
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return the indexURL
	 */
	public String getIndexURL() {
		return indexURL;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @param userStat
	 *            the userStat to set
	 */
	public void setUserStat(UserStat userStat) {
		this.userStat = userStat;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
