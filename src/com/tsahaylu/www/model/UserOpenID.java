package com.tsahaylu.www.model;

import java.util.Date;


public class UserOpenID {

	private Long id;
	private String openid_url;
	private String user_id;
	private String provider_id;
	private String openid_provider;
	private Date attachtime;

	public Date getAttachtime() {
		return attachtime;
	}

	public void setAttachtime(Date attachtime) {
		this.attachtime = attachtime;
	}

	public String getOpenid_provider() {
		return openid_provider;
	}

	public void setOpenid_provider(String openid_provider) {
		this.openid_provider = openid_provider;
	}
	
	public String getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}

	public String getOpenid_url() {
		return openid_url;
	}

	public void setOpenid_url(String openid_url) {
		this.openid_url = openid_url;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
