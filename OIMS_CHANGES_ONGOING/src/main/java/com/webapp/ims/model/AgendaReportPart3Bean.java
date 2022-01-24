package com.webapp.ims.model;

public class AgendaReportPart3Bean {
	private String id;
	private String applicationid;
	private String detailsOfIncentivesSought;
	private Double   amountOfReliefs;
	private String commentsOfConcernedDepartment ;
	private String incentiveAsPerRules;
	private String justificationComment;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplicationid() {
		return applicationid;
	}
	public void setApplicationid(String applicationid) {
		this.applicationid = applicationid;
	}
	public String getDetailsOfIncentivesSought() {
		return detailsOfIncentivesSought;
	}
	public void setDetailsOfIncentivesSought(String detailsOfIncentivesSought) {
		this.detailsOfIncentivesSought = detailsOfIncentivesSought;
	}

	 
	public Double getAmountOfReliefs() {
		return amountOfReliefs;
	}
	public void setAmountOfReliefs(Double amountOfReliefs) {
		this.amountOfReliefs = amountOfReliefs;
	}
	public String getCommentsOfConcernedDepartment() {
		return commentsOfConcernedDepartment;
	}
	public void setCommentsOfConcernedDepartment(String commentsOfConcernedDepartment) {
		this.commentsOfConcernedDepartment = commentsOfConcernedDepartment;
	}
	public String getIncentiveAsPerRules() {
		return incentiveAsPerRules;
	}
	public void setIncentiveAsPerRules(String incentiveAsPerRules) {
		this.incentiveAsPerRules = incentiveAsPerRules;
	}
	
	public String getjustificationComment() {
		return justificationComment;
	}
	public void setjustificationComment(String justificationComment) {
		this.justificationComment = justificationComment;
	}
	
}
