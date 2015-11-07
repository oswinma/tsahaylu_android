package com.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import com.tsahaylu.www.model.User;


public class UserDTO implements Serializable {

	private String id;

	private String email;

	private String nickname;

	private String country;
	
	private String language;

	private String avatarURL;

	private int status;
	
	private Date signuptime;
	
	public UserDTO()
	{
		
	}
	
	public UserDTO(User u)
	{
		super();
		this.avatarURL=u.getAvatarURL();
		this.id=String.valueOf(u.getId());
		this.email=u.getEmail();
		this.nickname=u.getNickname();
		this.country=u.getCountry();
		this.language=u.getLanguage();
		this.status=u.getStatus();
		this.signuptime=u.getSignuptime();
	}

	public Date getSignuptime() {
		return signuptime;
	}

	public void setSignuptime(Date signuptime) {
		this.signuptime = signuptime;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
