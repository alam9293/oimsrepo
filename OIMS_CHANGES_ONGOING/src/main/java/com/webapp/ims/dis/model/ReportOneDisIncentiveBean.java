package com.webapp.ims.dis.model;

import java.util.ArrayList;
import java.util.List;

import com.webapp.ims.model.NewProjectDetails;

public class ReportOneDisIncentiveBean {
String appId;
	String eligibility; 
	String compliance;
	String observation;
	List<NewProjectDetails> newProjList =new ArrayList<>();
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	public String getCompliance() {
		return compliance;
	}
	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public List<NewProjectDetails> getNewProjList() {
		return newProjList;
	}
	public void setNewProjList(List<NewProjectDetails> newProjList) {
		this.newProjList = newProjList;
	}
	
	
 
	 	
}
