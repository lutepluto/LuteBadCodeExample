/**
 * 
 */
package com.demo2do.darth.service;

import java.util.Map;

import com.demo2do.darth.entity.remote.LoginOutput;
import com.demo2do.darth.entity.weixin.Profile;

/**
 * @author Downpour
 */
public interface RemoteHandler {

	/**
	 * Mock as a HttpClient to do remote login, get Cookie as return
	 * 
	 * @param code
	 * @param password
	 * @return
	 */
	public LoginOutput login(String code, String password);

	/**
	 * Retrieve account profile
	 * 
	 * @param cookies
	 * @return
	 */
	public Profile retrieveAccountProfile(String token, Map<String, String> cookies);
	
	/**
	 * Retrieve fansNumber of the account
	 *
	 * @param token
	 * @param cookies
	 * @return
	 */
	public int retrieveAccountFansNumber(String token, Map<String, String> cookies);
	
	/**
	 * Save account image and returns the file name
	 * 
	 * @param fakeId
	 * @param token
	 * @param cookies
	 * @return
	 */
	public String retrieveAccountImage(String fakeId, String token, Map<String, String> cookies);


}
