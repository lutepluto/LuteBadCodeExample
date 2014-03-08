package com.demo2do.darth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.service.ReplyRuleService;

@Service("ReplyRuleService")
public class ReplyRuleServiceImpl extends GenericServiceImpl<ReplyRule> implements
		ReplyRuleService {
	
	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Override
	public ReplyRule loadReplyRuleByAccountAndTrigger(Account account,
			String trigger) {
		
		List<ReplyRule> replyRules = account.getReplyRules();
		
		for (ReplyRule replyRule : replyRules) {
			if (replyRule.getTrigger().toString().toLowerCase().equals(trigger))
				return replyRule;
		}
		return null;
	}

}
