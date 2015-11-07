package com.tsahaylu.www.model;

import java.util.Date;

import com.tsahaylu.www.common.UserStatus;

public class User {


	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String country;
	private String language;
	private String avatarKey;
	private String avatarURL;
	private String code;
	private int status = UserStatus.INVALID;
	private Date signuptime;
	
	public Date getSignuptime() {
		return signuptime;
	}

	public void setSignuptime(Date signuptime) {
		this.signuptime = signuptime;
	}

	public String getAvatarKey() {
		return avatarKey;
	}

	public void setAvatarKey(String avatarKey) {
		this.avatarKey = avatarKey;
	}
	
	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
