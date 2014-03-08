/**
 * 
 */
package com.demo2do.darth.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.darth.Constant;
import com.demo2do.darth.entity.remote.LoginInput;
import com.demo2do.darth.entity.remote.LoginOutput;
import com.demo2do.darth.entity.remote.LoginResult;
import com.demo2do.darth.entity.weixin.AccountType;
import com.demo2do.darth.entity.weixin.Profile;
import com.demo2do.darth.service.RemoteHandler;
import com.demo2do.darth.web.controller.exception.BusinessException;

/**
 * @author Downpour
 */
@Component("remoteHandler")
public class RemoteHandlerImpl implements RemoteHandler {
	
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	@Value("#{remote['weixin.login.url']}")
	private String loginURL;
	
	@Value("#{remote['weixin.home.url']}")
	private String homeURL;
	
	@Value("#{remote['weixin.settings.url']}")
	private String settingsURL;
	
	@Value("#{remote['weixin.avatar.url']}")
	private String avatarURL;
	
	@Value("#{remote['weixin.qrcode.url']}")
	private String qrcodeURL;
	
	@Value("#{remote['weixin.referer']}")
	private String referer;
	
	@Value("#{remote['weixin.host']}")
	private String host;
	
	@Value("#{remote['weixin.avatar.folder']}")
	private String avatarFolder;
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.RemoteAccountAccessor#login(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public LoginOutput login(String code, String password) {
		
		LoginOutput output = new LoginOutput();
		
		try {
			
			LoginInput loginInput = new LoginInput(code, this.passwordEncoder.encodePassword(password, null));
			
			Response response = Jsoup.connect(this.loginURL)
									 .ignoreContentType(true)
									 .header("Host", this.host)
									 .data(loginInput.data())
									 .referrer(this.referer)
									 .method(Method.POST)
									 .execute();
			
			LoginResult loginResult = JSON.parseObject(response.body(), LoginResult.class);
			
			if(loginResult.isValid()) {
				output.initialize(loginResult, response.cookies());
			} else {
				throw new BusinessException("error.account.remote.login.failure", "account/account-auto-create");
			}
			
		} catch (IOException e) {
			//e.printStackTrace();
			throw new BusinessException("error.account.create.failure", "account/account-auto-create");
		}
		
		return output;
	}

	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.RemoteAccountAccessor#retrieveAccountFansNumber(java.lang.String, java.util.Map)
	 */
	public int retrieveAccountFansNumber(String token, Map<String, String> cookies) {
		
		try {
			
			Document document = Jsoup.connect(this.homeURL)
									 .ignoreContentType(true)
									 .header("Host", this.host)
									 .data(Constant.WEIXIN_REQUEST_TOKEN, token)
									 .referrer(this.referer)
									 .cookies(cookies)
									 .get();
			
			Element totalFansElement = document.select(".total_fans .number").first();
			return Integer.valueOf(totalFansElement.text()).intValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error.account.create.failure", "account/account-auto-create");
		}
		
		//return -1;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.RemoteAccountAccessor#retrieveAccountProfile(java.lang.String, java.util.Map)
	 */
	public Profile retrieveAccountProfile(String token, Map<String, String> cookies) {
		
		Profile profile = new Profile();
		
		try {
			
			Document document = Jsoup.connect(this.settingsURL)
									 .ignoreContentType(true)
									 .header("Host", this.host)
									 .data(Constant.WEIXIN_REQUEST_TOKEN, token)
									 .referrer(this.referer)
									 .cookies(cookies)
									 .get();
			
			Element nameElement = document.select(".account .account_info .nickname").first();
			profile.setName(nameElement.text());
			
			Element descriptionElement = document.select("#modifyUserInfo").first().parent().parent().nextElementSibling();
			profile.setDescription(descriptionElement.text());
			
			Elements settingAreaHeaders = document.select("#settingArea h4");
			for(Element element : settingAreaHeaders) {
				if(element.text().trim().equals("微信号")) {
					profile.setWeixinCode(element.nextElementSibling().nextElementSibling().text());
				}
			}
			
			Element typeElement = document.select(".account .account_info label").first();
			profile.setType(typeElement.attr("class").contains("icon_subscribe_label") ? AccountType.SUBSCRIBE : AccountType.SERVICE);

			Element avatarElement = document.select(".account .account_info .avatar").first();
			String fakeId = StringUtils.findParameterFromURI(avatarElement.attr("src"), "fakeid");
			profile.setFakeId(fakeId);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("error.account.create.failure", "account/account-auto-create");
		}
		
		return profile;
	}
	
	/* (non-Javadoc)
	 * @see com.demo2do.darth.service.RemoteAccountAccessor#retrieveAccountImage(java.lang.String, java.lang.String, java.util.Map)
	 */
	public String retrieveAccountImage(String fakeId, String token, Map<String, String> cookies) {
		
		String avatarFileName = this.passwordEncoder.encodePassword(fakeId, null) + ".jpg";
		
		try {

			// download avatar
			Response avatarResponse = Jsoup.connect(this.avatarURL)
									 .ignoreContentType(true)
									 .header("Host", this.host)
									 .data(Constant.WEIXIN_REQUEST_FAKEID, fakeId)
									 .data(Constant.WEIXIN_REQUEST_TOKEN, token)
									 .referrer(this.referer)
									 .cookies(cookies)
									 .method(Method.GET)
									 .execute();
			
			
			if(avatarResponse.statusCode() != 500) {
				File avatar = new File(this.avatarFolder + avatarFileName);
				FileUtils.copyInputStreamToFile(new ByteArrayInputStream(avatarResponse.bodyAsBytes()), avatar);
			}
			
			// download qrcode
			Response qrcodeResponse = Jsoup.connect(this.qrcodeURL)
							.ignoreContentType(true)
							.header("Host", this.host)
							.data(Constant.WEIXIN_REQUEST_FAKEID, fakeId)
							.data(Constant.WEIXIN_REQUEST_TOKEN, token)
							.referrer(this.referer)
							.cookies(cookies)
							.method(Method.GET)
							.execute();
			
			if(qrcodeResponse.statusCode() != 500) {
				File qrcode = new File(this.avatarFolder + "qrcode_" + avatarFileName);
				FileUtils.copyInputStreamToFile(new ByteArrayInputStream(qrcodeResponse.bodyAsBytes()), qrcode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error.account.create.failure", "account/account-auto-create");
		}
		
		return avatarFileName;
	}
}
