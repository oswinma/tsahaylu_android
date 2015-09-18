package net.tsahaylu.www.model;



public class URLInfo {

	private Long id;


	private String url;
	
	private String title;
	
	private String icon;
	
	private String host;	
	
	private int status = 0;
	
	private Long share = 0l;
	
	private Long favs = 0l;
	
	public Long getShare() {
		return share;
	}

	public void setShare(Long share) {
		this.share = share;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getFavs() {
		return favs;
	}

	public void setFavs(Long favs) {
		this.favs = favs;
	}

	
	
}
