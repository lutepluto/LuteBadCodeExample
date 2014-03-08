package com.demo2do.darth.service;

import com.demo2do.core.service.GenericService;
import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.weixin.Account;

public interface ReplyRuleService extends GenericService<ReplyRule> {
	
	public ReplyRule loadReplyRuleByAccountAndTrigger(Account account, String trigger);

}
