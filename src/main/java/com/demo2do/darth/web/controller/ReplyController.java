/**
 * 
 */
package com.demo2do.darth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Secure;
import com.demo2do.darth.entity.material.Material;
import com.demo2do.darth.entity.material.MaterialType;
import com.demo2do.darth.entity.material.TextMaterial;
import com.demo2do.darth.entity.reply.ReplyRule;
import com.demo2do.darth.entity.weixin.Account;
import com.demo2do.darth.service.AccountService;
import com.demo2do.darth.service.MaterialService;
import com.demo2do.darth.service.ReplyRuleService;

/**
 * @author Downpour
 */
@Controller
@RequestMapping("/replies")
@MenuSetting("menu-reply")
public class ReplyController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private ReplyRuleService replyRuleService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "/subscribe"})
	@MenuSetting("submenu-reply-subscribe")
	public ModelAndView subscribeSettings(@Secure(property = "account") Account account) {
		
		ModelAndView modelAndView = new ModelAndView("reply/subscribe-settings");
		account = accountService.load(Account.class, account.getId());
		modelAndView.addObject("account", account);
		
		// get the current reply rule
		ReplyRule currentReplyRule = replyRuleService.loadReplyRuleByAccountAndTrigger(account, "subscribe");
		
		// the default material type is text if the account has no subscribe materials 
		String activeMaterialType = "text";
		
		// check the existence of reply
		if (currentReplyRule != null) {
			Material material = currentReplyRule.getMaterial() == null ? null : currentReplyRule.getMaterial();
			if (material != null) {
				MaterialType materialType = material.getType();
				activeMaterialType = materialType.toString().toLowerCase();
				
				Material currentMaterial = materialService.load(Material.class, material.getId());
				modelAndView.addObject("material", currentMaterial);
			}
		}
		modelAndView.addObject("activeMaterialType", activeMaterialType);
		
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/default")
	@MenuSetting("submenu-reply-default")
	public ModelAndView defaultSettings() {
		ModelAndView modelAndView = new ModelAndView("reply/default-settings");
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/inbox")
	@MenuSetting("submenu-reply-inbox")
	public ModelAndView inboxSettings() {
		ModelAndView modelAndView = new ModelAndView("reply/inbox-settings");
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/keywords")
	@MenuSetting("submenu-reply-keywords")
	public ModelAndView keywordsSettings() {
		ModelAndView modelAndView = new ModelAndView("reply/keywords-settings");
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/{id}/{trigger}/edit")
	public ModelAndView editMaterial(@PathVariable("id") long id, 
			@PathVariable("trigger") String trigger, 
			@RequestParam("type") String type) {
		
		ModelAndView modelAndView = new ModelAndView("reply/material-" + type + "-edit");
		
		Account account = accountService.load(Account.class, id);
		modelAndView.addObject("account", account);
		
		modelAndView.addObject("trigger", trigger);
		
		ReplyRule replyRule = replyRuleService.loadReplyRuleByAccountAndTrigger(account, trigger);
		
		if (replyRule != null) {
			Material material = replyRule.getMaterial() == null ? null : replyRule.getMaterial();
			if (material != null) {
				Material currentMaterial = materialService.load(Material.class, material.getId());
				modelAndView.addObject("material", currentMaterial);
			}
		}
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/{id}/{trigger}/text/edit", method=RequestMethod.PATCH)
	public String onEditMaterial(@PathVariable("id") long id, 
			@PathVariable("trigger") String trigger,
			@RequestParam("content") String content) {
		
		TextMaterial textMaterial = new TextMaterial();
		
		// initialize material
		Account account = accountService.load(Account.class, id);
		MaterialType materialType = MaterialType.valueOf("TEXT");
		
		textMaterial.initialize(account, materialType);
		textMaterial.editContent(content);
		
		materialService.save(textMaterial);
		
		ReplyRule replyRule = replyRuleService.loadReplyRuleByAccountAndTrigger(account, trigger);
		
		if (replyRule != null ) {
			replyRule.setMaterial(textMaterial);
			replyRuleService.update(replyRule);
		}
		
		return "redirect:/replies/" + trigger;
	}
	
}
