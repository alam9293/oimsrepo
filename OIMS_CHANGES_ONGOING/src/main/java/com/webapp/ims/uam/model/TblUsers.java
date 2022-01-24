package com.webapp.ims.uam.model;

import java.io.Serializable;
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
@Table(name = "Tbl_Users", schema = "loc")
public class TblUsers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	/*@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "\"Users_ID_seq\"")
	@SequenceGenerator(name = "\"Users_ID_seq\"", sequenceName = "loc.\"Users_ID_seq\"",allocationSize=1)*/
	@Column(name = "ID")
	private String id;

	@Column(name = "User_Name")
	private String userName;

	@Column(name = "Password")
	private String password;

	@Column(name = "User_First_Name")
	private String firstName;
	
	@Column(name = "User_Middle_Name")
	private String middleName;
	
	@Column(name = "User_Last_Name")
	private String lastName;

	@Column(name = "Department_Name")
	private String department;
	
	@Column(name = "Created_By")
	private String createdBy;
	
	

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDate;

	@Column(name = "Creator_Ip_Address")
	private String CreatorIpAddress;
	
	@Column(name = "Approved_By")
	private String approvedBy;
	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "Approver_Ip_Address")
	private String approverIpAddress;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "Role_id")
	private String roleId;
	
	@Column(name = "Group_Id")
	private String groupId;
	
	
	@Column(name = "Logged_Status")
	private String loggedStatus;
	
	@Column(name = "Last_Logged")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogged;

	@Column(name = "Current_Session_Id")
	private String currentSessionId;
	
	@Column(name = "A_SessionId")
	private String aSessionId;
	
	@Column(name = "Current_Attempts")
	private String currentAttempts;
	
	@Column(name = "Expiry_Date")
	@Temporal(TemporalType.DATE)
	private Date expiryDate;
	
	@Column(name = "Expire_Status")
	private String expireStatus;
	
	@Column(name = "Usr_Active_Status")
	private String usrActiveStatus;
	
	@Column(name = "Usr_Status")
	private String usrStatus;
	
	@Column(name = "Idms_Status")
	private String IdmsStatus;
	
	@Column(name = "Allowed_Forms")
	private String allowedForms;
	
	@Column(name = "Logout_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logoutDate;
	
	@Column(name = "Locked_Status")
	private String lockedStatus;

	
	public void setid(String id) {
		this.id = id;
	}
	
	public String getid() {
		return ""+id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public String getCreatorIpAddress() {
		return CreatorIpAddress;
	}

	public void setCreatorIpAddress(String creatorIpAddress) {
		CreatorIpAddress = creatorIpAddress;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApproverIpAddress() {
		return approverIpAddress;
	}

	public void setApproverIpAddress(String approverIpAddress) {
		this.approverIpAddress = approverIpAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLoggedStatus() {
		return loggedStatus;
	}

	public void setLoggedStatus(String loggedStatus) {
		this.loggedStatus = loggedStatus;
	}

	public Date getLastLogged() {
		return lastLogged;
	}

	public void setLastLogged(Date lastLogged) {
		this.lastLogged = lastLogged;
	}

	public String getCurrentSessionId() {
		return currentSessionId;
	}

	public void setCurrentSessionId(String currentSessionId) {
		this.currentSessionId = currentSessionId;
	}

	public String getCurrentAttempts() {
		return currentAttempts;
	}

	public void setCurrentAttempts(String currentAttempts) {
		this.currentAttempts = currentAttempts;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(String expireStatus) {
		this.expireStatus = expireStatus;
	}

	public String getUsrActiveStatus() {
		return usrActiveStatus;
	}

	public void setUsrActiveStatus(String usrActiveStatus) {
		this.usrActiveStatus = usrActiveStatus;
	}

	public String getUsrStatus() {
		return usrStatus;
	}

	public void setUsrStatus(String usrStatus) {
		this.usrStatus = usrStatus;
	}

	public String getIdmsStatus() {
		return IdmsStatus;
	}

	public void setIdmsStatus(String idmsStatus) {
		IdmsStatus = idmsStatus;
	}
    
	public String getAllowedForms() {
		return allowedForms;
	}

	public void setAllowedForms(String allowedForms) {
		this.allowedForms = allowedForms;
	}
	

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(String lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public String getaSessionId() {
		return aSessionId;
	}

	public void setaSessionId(String aSessionid) {
		this.aSessionId = aSessionid;
	}

}
