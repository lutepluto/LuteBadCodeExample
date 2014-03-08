/**
 * 
 */
package com.demo2do.darth.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.darth.entity.protocol.RequestMessage;
import com.demo2do.darth.entity.protocol.material.Article;

/**
 * The controller to receive request and dispatch to different services
 * 
 * @author Downpour
 */
@Controller
@RequestMapping("/dispatcher")
public class DispatcherController {

	private static final Log logger = LogFactory.getLog(DispatcherController.class);
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{host}", method = RequestMethod.GET)
	public @ResponseBody String onAuthorize(@PathVariable String host, 
											@RequestParam("signature") String signature, 
											@RequestParam("timestamp") String timestamp, 
											@RequestParam("nonce") String nonce, 
											@RequestParam("echostr") String echostr) {

		return echostr;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView onReceive(HttpEntity<RequestMessage> httpEntity) {
		RequestMessage requestMessage = httpEntity.getBody();
		
		// handle requestMessage to generate responseMessage

		ModelAndView modelAndView = new ModelAndView();
		
		String timestamp = DateUtils.getCurrentTimeMillis();
				
		if(requestMessage != null) {
			if("text".equalsIgnoreCase(requestMessage.getType())) {
				if(logger.isDebugEnabled()) {
					logger.info("Receive Text Message");
				}
				
				if("get text".equalsIgnoreCase(requestMessage.getContent())) {

					modelAndView.addObject("from", requestMessage.getTo());
					modelAndView.addObject("to", requestMessage.getFrom());
					modelAndView.addObject("createTime", timestamp);
					modelAndView.addObject("content", "content");

					modelAndView.setViewName("message");

					return modelAndView;
				} else if("get news".equalsIgnoreCase(requestMessage.getContent())) {
					List<Article> articles = new ArrayList<Article>(2);
					
					Article article1 = new Article();
					article1.setTitle("title1");
					article1.setDescription("description1");
					article1.setPictureUrl("http://jilu711.com/up.jpg");
					article1.setUrl("http://www.biolife365.com/m/");
					articles.add(article1);
					
					Article article2 = new Article();
					article2.setTitle("title");
					article2.setDescription("description");
					article2.setPictureUrl("http://cdn.ta-club.com/static/images/biz/hot/bb01.jpg");
					article2.setUrl("http://www.biolife365.com/m/");
					articles.add(article2);
					
					modelAndView.setViewName("article");

					modelAndView.addObject("from", requestMessage.getTo());
					modelAndView.addObject("to", requestMessage.getFrom());
					modelAndView.addObject("createTime", timestamp);
					modelAndView.addObject("articleAount", String.valueOf(articles.size()));
					modelAndView.addObject("articles", articles);
					
					return modelAndView;
				}
			}
		}
		
		return null;
	}

}
