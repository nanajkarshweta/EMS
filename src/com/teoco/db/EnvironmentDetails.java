package com.teoco.db;

import java.util.List;

public class EnvironmentDetails {
	
	private int id;
	private String envName;
	private String productApp;
	private String host;
	private String port;
	private String folder;
	private String url;
	private String login;
	private String comments;
	private String refhist;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getProductApp() {
		return productApp;
	}
	public void setProductApp(String productApp) {
		this.productApp = productApp;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRefhist() {
		return refhist;
	}
	public void setRefhist(String refhist) {
		this.refhist = refhist;
	}

}
