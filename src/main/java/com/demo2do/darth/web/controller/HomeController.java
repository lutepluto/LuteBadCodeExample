/**
 * 
 */
package com.demo2do.darth.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.cache.CompositeCacheAccessor;
import com.demo2do.core.utils.BeanUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Secure;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.entity.stat.AccountDailyStat;
import com.demo2do.darth.entity.stat.DateRange;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.service.AccountService;

/**
 * @author Downpour
 */
@Controller
@RequestMapping("")
@MenuSetting("menu-home")
public class HomeController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CompositeCacheAccessor cacheAccessor;
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/index", "/switch-account"})
	public ModelAndView index(@Secure User user) {
		
		ModelAndView modelAndView = new ModelAndView();

		if(user.isWithSingleAccount()) {
			
			// select single account as default active account
			user.selectActiveAccount(user.getFirstAccount());
			
			// reset role and resource definition from active account
			for(GrantedAuthority authority : user.getAuthorities()) {
				Map<String, List<String>> resources = (Map<String, List<String>>) cacheAccessor.evaluate("resources['" + authority.getAuthority() + "']");
				for(Iterator<Map.Entry<String, List<String>>> iterator = resources.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String, List<String>> entry = iterator.next();
					user.addResource(entry.getKey(), entry.getValue());
				}
			}
			
			// redirect to home page
			modelAndView.setViewName("redirect:/home");

		} else {
		
			// goes to index page to select accounts
			modelAndView.setViewName("index");
			modelAndView.addObject("accounts", user.getAccounts());
		
		}

		return modelAndView;
	}
	
	/**
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/home")
	public ModelAndView home(@Secure(property = "account") Account account, DateRange dateRange) {
		
		Map<String, AccountDailyStat> accountDailyStats = accountService.getAccountDailyStat(account, dateRange.hasValue() ? dateRange : dateRange.last7days());
		
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("account", account);
		modelAndView.addObject("dateRange", dateRange);
		
		modelAndView.addObject("fansDailyNetIncrements", BeanUtils.extractPropertyList(accountDailyStats.values(), "fansDailyNetIncrement"));
		modelAndView.addObject("messageDailyIncrements", BeanUtils.extractPropertyList(accountDailyStats.values(), "messageDailyIncrement"));
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView onSelectActiveAccount(@RequestParam(value = "id", required = false) Long id, @Secure User user, DateRange dateRange) {
		
		ModelAndView modelAndView = new ModelAndView("home");

		// select active account for user
		Account account = accountService.load(Account.class, id);
		user.selectActiveAccount(account);
		
		// reset role and resource definition from active account
		for(GrantedAuthority authority : user.getAuthorities()) {
			Map<String, List<String>> resources = (Map<String, List<String>>) cacheAccessor.evaluate("resources['" + authority.getAuthority() + "']");
			for(Iterator<Map.Entry<String, List<String>>> iterator = resources.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, List<String>> entry = iterator.next();
				user.addResource(entry.getKey(), entry.getValue());
			}
		}
		
		// set up statatistic data
		Map<String, AccountDailyStat> accountDailyStats = accountService.getAccountDailyStat(account, dateRange.hasValue() ? dateRange : dateRange.last7days());
		
		modelAndView.addObject("account", account);
		modelAndView.addObject("dateRange", dateRange);
		
		modelAndView.addObject("fansDailyNetIncrements", BeanUtils.extractPropertyList(accountDailyStats.values(), "fansDailyNetIncrement"));
		modelAndView.addObject("messageDailyIncrements", BeanUtils.extractPropertyList(accountDailyStats.values(), "messageDailyIncrement"));
		
		
		return modelAndView;
	}

}
