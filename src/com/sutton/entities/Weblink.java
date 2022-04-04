package com.sutton.entities;

import com.sutton.partners.Shareable;

public class Weblink extends Bookmark implements Shareable {

	private String url;
	private String host;
	private String htmlPage;
	private  DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if (url.contains("porn") || getTitle().contains("porn") || host.contains("adult")) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Weblink" + super.toString() + "[url=" + url + ", host=" + host + "]";
	}

	@Override
	public String getItemData() {
		StringBuilder itemData = new StringBuilder();
	     
	     itemData.append("<item>");
	     itemData.append("<type>Weblink</type>");
	     itemData.append("<title>").append(getTitle()).append("</title>");
	     itemData.append("<url>").append(url).append("</url>");
	     itemData.append("<host>").append(host).append("</host>");
	     itemData.append("</item>");
	     
	     return itemData.toString();
	}

	public enum  DownloadStatus {
		NOT_ATTEMPTED,
		SUCCESS,
		FAILED,
		NOT_ELIGIBLE;
	}

}
