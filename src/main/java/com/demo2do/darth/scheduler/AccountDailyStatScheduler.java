/**
 * 
 */
package com.demo2do.darth.scheduler;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.darth.service.AccountService;

/**
 * @author Downpour
 */
@Component("accountDailyStatScheduler")
public class AccountDailyStatScheduler {

	private static final Log logger = LogFactory.getLog(AccountDailyStatScheduler.class);
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Create a AccountDailyStat for each Account every day.
	 * 
	 * Running at 23:30 every day
	 * 
	 * @throws Exception
	 */
	public void createAccountDailyStat() throws Exception {
		
		logger.info("Start running AccountDailyStatScheduler#createAccountDailyStat");
		
		accountService.createAccountDailyStat(DateUtils.addDays(new Date(), 1));
		
	}
	
	/**
	 * Update AccountStat for each Account every day.
	 * 
	 * Running at 0:02 every day
	 * 
	 * @throws Exception
	 */
	public void updateAccountStat() throws Exception {
		
		logger.info("Start running AccountDailyStatScheduler#updateAccountStat");
		
		accountService.updateAccountStat(DateUtils.addDays(new Date(), -1));
		
	}

}
