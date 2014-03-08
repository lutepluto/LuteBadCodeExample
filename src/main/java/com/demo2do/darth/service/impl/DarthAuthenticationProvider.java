/**
 * 
 */
package com.demo2do.darth.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo2do.core.cache.CompositeCacheAccessor;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.service.UserService;

/**
 * @author Downpour
 */
public class DarthAuthenticationProvider implements UserDetailsService {
	
	private static final Log logger = LogFactory.getLog(DarthAuthenticationProvider.class);
	
	private UserService userService;
	
	private CompositeCacheAccessor cacheAccessor;

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * @param cacheAccessor the cacheAccessor to set
	 */
	public void setCacheAccessor(CompositeCacheAccessor cacheAccessor) {
		this.cacheAccessor = cacheAccessor;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		
		// query database to find user
		List<User> result = userService.getUserByName(username);

		// check whether user exists
		if (result.isEmpty()) {
			throw new UsernameNotFoundException("User is not found in the system.");
		}

		// ======= get user to return =======

		User user = result.get(0);
		
		if (user.isUserLocked()) {
			throw new LockedException("security.authentication.account.locked");
		}

		logger.info(user.getName() + "[" + user.getAuthority() + "] is found to login. ");
		
		// ======= initialize other user properties =======
		
		// 1. initialize managed account

		logger.info("Starting initialize managed account for user[" + user.getName() + "[" + user.getAuthority() + "]]");

		// Using logger here to initialize many-to-many relationship
		logger.info("The user[" + user.getName() + "[" + user.getAuthority() + "]] has managed " + user.getAccounts().size() + " accounts: " + user.getAccounts());
		
		// 2. initialize role based resources
		Map<String, List<String>> resources = (Map<String, List<String>>) cacheAccessor.evaluate("resources['" + user.getAuthority() + "']");
		
		if(MapUtils.isNotEmpty(resources)) {
			
			// initialize user role based resources
			// could be override by two of the conditions in order
			// 1. account role based resources
			// 2. customized resources
			user.initResources(resources);
		
		}
		
		return user;
	}

}
