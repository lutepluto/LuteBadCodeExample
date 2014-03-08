/**
 * 
 */
package com.demo2do.darth.service;

import java.util.Date;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.demo2do.darth.entity.stat.AccountDailyStat;
import com.demo2do.darth.entity.stat.AccountStat;
import com.demo2do.darth.entity.stat.DateRange;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.entity.weixin.Development;
import com.demo2do.darth.entity.weixin.Profile;

/**
 * @author Downpour
 */
public interface AccountService extends GenericService<Account> {

	/**
	 * 
	 * @param account
	 * @param profile
	 * @param development
	 * @param accountStat
	 * @param createAdmin
	 */
	public void create(Account account, Profile profile, Development development, AccountStat accountStat, boolean createAdmin);
	
	/**
	 * 
	 * @param account
	 * @param dateRange
	 * @return
	 */
	public Map<String, AccountDailyStat> getAccountDailyStat(Account account, DateRange dateRange);
	
	/**
	 * Create AccountDailyStat for all accounts
	 * 
	 * @param day
	 */
	public void createAccountDailyStat(Date day);
	
	/**
	 * Update AccountStat for all accounts
	 * 
	 * @param day
	 */
	public void updateAccountStat(Date day);
	
}
