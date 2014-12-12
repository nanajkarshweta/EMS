package com.teoco.db;

public class Environment {
	
	private String envName;
	private String productSuite;
	private String envStatus;
	private String envOwner;
	private int testFlag;
	private int envArchived;
	private String use;
	
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName;
	}
	public String getProductSuite() {
		return productSuite;
	}
	public void setProductSuite(String productSuite) {
		this.productSuite = productSuite;
	}
	public String getEnvStatus() {
		return envStatus;
	}
	public void setEnvStatus(String envStatus) {
		this.envStatus = envStatus;
	}
	public String getEnvOwner() {
		return envOwner;
	}
	public void setEnvOwner(String envOwner) {
		this.envOwner = envOwner;
	}
	public int getTestFlag() {
		return testFlag;
	}
	public void setTestFlag(int testFlag) {
		this.testFlag = testFlag;
	}
	public int getEnvArchived() {
		return envArchived;
	}
	public void setEnvArchived(int envArchived) {
		this.envArchived = envArchived;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	
}
