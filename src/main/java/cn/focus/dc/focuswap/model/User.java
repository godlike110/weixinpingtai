package cn.focus.dc.focuswap.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.focus.dc.commons.model.BaseObject;

@XStreamAlias("result")
public class User extends BaseObject {

	@XStreamAlias("regappid")
	private String appId;
	
	@XStreamAlias("userid")
	private String userId;
	
	@XStreamAlias("uid")
	private String uid;
	
	@XStreamAlias("avatarurl")
	private String picUrl;
	
	@XStreamAlias("birthday")
	private String birthDay;
	
	@XStreamAlias("province")
	private String provice;
	
	@XStreamAlias("city")
	private String city;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
