/**
 * 
 */
package com.demo2do.darth.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.darth.entity.stat.LoginSession;
import com.demo2do.darth.service.StatService;

/**
 * @author Downpour
 */
@Service("statService")
public class StatServiceImpl implements StatService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.StatService#listLoginSession(com.demo2do.core.web.resolver.Page)
	 */
	@SuppressWarnings("unchecked")
	public List<LoginSession> listLoginSession(Page page) {
		List<LoginSession> loginSessions = genericDaoSupport.searchForList("FROM LoginSession loginSession ORDER BY loginSession.loginTime DESC", Collections.EMPTY_MAP, page.getBeginIndex(), page.getMaxResultRecords());
		page.calculate(loginSessions.size(), 0);
		return (loginSessions.size() >= page.getEveryPage()) ? loginSessions.subList(0, page.getEveryPage()) : loginSessions;
	}

}
