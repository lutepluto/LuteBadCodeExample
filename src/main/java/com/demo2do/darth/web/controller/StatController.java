package com.demo2do.darth.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.demo2do.darth.entity.stat.LoginSession;
import com.demo2do.darth.service.StatService;

@Controller
@RequestMapping("/stat")
@MenuSetting("menu-stat")
public class StatController {
	
	@Autowired
	private StatService statService;
	
	/**
	 * 
	 * list all login session stat
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String list(Page page) {
		return "redirect:/stat/login-session?page=" + page.getCurrentPage();
	}
	
	@RequestMapping("/login-session")
	@MenuSetting("submenu-login-session")
	public ModelAndView page(Page page) {
		
		ModelAndView modelAndView = new ModelAndView("stat/login-session");
		
		List<LoginSession> loginSessions = statService.listLoginSession(page);
		modelAndView.addObject("loginSessions", loginSessions);
		
		return modelAndView;
		
	}
	
}
