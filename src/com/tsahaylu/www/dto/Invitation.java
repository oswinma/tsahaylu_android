package com.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import com.tsahaylu.www.model.Friend;
import com.tsahaylu.www.model.User;



public class Invitation  implements Serializable{

	private Long id;
	
	private Long fromid;

	private Long toid;
	
	private int status;
	
	private boolean popup;
	
	private String nickname;

	private String avatarURL;
	
	private Date bondtime;

	public Invitation()
	{

	}
	
	public Invitation(Friend f,User u)
	{
		super();
		this.id=f.getId();
		this.fromid=f.getFromid();
		this.toid=f.getToid();
		this.status=f.getStatus();
		this.setPopup(f.isPopup());
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
		this.bondtime=f.getBondtime();
	}
		
	public Date getBondtime() {
		return bondtime;
	}

	public void setBondtime(Date bondtime) {
		this.bondtime = bondtime;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getFromid() {
		return fromid;
	}

	public void setFromid(Long fromid) {
		this.fromid = fromid;
	}

	public Long getToid() {
		return toid;
	}

	public void setToid(Long toid) {
		this.toid = toid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	
}
