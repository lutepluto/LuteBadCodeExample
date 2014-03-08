/**
 * 
 */
package com.demo2do.darth.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.InfoMessage;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.darth.entity.remote.LoginOutput;
import com.demo2do.darth.entity.stat.AccountStat;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.entity.weixin.Development;
import com.demo2do.darth.entity.weixin.Profile;
import com.demo2do.darth.service.AccountService;
import com.demo2do.darth.service.RemoteHandler;

/**
 * @author Downpour
 */
@Controller
@RequestMapping("/accounts")
@MenuSetting("menu-account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RemoteHandler remoteHandler;
	
	/**
	 * List all the accounts, including active and inactive accounts
	 * 
	 * @return
	 */
	@RequestMapping("")
	@MenuSetting("submenu-account-list")
	public ModelAndView list() {

		List<Account> accounts = accountService.loadAll(Account.class);

		ModelAndView modelAndView = new ModelAndView("account/account-list");
		modelAndView.addObject("accounts", accounts);

		return modelAndView;

	}
	
	/**
	 * Prepare to create a new account
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	@MenuSetting("submenu-account-create")
	public String create() {
		return "account/account-create";
	}
	
	/**
	 * Select account creation mode
	 * 
	 * @param mode
	 * @return
	 */
	@RequestMapping("/{mode}-create")
	@MenuSetting("submenu-account-create")
	public String onSelectCreationMode(@PathVariable("mode") String mode) {
		return "account/account-" + mode + "-create";
	}
	
	/**
	 * Create a new account in the system.
	 * 
	 * Go to mp.weixin.qq.com to mock login to grab some basic information of the account and save into local database.
	 * 
	 * If createAdmin flag is set as true, an admin user will be created linked to the account
	 * 
	 * @param account
	 * @param createAdmin
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@InfoMessage("info.account.create.success")
	public String onCreate(Account account, @RequestParam(value = "createAdmin", required = false) boolean createAdmin) {
		
		// do remote mock login first
		LoginOutput loginOutput = remoteHandler.login(account.getCode(), account.getPassword());
		
		// TODO check if remote login is successful
		
		// 1. retrieve fansNumber to construct accountDailyStat
		int fansNumber = remoteHandler.retrieveAccountFansNumber(loginOutput.getToken(), loginOutput.getCookies());
		AccountStat accountStat = new AccountStat(account, fansNumber);
		
		// 2. retrieve account profile
		Profile profile = remoteHandler.retrieveAccountProfile(loginOutput.getToken(), loginOutput.getCookies());
		
		// 3. retrieve account image and save to local
		String avatar = remoteHandler.retrieveAccountImage(profile.getFakeId(), loginOutput.getToken(), loginOutput.getCookies());
		profile.initialize(avatar);
		
		// 4. TODO initialize account development by grab appId and appSecrect
		Development development = new Development();
		
		//5.  check if account already existed
		accountService.create(account, profile, development, accountStat, createAdmin);
		
		return "redirect:/accounts";
		
	}
	
	@RequestMapping("/{id}/change-status")
	public ModelAndView changeStatus(@PathVariable("id") long id) {
		
		Account account = accountService.load(Account.class, id);
		
		ModelAndView modelAndView = new ModelAndView("account/account-status-change");
		modelAndView.addObject("account", account);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/change-status", method = RequestMethod.PATCH)
	@InfoMessage("info.account.change-status.succes")
	public String onChangeStatus(@PathVariable("id") long id) {
		
		Account account = accountService.load(Account.class, id);
		accountService.update(account.changeStatus());
		
		return "redirect:/accounts";
	}
	
	@RequestMapping("/{id}/change-development")
	public ModelAndView changeDevelopment(@PathVariable("id") long id) {
		
		Account account = accountService.load(Account.class, id);
		
		ModelAndView modelAndView = new ModelAndView("account/account-development-change");
		modelAndView.addObject("account", account);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/change-development", method = RequestMethod.PATCH)
	@InfoMessage("info.account.change-development.success")
	public String onChangeDevelopment(@PathVariable("id") long id, Development development) {
		
		Account account = accountService.load(Account.class, id);
		
		account.changeDevelopment(development);
		
		accountService.update(account);
				
		return "redirect:/accounts";
	}

}
