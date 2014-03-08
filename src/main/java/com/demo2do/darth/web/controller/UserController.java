/**
 * 
 */
package com.demo2do.darth.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.utils.BeanUtils;
import com.demo2do.core.web.interceptor.InfoMessage;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.darth.entity.security.RoleCode;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.service.AccountService;
import com.demo2do.darth.service.UserService;

/**
 * @author Downpour
 */
@Controller
@RequestMapping("/users")
@MenuSetting("menu-account")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping("/role-{role}")
	public ModelAndView list(@PathVariable String role, HttpServletRequest request) {

		List<User> users = userService.getUserByRole(role);

		ModelAndView modelAndView = new ModelAndView("user/" + role + "-user-list");
		modelAndView.addObject("users", users);

		modelAndView.addObject("activeSubmenu", "submenu-user-" + role);

		return modelAndView;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/create-{type}")
	public ModelAndView create(@PathVariable String type) {
		
		ModelAndView modelAndView = new ModelAndView("user/" + type + "-user-create");
		
		if(RoleCode.aliasOf(type).equals(RoleCode.ROLE_WEIXIN.name())) {
			List<Account> accounts = accountService.loadAll(Account.class);
			modelAndView.addObject("accounts", accounts);
		}
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@InfoMessage("info.user.create.success")
	public String onCreate(@RequestParam String name, @RequestParam String password, @RequestParam("authority") RoleCode roleCode, @RequestParam(value = "accountIds", required = false) Long[] accountIds) {
		
		// create a new user
		User user = new User(name, password, roleCode.name());
			
		// add account when it is a ROLE_WEIXIN user and has managed accountIds
		if(roleCode.equals(RoleCode.ROLE_WEIXIN) && ArrayUtils.isNotEmpty(accountIds)) {
			for(Long accountId : accountIds) {
				Account account = accountService.load(Account.class, accountId);
				user.addAccount(account);
			}
		}
			
		userService.create(user);
		
		return "redirect:/users/role-" + RoleCode.valueOf(user.getAuthority()).getKey();
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/{id}/reset-password")
	public String resetPassword(@PathVariable("id") Long id) {
		return "user/user-password-reset";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}/reset-password", method = RequestMethod.PATCH)
	@InfoMessage("info.user.reset-password.success")
	public String onResetPassword(@PathVariable("id") Long id, @RequestParam("password") String password) {

		// load user and reset password
		User user = userService.load(User.class, id);
		
		userService.resetPassword(user, password);

		return "redirect:/users/role-" + RoleCode.valueOf(user.getAuthority()).getKey();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long id) {
		return "user/user-delete";
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@InfoMessage("info.user.delete.success")
	public String onDelete(@PathVariable("id") Long id) {

		// delete user
		User user = userService.load(User.class, id);
		
		userService.disable(user);
		
		return "redirect:/users/role-" + RoleCode.valueOf(user.getAuthority()).getKey();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{id}/accounts")
	public ModelAndView manageAccount(@PathVariable("id") Long id) {
		
		User user = userService.load(User.class, id);
		
		List<Long> userAccountIds = BeanUtils.extractPropertyList(user.getAccounts(), "id");
		List<Account> accounts = accountService.loadAll(Account.class);
		
		for(Account account : accounts) {
			if(userAccountIds.contains(account.getId())) {
				account.setChecked(true);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("user/user-account-manage");
		modelAndView.addObject("user", user);
		modelAndView.addObject("accounts", accounts);
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @param id
	 * @param accountIds
	 * @return
	 */
	@RequestMapping(value="/{id}/accounts", method = RequestMethod.PATCH)
	@InfoMessage("info.user.manage-accounts.success")
	public String onManageAccount(@PathVariable("id") long id, @RequestParam(value = "accountIds", required = false) Long[] accountIds) {
		
		User user = userService.load(User.class, id);
		
		List<Account> accounts = new ArrayList<Account>();
		
		if(accountIds != null) {
			for(Long accountId : accountIds) {
				Account account = accountService.load(Account.class, accountId);
				accounts.add(account);
			}
		}
		
		userService.update(user.resetAccounts(accounts));
		
		return "redirect:/users/role-" + RoleCode.valueOf(user.getAuthority()).getKey();
	}

	
}
