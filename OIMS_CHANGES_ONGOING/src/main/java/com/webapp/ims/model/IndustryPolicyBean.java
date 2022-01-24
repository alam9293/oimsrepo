/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.model;

/**
 * @author dell
 */
public class IndustryPolicyBean {

	private String appId;
	private String unitId;
	private String processIndustryID;
	private String policyCode;
	private String policyName;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getProcessIndustryID() {
		return processIndustryID;
	}

	public void setProcessIndustryID(String processIndustryID) {
		this.processIndustryID = processIndustryID;
	}

	public String getPolicyCode() {
		return policyCode;
	}

	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}
