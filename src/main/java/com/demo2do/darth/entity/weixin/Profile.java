/**
 * 
 */
package com.demo2do.darth.entity.weixin;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Downpour
 */
@Embeddable
public class Profile {

	/** 碧遨农业 */
	private String name;
	
	/** 微信号 */
	private String weixinCode;

	/** SUBSCRIBE / SERVICE */
	@Enumerated(EnumType.ORDINAL)
	private AccountType type;
	
	/** true / false */
	private boolean certificated;

	/** 碧遨农业 选择新鲜 选择健康 */
	private String description;

	/** 头像 */
	private String avatar;
	
	/** 二维码 */
	private String qrcodePic;
	
	/** 2395138980 */
	private String fakeId;
	
	private Date createDate;

	/**
	 * The default constructor
	 */
	public Profile() {

	}

	/**
	 * Initialize avatar and qrcode
	 * 
	 * @param avatar
	 */
	public void initialize(String avatar) {
		this.avatar = avatar;
		this.qrcodePic = "qrcode_" + avatar;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the weixinCode
	 */
	public String getWeixinCode() {
		return weixinCode;
	}

	/**
	 * @return the type
	 */
	public AccountType getType() {
		return type;
	}
	
	/**
	 * @return the certificated
	 */
	public boolean isCertificated() {
		return certificated;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	
	/**
	 * @return the qrcodePic
	 */
	public String getQrcodePic() {
		return qrcodePic;
	}

	/**
	 * @return the fakeId
	 */
	public String getFakeId() {
		return fakeId;
	}
	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param weixinCode the weixinCode to set
	 */
	public void setWeixinCode(String weixinCode) {
		this.weixinCode = weixinCode;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(AccountType type) {
		this.type = type;
	}

	/**
	 * @param certificated
	 *            the certificated to set
	 */
	public void setCertificated(boolean certificated) {
		this.certificated = certificated;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/**
	 * @param qrcodePic
	 *            the qrcodePic to set
	 */
	public void setQrcodePic(String qrcodePic) {
		this.qrcodePic = qrcodePic;
	}

	/**
	 * @param fakeId the fakeId to set
	 */
	public void setFakeId(String fakeId) {
		this.fakeId = fakeId;
	}
	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
