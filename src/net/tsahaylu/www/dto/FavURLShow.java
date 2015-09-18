package net.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import net.tsahaylu.www.model.FavURL;
import net.tsahaylu.www.model.URLInfo;
import net.tsahaylu.www.model.User;



public class FavURLShow implements Serializable {

	private Long id;
	
	private Long fromid;

	private Long toid;

	private String url;

	private int status;
	
	private int fstatus;

	private Date sendtime;
	
	private Long serial;
	
	private Date readtime;
	
	private String comment;
	
	private boolean fav;
	
	private String channel;

	private String title;

	private String icon;

	private String host;
	
	private String nickname;
	
	private String avatarURL;
	
	private Long urlid;

	private Long share = 0l;
	
	private Long favs = 0l;	
	
	private int commentnum=0;
	
	public FavURLShow()
	{	
		
	}
	
	public FavURLShow(FavURL fu, URLInfo ui,User u, int num)
	{	
		super(); 
		this.id=fu.getId();
		this.fromid=fu.getFromid();
		this.toid=fu.getToid();
		this.url=ui.getUrl();
		this.status=fu.getStatus();
		this.fstatus=fu.getFstatus();
		this.sendtime=fu.getSendtime();
		this.serial=fu.getSerial();
		this.comment=fu.getComment();
		this.fav=fu.isFav();
		this.channel=fu.getChannel();
		
		this.urlid=ui.getId();		
		this.title=ui.getTitle();
		this.icon=ui.getIcon();
		this.host=ui.getHost();
		this.nickname=u.getNickname();
		this.avatarURL=u.getAvatarURL();
		this.readtime=fu.getReadtime();
		this.setShare(ui.getShare());
		this.setFavs(ui.getFavs());
		this.setCommentnum(num);
	}
	
	
	
	public Long getUrlid() {
		return urlid;
	}



	public void setUrlid(Long urlid) {
		this.urlid = urlid;
	}



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


	public Long getToid() {
		return toid;
	}


	public void setToid(Long toid) {
		this.toid = toid;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFav() {
		return fav;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getFavs() {
		return favs;
	}

	public void setFavs(Long favs) {
		this.favs = favs;
	}

	public Long getShare() {
		return share;
	}

	public void setShare(Long share) {
		this.share = share;
	}

	public int getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}

	
}
