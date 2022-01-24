package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dis_EligibleAmt_CIS", schema = "loc")
public class DisEligibleAmtCIS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EligibleAmt_Id")
	private String eligibleAmtId;

	@Column(name = "Apc_Id")
	private String apcId;

	// I Row
	@Column(name = "YearI")
	private String fYI;

	@Column(name = "IntPM_I")
	private String intPMI;

	@Column(name = "Date_DIS_I")
	private String dateofDISI;

	@Column(name = "ActAmt_IP_I")
	private String actAmtIPI;

	@Column(name = "Date_PI_I")
	private String dateofPI;

	@Column(name = "PropInt_ROI_I")
	private String propIntRoiI;

	@Column(name = "PropInt_PA_I")
	private String propIntPAI;

	// II Row
	@Column(name = "YearII")
	private String fYII;

	@Column(name = "IntPM_II")
	private String intPMII;

	@Column(name = "Date_DIS_II")
	private String dateofDISII;

	@Column(name = "ActAmt_IP_II")
	private String actAmtIPII;

	@Column(name = "Date_PI_II")
	private String dateofPII;

	@Column(name = "PropInt_ROI_II")
	private String propIntRoiII;

	@Column(name = "PropInt_PA_II")
	private String propIntPAII;

	// III Row
	@Column(name = "YearIII")
	private String fYIII;

	@Column(name = "IntPM_III")
	private String intPMIII;

	@Column(name = "Date_DIS_III")
	private String dateofDISIII;

	@Column(name = "ActAmt_IP_III")
	private String actAmtIPIII;

	@Column(name = "Date_PI_III")
	private String dateofPIII;

	@Column(name = "PropInt_ROI_III")
	private String propIntRoiIII;

	@Column(name = "PropInt_PA_III")
	private String propIntPAIII;

	// IV Row
	@Column(name = "YearIV")
	private String fYIV;

	@Column(name = "IntPM_IV")
	private String intPMIV;

	@Column(name = "Date_DIS_IV")
	private String dateofDISIV;

	@Column(name = "ActAmt_IP_IV")
	private String actAmtIPIV;

	@Column(name = "Date_PI_IV")
	private String dateofPIV;

	@Column(name = "PropInt_ROI_IV")
	private String propIntRoiIV;

	@Column(name = "PropInt_PA_IV")
	private String propIntPAIV;

	// V Row
	@Column(name = "YearV")
	private String fYV;

	@Column(name = "IntPM_V")
	private String intPMV;

	@Column(name = "Date_DIS_V")
	private String dateofDISV;

	@Column(name = "ActAmt_IP_V")
	private String actAmtIPV;

	@Column(name = "Date_PI_V")
	private String dateofPV;

	@Column(name = "PropInt_ROI_V")
	private String propIntRoiV;

	@Column(name = "PropInt_PA_V")
	private String propIntPAV;

	@Column(name = "Eligible_Amt_CIS_Observe")
	private String ElgAmtcisObserv;

	public String getEligibleAmtId() {
		return eligibleAmtId;
	}

	public void setEligibleAmtId(String eligibleAmtId) {
		this.eligibleAmtId = eligibleAmtId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getfYI() {
		return fYI;
	}

	public void setfYI(String fYI) {
		this.fYI = fYI;
	}

	public String getIntPMI() {
		return intPMI;
	}

	public void setIntPMI(String intPMI) {
		this.intPMI = intPMI;
	}

	public String getDateofDISI() {
		return dateofDISI;
	}

	public void setDateofDISI(String dateofDISI) {
		this.dateofDISI = dateofDISI;
	}

	public String getActAmtIPI() {
		return actAmtIPI;
	}

	public void setActAmtIPI(String actAmtIPI) {
		this.actAmtIPI = actAmtIPI;
	}

	public String getPropIntRoiI() {
		return propIntRoiI;
	}

	public void setPropIntRoiI(String propIntRoiI) {
		this.propIntRoiI = propIntRoiI;
	}

	public String getPropIntPAI() {
		return propIntPAI;
	}

	public void setPropIntPAI(String propIntPAI) {
		this.propIntPAI = propIntPAI;
	}

	public String getfYII() {
		return fYII;
	}

	public void setfYII(String fYII) {
		this.fYII = fYII;
	}

	public String getIntPMII() {
		return intPMII;
	}

	public void setIntPMII(String intPMII) {
		this.intPMII = intPMII;
	}

	public String getDateofDISII() {
		return dateofDISII;
	}

	public void setDateofDISII(String dateofDISII) {
		this.dateofDISII = dateofDISII;
	}

	public String getActAmtIPII() {
		return actAmtIPII;
	}

	public void setActAmtIPII(String actAmtIPII) {
		this.actAmtIPII = actAmtIPII;
	}

	public String getPropIntRoiII() {
		return propIntRoiII;
	}

	public void setPropIntRoiII(String propIntRoiII) {
		this.propIntRoiII = propIntRoiII;
	}

	public String getPropIntPAII() {
		return propIntPAII;
	}

	public void setPropIntPAII(String propIntPAII) {
		this.propIntPAII = propIntPAII;
	}

	public String getfYIII() {
		return fYIII;
	}

	public void setfYIII(String fYIII) {
		this.fYIII = fYIII;
	}

	public String getIntPMIII() {
		return intPMIII;
	}

	public void setIntPMIII(String intPMIII) {
		this.intPMIII = intPMIII;
	}

	public String getDateofDISIII() {
		return dateofDISIII;
	}

	public void setDateofDISIII(String dateofDISIII) {
		this.dateofDISIII = dateofDISIII;
	}

	public String getActAmtIPIII() {
		return actAmtIPIII;
	}

	public void setActAmtIPIII(String actAmtIPIII) {
		this.actAmtIPIII = actAmtIPIII;
	}

	public String getPropIntRoiIII() {
		return propIntRoiIII;
	}

	public void setPropIntRoiIII(String propIntRoiIII) {
		this.propIntRoiIII = propIntRoiIII;
	}

	public String getPropIntPAIII() {
		return propIntPAIII;
	}

	public void setPropIntPAIII(String propIntPAIII) {
		this.propIntPAIII = propIntPAIII;
	}

	public String getfYIV() {
		return fYIV;
	}

	public void setfYIV(String fYIV) {
		this.fYIV = fYIV;
	}

	public String getIntPMIV() {
		return intPMIV;
	}

	public void setIntPMIV(String intPMIV) {
		this.intPMIV = intPMIV;
	}

	public String getDateofDISIV() {
		return dateofDISIV;
	}

	public void setDateofDISIV(String dateofDISIV) {
		this.dateofDISIV = dateofDISIV;
	}

	public String getActAmtIPIV() {
		return actAmtIPIV;
	}

	public void setActAmtIPIV(String actAmtIPIV) {
		this.actAmtIPIV = actAmtIPIV;
	}

	public String getPropIntRoiIV() {
		return propIntRoiIV;
	}

	public void setPropIntRoiIV(String propIntRoiIV) {
		this.propIntRoiIV = propIntRoiIV;
	}

	public String getPropIntPAIV() {
		return propIntPAIV;
	}

	public void setPropIntPAIV(String propIntPAIV) {
		this.propIntPAIV = propIntPAIV;
	}

	public String getfYV() {
		return fYV;
	}

	public void setfYV(String fYV) {
		this.fYV = fYV;
	}

	public String getIntPMV() {
		return intPMV;
	}

	public void setIntPMV(String intPMV) {
		this.intPMV = intPMV;
	}

	public String getDateofDISV() {
		return dateofDISV;
	}

	public void setDateofDISV(String dateofDISV) {
		this.dateofDISV = dateofDISV;
	}

	public String getActAmtIPV() {
		return actAmtIPV;
	}

	public void setActAmtIPV(String actAmtIPV) {
		this.actAmtIPV = actAmtIPV;
	}

	public String getPropIntRoiV() {
		return propIntRoiV;
	}

	public void setPropIntRoiV(String propIntRoiV) {
		this.propIntRoiV = propIntRoiV;
	}

	public String getPropIntPAV() {
		return propIntPAV;
	}

	public void setPropIntPAV(String propIntPAV) {
		this.propIntPAV = propIntPAV;
	}

	public String getElgAmtcisObserv() {
		return ElgAmtcisObserv;
	}

	public void setElgAmtcisObserv(String elgAmtcisObserv) {
		ElgAmtcisObserv = elgAmtcisObserv;
	}

	public String getDateofPI() {
		return dateofPI;
	}

	public void setDateofPI(String dateofPI) {
		this.dateofPI = dateofPI;
	}

	public String getDateofPII() {
		return dateofPII;
	}

	public void setDateofPII(String dateofPII) {
		this.dateofPII = dateofPII;
	}

	public String getDateofPIII() {
		return dateofPIII;
	}

	public void setDateofPIII(String dateofPIII) {
		this.dateofPIII = dateofPIII;
	}

	public String getDateofPIV() {
		return dateofPIV;
	}

	public void setDateofPIV(String dateofPIV) {
		this.dateofPIV = dateofPIV;
	}

	public String getDateofPV() {
		return dateofPV;
	}

	public void setDateofPV(String dateofPV) {
		this.dateofPV = dateofPV;
	}

}
