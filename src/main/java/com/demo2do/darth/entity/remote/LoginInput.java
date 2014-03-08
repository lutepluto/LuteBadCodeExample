/**
 * 
 */
package com.demo2do.darth.entity.remote;

import java.util.Map;

import com.demo2do.core.utils.BeanUtils;

/**
 * @author Downpour
 */
public class LoginInput {
	
	private String username;
	
	private String pwd;
	
	private String imgcode;
	
	private String f;
	
	/**
	 * The constructor using username and password
	 * 
	 * @param username
	 * @param password
	 */
	public LoginInput(String username, String password) {
		this.username = username;
		this.pwd = password;
		this.imgcode = "";
		this.f = "json";
	}

	/**
	 * Returns the Map value of this bean
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map data() {
		return BeanUtils.describe(this);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @return the imgcode
	 */
	public String getImgcode() {
		return imgcode;
	}

	/**
	 * @return the f
	 */
	public String getF() {
		return f;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @param imgcode the imgcode to set
	 */
	public void setImgcode(String imgcode) {
		this.imgcode = imgcode;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(String f) {
		this.f = f;
	}
	
	
	
}
