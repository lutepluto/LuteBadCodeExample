/**
 * 
 */
package com.demo2do.darth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo2do.core.cache.CacheAccessor;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.entity.stat.LoginSession;
import com.demo2do.darth.entity.stat.UserStat;
import com.demo2do.darth.service.UserService;

/**
 * @author Downpour
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	@Autowired
	private CacheAccessor cacheAccessor;
	
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#getUserByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserByName(String name) {
		return genericDaoSupport.searchForCacheableList("FROM User user WHERE user.name = :name AND user.active = true", "name", name);
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#getUserByRole(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserByRole(String role) {
		return genericDaoSupport.searchForCacheableList("FROM User user WHERE user.active = true AND user.authority = :authority ORDER BY user.primary ASC,user.id DESC", "authority", cacheAccessor.evaluate("roles['" + role + "'].name"));
	}

	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#create(com.demo2do.darth.entity.security.User)
	 */
	public void create(User user) {
		String encodingPassword = this.passwordEncoder.encodePassword(user.getPassword(), null);
		genericDaoSupport.save(user.putEncodingPassword(encodingPassword));
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#createLoginSession(com.demo2do.darth.entity.security.User)
	 */
	public void createLoginSession(User user) {
		
		Date loginTime = new Date();

		// add new login session record
		Long loginSessionKey = (Long) genericDaoSupport.save(new LoginSession(user.getName(), loginTime));
		user.createLoginSession(loginSessionKey);
		
		// update last session in userstat with login time
		UserStat userStat = genericDaoSupport.load(UserStat.class, user.getId());
		genericDaoSupport.update(userStat.updateLastSession(loginTime));
		
	}

	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#resetPassword(com.demo2do.darth.entity.security.User, java.lang.String)
	 */
	public void resetPassword(User user, String password) {
		String encodingPassword = this.passwordEncoder.encodePassword(password, null);
		this.genericDaoSupport.update(user.putEncodingPassword(encodingPassword));
	}

	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.UserService#disable(com.demo2do.darth.entity.security.User)
	 */
	public void disable(User user) {
		genericDaoSupport.update(user.changeStatus(false));
	}
	
}
