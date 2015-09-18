package net.tsahaylu.www.model;

import java.util.Date;

public class Comment {


	private Long id;


	private Long fromid;


	private Long toid;
	

	private Long urlid;
	

	private Long favurlid;

	private Date sendtime;
	
	private String content;

	
	private Long like=0l;
	
	private Long unlike=0l;	
	
	public Long getLike() {
		return like;
	}

	public void setLike(Long like) {
		this.like = like;
	}

	public Long getUnlike() {
		return unlike;
	}

	public void setUnlike(Long unlike) {
		this.unlike = unlike;
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

	public Long getUrlid() {
		return urlid;
	}

	public void setUrlid(Long urlid) {
		this.urlid = urlid;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getFavurlid() {
		return favurlid;
	}

	public void setFavurlid(Long favurlid) {
		this.favurlid = favurlid;
	}
	
		
}
