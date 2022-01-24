package com.webapp.ims.uam.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Tbl_Pending_Works", schema = "loc")
public class TblPendingWorks  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "\"Pending_Works_Id_Seq\"")
	@SequenceGenerator(name = "\"Pending_Works_Id_Seq\"", sequenceName = "loc.\"Pending_Works_Id_Seq\"",allocationSize=1)
	@Column(name = "Task_Id")
	private long taskId;

	@Column(name = "Task_Query")
	private String taskQuery;

	@Column(name = "Task_Create_Date")
	@Temporal(TemporalType.DATE)
	private Date taskCreateDate;

	@Column(name = "Task_Message")
	private String taskMessage;

	@Column(name = "Task_Subject")
	private String taskSubject;
	
	@Column(name = "Activity")
	private String activity;

	@Column(name = "Module_Name")
	private String moduleName;

	@Column(name = "Task_Purpose")
	private String taskPurpose;

	@Column(name = "Task_Status")
	private int taskStatus;
	
	@Column(name = "Task_Creator_Role")
	private String taskCreatorRole;

	@Column(name = "Task_Approver_Role")
	private String taskApproverRole;
	
	@Column(name = "Creator")
	private String creator;

	@Column(name = "Creator_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creatorDate;
	
	@Column(name = "Approver")
	private String approver;
	
	@Column(name = "Approver_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approverDate;

	@Column(name = "Approver_Display_String")
	private String approverDisplayString;

	@Column(name = "Idms_Status")
	private String idmsStatus;
	
	@Column(name = "Whereclause")
	private String whereClause;
	
	
	@Column(name = "Creator_Ip_Address")
	private String creatorIpAddress;
	
	@Column(name = "Approver_Ip_Address")
	private String approverIpAddress;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskQuery() {
		return taskQuery;
	}

	public void setTaskQuery(String taskQuery) {
		this.taskQuery = taskQuery;
	}

	public Date getTaskCreateDate() {
		return taskCreateDate;
	}

	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public String getTaskMessage() {
		return taskMessage;
	}

	public void setTaskMessage(String taskMessage) {
		this.taskMessage = taskMessage;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTaskPurpose() {
		return taskPurpose;
	}

	public void setTaskPurpose(String taskPurpose) {
		this.taskPurpose = taskPurpose;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskCreatorRole() {
		return taskCreatorRole;
	}

	public void setTaskCreatorRole(String taskCreatorRole) {
		this.taskCreatorRole = taskCreatorRole;
	}

	public String getTaskApproverRole() {
		return taskApproverRole;
	}

	public void setTaskApproverRole(String taskApproverRole) {
		this.taskApproverRole = taskApproverRole;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(Date creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApproverDate() {
		return approverDate;
	}

	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	public String getApproverDisplayString() {
		return approverDisplayString;
	}

	public void setApproverDisplayString(String approverDisplayString) {
		this.approverDisplayString = approverDisplayString;
	}

	public String getIdmsStatus() {
		return idmsStatus;
	}

	public void setIdmsStatus(String idmsStatus) {
		this.idmsStatus = idmsStatus;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getCreatorIpAddress() {
		return creatorIpAddress;
	}

	public void setCreatorIpAddress(String creatorIpAddress) {
		this.creatorIpAddress = creatorIpAddress;
	}

	public String getApproverIpAddress() {
		return approverIpAddress;
	}

	public void setApproverIpAddress(String approverIpAddress) {
		this.approverIpAddress = approverIpAddress;
	}

	
	
}
