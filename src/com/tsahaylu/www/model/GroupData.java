package com.tsahaylu.www.model;

import java.util.Date;

import com.tsahaylu.www.common.GroupDataStatus;



public class GroupData {


	private Long id;


	private Long toid;


	private String groupid;
	

	private int status=GroupDataStatus.INVALID;

	private Date addtime;
	
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getToid() {
		return toid;
	}

	public void setToid(Long toid) {
		this.toid = toid;
	}

}
