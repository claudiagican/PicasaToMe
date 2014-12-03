package cgi.picasa;

/**
 * Class that maps the settings file (.ini)
 * */
public class Settings {

	private boolean isProxy = false;
	private String proxyUser;
	private String proxyPassword;
	private String proxyHost;
	private String proxyPort;
	private String picasaUser;
	private String picasaPassword;
	private String picasaAlbumId;
	private int photoDownloadSize = 400;
	private int photoStartIndex = 1;
	private int photoMaxResults = 1000; 
	private String lightboxRel = "";
	private int lightboxLinkMax = 800;
	
	private String defaultText = "";
	
	private int tableColsNum = 2;
	
	private int htmlType = 1;
	
	public String getProxyUser() {
		return proxyUser;
	}
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	public String getProxyPassword() {
		return proxyPassword;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getPicasaUser() {
		return picasaUser;
	}
	public void setPicasaUser(String picasaUser) {
		this.picasaUser = picasaUser;
	}
	public String getPicasaPassword() {
		return picasaPassword;
	}
	public void setPicasaPassword(String picasaPassword) {
		this.picasaPassword = picasaPassword;
	}
	public String getPicasaAlbumId() {
		return picasaAlbumId;
	}
	public void setPicasaAlbumId(String picasaAlbumId) {
		this.picasaAlbumId = picasaAlbumId;
	}
	public int getPhotoDownloadSize() {
		return photoDownloadSize;
	}
	public void setPhotoDownloadSize(int photoDownloadSize) {
		this.photoDownloadSize = photoDownloadSize;
	}
	public boolean isProxy() {
		return isProxy;
	}
	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}
	public int getHtmlType() {
		return htmlType;
	}
	public void setHtmlType(int htmlType) {
		this.htmlType = htmlType;
	}
	public int getPhotoStartIndex() {
		return photoStartIndex;
	}
	public void setPhotoStartIndex(int photoStartIndex) {
		this.photoStartIndex = photoStartIndex;
	}
	public int getPhotoMaxResults() {
		return photoMaxResults;
	}
	public void setPhotoMaxResults(int photoMaxResults) {
		this.photoMaxResults = photoMaxResults;
	}
	public int getTableColsNum() {
		return tableColsNum;
	}
	public void setTableColsNum(int tableColsNum) {
		this.tableColsNum = tableColsNum;
	}
	public String getLightboxRel() {
		return lightboxRel;
	}
	public void setLightboxRel(String lightboxRel) {
		this.lightboxRel = lightboxRel;
	}
	public int getLightboxLinkMax() {
		return lightboxLinkMax;
	}
	public void setLightboxLinkMax(int lightboxLinkMax) {
		this.lightboxLinkMax = lightboxLinkMax;
	}
	public String getDefaultText() {
		return defaultText;
	}
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

}
