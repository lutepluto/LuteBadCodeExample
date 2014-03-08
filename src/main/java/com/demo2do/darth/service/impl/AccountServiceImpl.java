/**
 * 
 */
package com.demo2do.darth.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.reply.Trigger;
import com.demo2do.darth.entity.security.RoleCode;
import com.demo2do.darth.entity.security.User;
import com.demo2do.darth.entity.stat.AccountDailyStat;
import com.demo2do.darth.entity.stat.AccountStat;
import com.demo2do.darth.entity.stat.DateRange;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.entity.weixin.Development;
import com.demo2do.darth.entity.weixin.Profile;
import com.demo2do.darth.service.AccountService;
import com.demo2do.darth.web.controller.exception.BusinessException;

/**
 * @author Downpour
 */
@Service("accountService")
public class AccountServiceImpl extends GenericServiceImpl<Account> implements AccountService {

	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Value("#{remote['weixin.account.development.url.prefix']}")
	private String prefixURL;
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.AccountService#create(com.demo2do.darth.entity.weixin.Account, com.demo2do.darth.entity.weixin.Profile, com.demo2do.darth.entity.weixin.Development, com.demo2do.darth.entity.stat.AccountStat, boolean)
	 */
	public void create(Account account, Profile profile, Development development, AccountStat accountStat, boolean createAdmin) {
		
		//check existence
		if (getAccountByCode(account.getCode()) != null) {
			throw new BusinessException("error.account.create.duplication.failure", "account/account-auto-create");
		}
		
		
		// reset password
		account.changePassword(passwordEncoder.encodePassword(account.getPassword(), null));
		
		// generate default development url
		//profile.setCreateDate(new Date());
		//String encodedSuffixURL = this.passwordEncoder.encodePassword(profile.getWeixinCode(), profile.getCreateDate());
		development.createDefaultUrl(prefixURL, profile.getWeixinCode());
		
		// initialize profile, region, development and accountState
		account.initialize(profile, development, accountStat);
		
		// create default admin
		if (createAdmin) {
			account.createAdmin(new User(RoleCode.ROLE_WEIXIN));
		}
		
		// create reply rule
		for(Trigger trigger : Trigger.values()) {
			if(trigger.isSingle()) {
				account.addReplyRule(new ReplyRule(trigger, account));
			}
		}
		
		genericDaoSupport.save(account);
	}
	
	@SuppressWarnings("unchecked")
	private Account getAccountByCode(String code) {
		List<Account> accounts =  genericDaoSupport.searchForCacheableList("FROM Account account WHERE account.code = :code", "code", code);
		
		return accounts.size() > 0 ? accounts.get(0) : null;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.AccountService#getAccountDailyStat(com.demo2do.darth.entity.weixin.Account, com.demo2do.darth.entity.stat.DateRange)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, AccountDailyStat> getAccountDailyStat(Account account, DateRange dateRange) {
		
		// prepare result
		Map<String, AccountDailyStat> result = new LinkedHashMap<String, AccountDailyStat>(dateRange.getIntervalDays());
		for(Date date : DateUtils.getIntervalDays(dateRange.getStartDate(), dateRange.getEndDate())) {
			result.put(DateUtils.format(date, "yyyy-MM-dd"), new AccountDailyStat(date, account));
		}
		
		// do query of AccountDailyStat by dateRange and populate result again
		List<AccountDailyStat> accountDailyStats = genericDaoSupport.searchForList("FROM AccountDailyStat accountDailyStat WHERE accountDailyStat.statDate >= :startDate AND accountDailyStat.statDate <= :endDate", dateRange);
		
		for(AccountDailyStat accountDailyStat : accountDailyStats) {
			result.put(DateUtils.format(accountDailyStat.getStatDate(), "yyyy-MM-dd"), accountDailyStat);
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.AccountService#createAccountDailyStat(java.util.Date)
	 */
	public void createAccountDailyStat(Date day) {
		
		// load all the accounts
		List<Account> accounts = genericDaoSupport.loadAll(Account.class);
	
		// create account daily stat
		for(Account account : accounts) {
			genericDaoSupport.save(new AccountDailyStat(day, account));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.AccountService#updateAccountStat(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public void updateAccountStat(Date day) {
		
		// load all the accounts
		List<Account> accounts = genericDaoSupport.loadAll(Account.class);
		
		for(Account account : accounts) {
			AccountStat accountStat = genericDaoSupport.load(AccountStat.class, account.getId());
			List<AccountDailyStat> accountDailyStats = genericDaoSupport.searchForList("FROM AccountDailyStat accountDailyStat WHERE accountDailyStat.statDate in (:statDate, :weekAgo, :monthAgo) AND accountDailyStat.account = :account ORDER BY accountDailyStat.statDate ASC", new AccountDailyStat(day, account));
			if(!accountDailyStats.isEmpty()) {
				// find out accountDailyStat from today, week ago and month ago
				AccountDailyStat today = accountDailyStats.get(0);
				AccountDailyStat weekAgo = accountDailyStats.size() > 1 ? accountDailyStats.get(1) : new AccountDailyStat(DateUtils.addWeeks(day, -1), account);
				AccountDailyStat monthAgo = accountDailyStats.size() > 2 ? accountDailyStats.get(2) : new AccountDailyStat(DateUtils.addMonths(day, -1), account);
				// update accountStat by calculation
				genericDaoSupport.update(accountStat.calculate(today, weekAgo, monthAgo));
			}
		}
		
	}
	
}
