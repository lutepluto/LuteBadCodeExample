/**
 * 
 */
package com.demo2do.darth.entity.remote;

/**
 * @author Downpour
 */
public class LoginResult {

	private String Ret;

	private String ErrMsg;

	private String ErrCode;

	private String ShowVerifyCode;

	/**
	 * 
	 * @return
	 */
	public boolean isValid() {
		return this.getErrCode().equals("0") && this.getRet().equals("302");
	}

	/**
	 * 
	 * @return
	 */
	public String getToken() {
		return this.getErrMsg().substring(this.getErrMsg().lastIndexOf("=") + 1);
	}

	/**
	 * @return the ret
	 */
	public String getRet() {
		return Ret;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return ErrMsg;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return ErrCode;
	}

	/**
	 * @return the showVerifyCode
	 */
	public String getShowVerifyCode() {
		return ShowVerifyCode;
	}

	/**
	 * @param ret
	 *            the ret to set
	 */
	public void setRet(String ret) {
		Ret = ret;
	}

	/**
	 * @param errMsg
	 *            the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}

	/**
	 * @param errCode
	 *            the errCode to set
	 */
	public void setErrCode(String errCode) {
		ErrCode = errCode;
	}

	/**
	 * @param showVerifyCode
	 *            the showVerifyCode to set
	 */
	public void setShowVerifyCode(String showVerifyCode) {
		ShowVerifyCode = showVerifyCode;
	}

}
