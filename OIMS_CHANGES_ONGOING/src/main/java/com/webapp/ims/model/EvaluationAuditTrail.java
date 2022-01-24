package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shailendra Kumar Rathour
 *
 */
@Entity
@Table(name = "DPT_Evaluation_Audit_Trial", schema = "loc")
public class EvaluationAuditTrail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DPT_EAI_ID")
	private String id;

	@Column(name = "DPT_EAI_User_ID")
	private String userId;

	@Column(name = "DPT_EAI_Fields_Name")
	private String fieldsName;

	@Column(name = "DPT_EAI_Old_Details")
	private String oldDetails;

	@Column(name = "DPT_EAI_New_Details")
	private String newDetails;

	@Column(name = "DPT_EAI_APC_ID")
	private String appliId;

	@Column(name = "DPT_EAI_Proj_Details_ID")
	private String projId;

	@Column(name = "Modify_Date")
	private Date modifyedDate;
	@Column(name = "Created_Date")
	private Date createdDate;

	@Column(name = "Status")
	private String status;

	@Column(name = "Created_By")
	private String createdBY;

	@Column(name = "Modify_By")
	private String modifyedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(String fieldsName) {
		this.fieldsName = fieldsName;
	}

	public String getOldDetails() {
		return oldDetails;
	}

	public void setOldDetails(String oldDetails) {
		this.oldDetails = oldDetails;
	}

	public String getNewDetails() {
		return newDetails;
	}

	public void setNewDetails(String newDetails) {
		this.newDetails = newDetails;
	}

	public String getAppliId() {
		return appliId;
	}

	public void setAppliId(String appliId) {
		this.appliId = appliId;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifyedDate() {
		return modifyedDate;
	}

	public void setModifyedDate(Date modifyedDate) {
		this.modifyedDate = modifyedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBY() {
		return createdBY;
	}

	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}

	public String getModifyedBy() {
		return modifyedBy;
	}

	public void setModifyedBy(String modifyedBy) {
		this.modifyedBy = modifyedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
