package com.webapp.ims.food.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "policy")
public class Policy implements Serializable {
	/*
	 * public String getModelClass() { return modelClass; }
	 * 
	 * public void setModelClass(String modelClass) { this.modelClass = modelClass;
	 * }
	 */

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * @OneToMany(mappedBy = "form") private Set<Form> forms;
	 * 
	 *//**
		 * @return the forms
		 */
	/*
	 * public Set<Form> getForms() { return forms; }
	 * 
	 *//**
		 * @param forms the forms to set
		 */
	/*
	 * public void setForms(Set<Form> forms) { this.forms = forms; }
	 * 
	 *//**
		 * @return the formula
		 */

	/*
	 * public Set<Formula> getFormula() { return formula; }
	 * 
	 *//**
		 * @param formula the formula to set
		 *//*
			 * public void setFormula(Set<Formula> formula) { this.formula = formula; }
			 * 
			 * @OneToMany(mappedBy = "formula") private Set<Formula> formula;
			 */

	/*
	 * @Lob
	 * 
	 * @Column(name = "modelClass") private String modelClass;
	 */

	@Column(name = "createdDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdDate;

	@Column(name = "createdBy")
	private String createdBy;

	@Column(name = "modifiedBy")
	private String modifiedBy;

	@Column(name = "modifiedDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate modifiedDate;

	@Column(name = "groupId")
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifact_id() {
		return artifact_id;
	}

	public void setArtifact_id(String artifact_id) {
		this.artifact_id = artifact_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "artifact_id")
	private String artifact_id;

	@Column(name = "version")
	private String version;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "policy_id")
	private String policy_id;

	public String getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(String policy_id) {
		this.policy_id = policy_id;
	}

	public LocalDate getPolicyPeriodStartDate() {
		return policyPeriodStartDate;
	}

	public void setPolicyPeriodStartDate(LocalDate policyPeriodStartDate) {
		this.policyPeriodStartDate = policyPeriodStartDate;
	}

	public LocalDate getPolicyPeriodEndDate() {
		return policyPeriodEndDate;
	}

	public void setPolicyPeriodEndDate(LocalDate policyPeriodEndDate) {
		this.policyPeriodEndDate = policyPeriodEndDate;
	}

	public String getPolicyDepartment() {
		return policyDepartment;
	}

	public void setPolicyDepartment(String policyDepartment) {
		this.policyDepartment = policyDepartment;
	}

	public byte[] getPolicyGO() {
		return policyGO;
	}

	public void setPolicyGO(byte[] policyGO) {
		this.policyGO = policyGO;
	}

	public String getCapitalInvestment() {
		return capitalInvestment;
	}

	public void setCapitalInvestment(String capitalInvestment) {
		this.capitalInvestment = capitalInvestment;
	}

	@Column(name = "policy_name")
	private String policy_name;

	@Column(name = "policyPeriodStartDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate policyPeriodStartDate;

	@Column(name = "policyPeriodEndDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate policyPeriodEndDate;

	@Column(name = "policyDepartment")
	private String policyDepartment;

	private byte[] policyGO;

	@Column(name = "policy_go_name")
	private String policy_go_name;

	@Column(name = "capitalInvestment")
	private String capitalInvestment;

	/*
	 * @Column(name = "formName") private String formName;
	 * 
	 * public String getFormName() { return formName; }
	 * 
	 * public void setFormName(String formName) { this.formName = formName; }
	 */

	private String entry_point;

	public String getEntry_point() {
		return entry_point;
	}

	public void setEntry_point(String entry_point) {
		this.entry_point = entry_point;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;

	private String updated_host;

	public String getPolicy_name() {
		return policy_name;
	}

	public void setPolicy_name(String policy_name) {
		this.policy_name = policy_name;
	}

	public String getPolicy_go_name() {
		return policy_go_name;
	}

	public void setPolicy_go_name(String policy_go_name) {
		this.policy_go_name = policy_go_name;
	}

	public String getUpdated_host() {
		return updated_host;
	}

	public void setUpdated_host(String updated_host) {
		this.updated_host = updated_host;
	}

	public String getIs_approved() {
		return Is_approved;
	}

	public void setIs_approved(String is_approved) {
		Is_approved = is_approved;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPolicy_go_file_size() {
		return policy_go_file_size;
	}

	public void setPolicy_go_file_size(String policy_go_file_size) {
		this.policy_go_file_size = policy_go_file_size;
	}

	public String getPolicy_go_mime_type() {
		return policy_go_mime_type;
	}

	public void setPolicy_go_mime_type(String policy_go_mime_type) {
		this.policy_go_mime_type = policy_go_mime_type;
	}

	public String getPolicy_go_file_type() {
		return policy_go_file_type;
	}

	public void setPolicy_go_file_type(String policy_go_file_type) {
		this.policy_go_file_type = policy_go_file_type;
	}

	private String Is_approved;
	private String host;
	private String uri;
	private String policy_go_file_size;
	private String policy_go_mime_type;
	private String policy_go_file_type;

	@Column(name = "createdDateTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss.SSS Z")
	private LocalDateTime createdDateTime;

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Column(name = "modifiedDateTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss.SSS Z")
	private LocalDateTime modifiedDateTime;

	private String container;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

}