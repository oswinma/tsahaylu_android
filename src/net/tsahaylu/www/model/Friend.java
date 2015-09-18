package net.tsahaylu.www.model;

import java.util.Date;

import net.tsahaylu.www.common.FriendStatus;


public class Friend {

	private Long id;

	private Long fromid;

	private Long toid;

	private int status = FriendStatus.INVALID;

	private boolean popup = true;
	

	private Date bondtime;
	
	public Date getBondtime() {
		return bondtime;
	}

	public void setBondtime(Date bondtime) {
		this.bondtime = bondtime;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
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

}
