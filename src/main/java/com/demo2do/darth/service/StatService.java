/**
 * 
 */
package com.demo2do.darth.service;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.demo2do.darth.entity.stat.LoginSession;

/**
 * @author Downpour
 */
public interface StatService {
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<LoginSession> listLoginSession(Page page);

}
