package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/* Author Pankaj Sahu */

@Entity
@Table(name = "Dis_Meeting_Scheduler", schema = "loc")
public class MeetingSchedulerDis implements Serializable {
	private static final long serialVersionUID = 8965318876614489749L;

	@Id
	@Column(name = "MS_ID")
	private String id;

	@Column(name = "MS_AppID")
	private String appId;

	@Column(name = "Commi_Dept_Name")
	private String commiteeDepartment;

	@Column(name = "Schedulder_Subject")
	private String schedulerSubject;

	@Column(name = "Schedulder_Date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date schedulderDate;

	@Column(name = "Schedulder_Time")
	private String time;

	@Column(name = "Schedulder_Location")
	private String schedulerLocation;

	@Column(name = "User_ID")
	private String userId;

	@Column(name = "From_Mail")
	private String fromMail;

	@Column(name = "To_Mail")
	private String toMail;

	@Column(name = "Sched_Created_By")
	private String createdBy;

	@Column(name = "Sched_Modified_By")
	private String modifiedBy;

	@Column(name = "Sched_Created_Date")
	private Date createDate;

	@Column(name = "Sched_Modified_Date")
	private Date modifyDate;

	@Column(name = "Sched_Status")
	private String active;

	private transient String deptEmail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommiteeDepartment() {
		return commiteeDepartment;
	}

	public void setCommiteeDepartment(String commiteeDepartment) {
		this.commiteeDepartment = commiteeDepartment;
	}

	public String getSchedulerSubject() {
		return schedulerSubject;
	}

	public void setSchedulerSubject(String schedulerSubject) {
		this.schedulerSubject = schedulerSubject;
	}

	public Date getSchedulderDate() {
		return schedulderDate;
	}

	public void setSchedulderDate(Date schedulderDate) {
		this.schedulderDate = schedulderDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSchedulerLocation() {
		return schedulerLocation;
	}

	public void setSchedulerLocation(String schedulerLocation) {
		this.schedulerLocation = schedulerLocation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeptEmail() {
		return deptEmail;
	}

	public void setDeptEmail(String deptEmail) {
		this.deptEmail = deptEmail;
	}

}
