package com.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.common.MessageType;
import com.tsahaylu.www.model.Message;
import com.tsahaylu.www.model.User;



public class MessageInfo  implements Serializable {

	private Long id;
	
	private Long fromid;

	private Long toid;

	private String type;
	
	private int status;

	private String content;
	
	private String nickname;
	
	private String jump_link;

	private String avatarURL;
	
	private Date sendtime;
	
	private Date readtime;
	
	private Long refid;

	public MessageInfo()
	{

	}
	
	public MessageInfo(Message m,User u)
	{
		super();
		this.id=m.getId();
		this.fromid=m.getFromid();
		this.toid=m.getToid();
		this.type=m.getType();
		this.content=m.getContent();
		this.status=m.getStatus();
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
		this.sendtime=m.getSendtime();
		this.readtime=m.getReadtime();
		this.refid=m.getRefid();
		
		if (this.type.equals(MessageType.FAVURL))
		{
			this.jump_link=Constants.PAGE_HOME;
		}
		else
		if (this.type.equals(MessageType.FRIEND))
		{
				this.jump_link=Constants.PAGE_FRIENDS;
		}
		else
		if (this.type.equals(MessageType.INVITATION))
		{
				this.jump_link=Constants.PAGE_INVITATION;
		}
	}
	
	public Long getRefid() {
		return refid;
	}

	public void setRefid(Long refid) {
		this.refid = refid;
	}

	public String getJump_link() {
		return jump_link;
	}

	public void setJump_link(String jump_link) {
		this.jump_link = jump_link;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	
	
}
