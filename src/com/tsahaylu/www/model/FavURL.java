package com.tsahaylu.www.model;

import java.util.Date;

import com.tsahaylu.www.common.FavURLStatus;

public class FavURL {


	private Long id;


	private Long fromid;


	private Long toid;
	

	private Long urlid;


	private int status = FavURLStatus.PENDING;
	

	private int fstatus = FavURLStatus.ARCHIVE;
	

	private Date sendtime;
	

	private Long serial;
	
	private Date readtime;

	private String comment;
	

	private String channel;


	private boolean fav=false;
	
	public Long getSerial() {
		return serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public Long getFromid() {
		return fromid;
	}

	public void setFromid(Long fromid) {
		this.fromid = fromid;
	}
	
	public Long getUrlid() {
		return urlid;
	}

	public void setUrlid(Long urlid) {
		this.urlid = urlid;
	}
	
	public Long getToid() {
		return toid;
	}

	public void setToid(Long toid) {
		this.toid = toid;
	}

	public boolean isFav() {
		return fav;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
