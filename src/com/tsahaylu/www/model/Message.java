package com.tsahaylu.www.model;

import java.util.Date;

import com.tsahaylu.www.common.MessageStatus;


public class Message {

	private Long id;


	private Long fromid;


	private Long toid;


	private String type;
	

	private int status = MessageStatus.UNREAD;

	private String content;
	

	private Date sendtime;
	
	private Date readtime;
	

	private Long refid;
	
	public Long getRefid() {
		return refid;
	}

	public void setRefid(Long refid) {
		this.refid = refid;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
