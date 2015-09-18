package net.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import net.tsahaylu.www.model.Comment;
import net.tsahaylu.www.model.User;


public class CommentDTO  implements Serializable {

	private Long id;

	private Long fromid;

	private Long toid;
	
	private Long urlid;
	
	private Long favurlid;
	
	private Date sendtime;
	
	private String content;
	
	private String nickname;
	
	private String tonickname;
	
	private String avatarURL;
	
	
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
	
	
	public CommentDTO()
	{			
	}
	
	public CommentDTO(Comment com,User u,User tu)
	{	
		super();
		this.id=com.getId();
		this.fromid=com.getFromid();
		this.toid=com.getToid();
		this.urlid=com.getUrlid();
		this.favurlid=com.getFavurlid();
		this.sendtime=com.getSendtime();
		this.content=com.getContent();
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
		this.tonickname=tu.getNickname();		
		this.like=com.getLike();
		this.unlike=com.getUnlike();
	}
	
	public CommentDTO(Comment com,User u)
	{	
		this.id=com.getId();
		this.fromid=com.getFromid();
		this.toid=com.getToid();
		this.urlid=com.getUrlid();
		this.favurlid=com.getFavurlid();
		this.sendtime=com.getSendtime();
		this.content=com.getContent();
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();	
		this.like=com.getLike();
		this.unlike=com.getUnlike();
	}
	
	public String getTonickname() {
		return tonickname;
	}

	public void setTonickname(String tonickname) {
		this.tonickname = tonickname;
	}

	public Long getFavurlid() {
		return favurlid;
	}

	public void setFavurlid(Long favurlid) {
		this.favurlid = favurlid;
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
