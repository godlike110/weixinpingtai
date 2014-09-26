package cn.focus.dc.focuswap.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.focus.dc.commons.model.BaseObject;


public class User1 extends BaseObject {


	private String appId;
	

	private String userId;
	

	private String uId;
	

	private String picUrl;
	

	private String birthDay;
	

	private String provice;
	

	private String city;

	public String getUId() {
		return uId;
	}

	public void setUId(String uId) {
		this.uId = uId;
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
