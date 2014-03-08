/**
 * 
 */
package com.demo2do.darth.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.darth.entity.security.User;

/**
 * @author Downpour
 */
public interface UserService extends GenericService<User> {

	public List<User> getUserByName(String name);
	
	public List<User> getUserByRole(String role);
	
	public void create(User user);
	
	public void createLoginSession(User user);
	
	public void resetPassword(User user, String password);
	
	public void disable(User user);
	
}
