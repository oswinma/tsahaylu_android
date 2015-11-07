package com.tsahaylu.www.dto;

import java.io.Serializable;

import com.tsahaylu.www.model.Friend;
import com.tsahaylu.www.model.User;


public class Contact  implements Serializable{
	
	private String id;

	private String email;

	private String nickname;

	private String avatarURL;

	private Long friendid;
	
	private Long groupid;
	
	private Long groupdataid;
	
	private Long fromid;
	
	private int status;
	
	private boolean popup;

	public Contact()
	{
	}
	
	public Contact(String nickname,String email)
	{
		super();
		this.email=email;
		this.nickname=nickname;
	}
	
	public Contact(User u)
	{	
		this.id=String.valueOf(u.getId());
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
	}
		
	public Contact(User u,Friend f)
	{
		this.id=String.valueOf(u.getId());
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
		this.friendid=f.getId();
		this.fromid=f.getFromid();
		this.status=f.getStatus();
		this.setPopup(f.isPopup());
	}
		
	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public Long getGroupdataid() {
		return groupdataid;
	}

	public void setGroupdataid(Long groupdataid) {
		this.groupdataid = groupdataid;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public Long getFriendid() {
		return friendid;
	}

	public void setFriendid(Long friendid) {
		this.friendid = friendid;
	}

	public Long getFromid() {
		return fromid;
	}

	public void setFromid(Long fromid) {
		this.fromid = fromid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	
	
}
