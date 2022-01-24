/**
 * Author:: Pankaj
* Created on:: 15/06/2021 
 */

package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dis_Eval_Mandi_Fee_Exemption", schema = "loc")
public class MandiFeeExemption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Mandi_Fee_Exe_Id")
	private String mandiFeeExeId;

	@Column(name = "Apc_Id")
	private String apcId;

	@Column(name = "Mandi_Fee_Exe_Fin_Yr1")
	private String mandiFeeExeFinYr1;

	@Column(name = "mandiFeeDateFrom1")
	private String mandiFeeDateFrom1;
	@Column(name = "mandiFeeDateTo1")
	private String mandiFeeDateTo1;
	@Column(name = "claimMandiFeeExe1")
	private String claimMandiFeeExe1;

	@Column(name = "Avail_Mandi_Fee_Exe1")
	private String AvailMandiFeeExe1;

	@Column(name = "Mandi_Fee_Exe_Fin_Yr2")
	private String mandiFeeExeFinYr2;

	@Column(name = "mandiFeeDateFrom2")
	private String mandiFeeDateFrom2;
	@Column(name = "mandiFeeDateTo2")
	private String mandiFeeDateTo2;
	@Column(name = "claimMandiFeeExe2")
	private String claimMandiFeeExe2;

	@Column(name = "Avail_Mandi_Fee_Exe2")
	private String AvailMandiFeeExe2;

	@Column(name = "Mandi_Fee_Exe_Fin_Yr3")
	private String mandiFeeExeFinYr3;

	@Column(name = "mandiFeeDateFrom3")
	private String mandiFeeDateFrom3;
	@Column(name = "mandiFeeDateTo3")
	private String mandiFeeDateTo3;
	@Column(name = "claimMandiFeeExe3")
	private String claimMandiFeeExe3;

	@Column(name = "Avail_Mandi_Fee_Exe3")
	private String AvailMandiFeeExe3;

	@Column(name = "Mandi_Fee_Exe_Fin_Yr4")
	private String mandiFeeExeFinYr4;

	@Column(name = "mandiFeeDateFrom4")
	private String mandiFeeDateFrom4;
	@Column(name = "mandiFeeDateTo4")
	private String mandiFeeDateTo4;
	@Column(name = "claimMandiFeeExe4")
	private String claimMandiFeeExe4;

	@Column(name = "Avail_Mandi_Fee_Exe4")
	private String AvailMandiFeeExe4;

	@Column(name = "Mandi_Fee_Exe_Fin_Yr5")
	private String mandiFeeExeFinYr5;

	@Column(name = "mandiFeeDateFrom5")
	private String mandiFeeDateFrom5;
	@Column(name = "mandiFeeDateTo5")
	private String mandiFeeDateTo5;
	@Column(name = "claimMandiFeeExe5")
	private String claimMandiFeeExe5;

	@Column(name = "Avail_Mandi_Fee_Exe5")
	private String AvailMandiFeeExe5;

	@Column(name = "Ttl_Avail_Mandi_Fee_Exe")
	private String ttlAvailMandiFeeExe;

	@Column(name = "Mandi_Fee_Exe_Observ")
	private String mandiFeeExeObserv;

	public String getMandiFeeExeId() {
		return mandiFeeExeId;
	}

	public void setMandiFeeExeId(String mandiFeeExeId) {
		this.mandiFeeExeId = mandiFeeExeId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getMandiFeeExeFinYr1() {
		return mandiFeeExeFinYr1;
	}

	public void setMandiFeeExeFinYr1(String mandiFeeExeFinYr1) {
		this.mandiFeeExeFinYr1 = mandiFeeExeFinYr1;
	}

	public String getAvailMandiFeeExe1() {
		return AvailMandiFeeExe1;
	}

	public void setAvailMandiFeeExe1(String availMandiFeeExe1) {
		AvailMandiFeeExe1 = availMandiFeeExe1;
	}

	public String getMandiFeeExeFinYr2() {
		return mandiFeeExeFinYr2;
	}

	public void setMandiFeeExeFinYr2(String mandiFeeExeFinYr2) {
		this.mandiFeeExeFinYr2 = mandiFeeExeFinYr2;
	}

	public String getAvailMandiFeeExe2() {
		return AvailMandiFeeExe2;
	}

	public void setAvailMandiFeeExe2(String availMandiFeeExe2) {
		AvailMandiFeeExe2 = availMandiFeeExe2;
	}

	public String getMandiFeeExeFinYr3() {
		return mandiFeeExeFinYr3;
	}

	public void setMandiFeeExeFinYr3(String mandiFeeExeFinYr3) {
		this.mandiFeeExeFinYr3 = mandiFeeExeFinYr3;
	}

	public String getAvailMandiFeeExe3() {
		return AvailMandiFeeExe3;
	}

	public void setAvailMandiFeeExe3(String availMandiFeeExe3) {
		AvailMandiFeeExe3 = availMandiFeeExe3;
	}

	public String getMandiFeeExeFinYr4() {
		return mandiFeeExeFinYr4;
	}

	public void setMandiFeeExeFinYr4(String mandiFeeExeFinYr4) {
		this.mandiFeeExeFinYr4 = mandiFeeExeFinYr4;
	}

	public String getAvailMandiFeeExe4() {
		return AvailMandiFeeExe4;
	}

	public void setAvailMandiFeeExe4(String availMandiFeeExe4) {
		AvailMandiFeeExe4 = availMandiFeeExe4;
	}

	public String getMandiFeeExeFinYr5() {
		return mandiFeeExeFinYr5;
	}

	public void setMandiFeeExeFinYr5(String mandiFeeExeFinYr5) {
		this.mandiFeeExeFinYr5 = mandiFeeExeFinYr5;
	}

	public String getAvailMandiFeeExe5() {
		return AvailMandiFeeExe5;
	}

	public void setAvailMandiFeeExe5(String availMandiFeeExe5) {
		AvailMandiFeeExe5 = availMandiFeeExe5;
	}

	public String getTtlAvailMandiFeeExe() {
		return ttlAvailMandiFeeExe;
	}

	public void setTtlAvailMandiFeeExe(String ttlAvailMandiFeeExe) {
		this.ttlAvailMandiFeeExe = ttlAvailMandiFeeExe;
	}

	public String getMandiFeeExeObserv() {
		return mandiFeeExeObserv;
	}

	public void setMandiFeeExeObserv(String mandiFeeExeObserv) {
		this.mandiFeeExeObserv = mandiFeeExeObserv;
	}

	public String getMandiFeeDateFrom1() {
		return mandiFeeDateFrom1;
	}

	public void setMandiFeeDateFrom1(String mandiFeeDateFrom1) {
		this.mandiFeeDateFrom1 = mandiFeeDateFrom1;
	}

	public String getMandiFeeDateTo1() {
		return mandiFeeDateTo1;
	}

	public void setMandiFeeDateTo1(String mandiFeeDateTo1) {
		this.mandiFeeDateTo1 = mandiFeeDateTo1;
	}

	public String getClaimMandiFeeExe1() {
		return claimMandiFeeExe1;
	}

	public void setClaimMandiFeeExe1(String claimMandiFeeExe1) {
		this.claimMandiFeeExe1 = claimMandiFeeExe1;
	}

	public String getMandiFeeDateFrom2() {
		return mandiFeeDateFrom2;
	}

	public void setMandiFeeDateFrom2(String mandiFeeDateFrom2) {
		this.mandiFeeDateFrom2 = mandiFeeDateFrom2;
	}

	public String getMandiFeeDateTo2() {
		return mandiFeeDateTo2;
	}

	public void setMandiFeeDateTo2(String mandiFeeDateTo2) {
		this.mandiFeeDateTo2 = mandiFeeDateTo2;
	}

	public String getClaimMandiFeeExe2() {
		return claimMandiFeeExe2;
	}

	public void setClaimMandiFeeExe2(String claimMandiFeeExe2) {
		this.claimMandiFeeExe2 = claimMandiFeeExe2;
	}

	public String getMandiFeeDateFrom3() {
		return mandiFeeDateFrom3;
	}

	public void setMandiFeeDateFrom3(String mandiFeeDateFrom3) {
		this.mandiFeeDateFrom3 = mandiFeeDateFrom3;
	}

	public String getMandiFeeDateTo3() {
		return mandiFeeDateTo3;
	}

	public void setMandiFeeDateTo3(String mandiFeeDateTo3) {
		this.mandiFeeDateTo3 = mandiFeeDateTo3;
	}

	public String getClaimMandiFeeExe3() {
		return claimMandiFeeExe3;
	}

	public void setClaimMandiFeeExe3(String claimMandiFeeExe3) {
		this.claimMandiFeeExe3 = claimMandiFeeExe3;
	}

	public String getMandiFeeDateFrom4() {
		return mandiFeeDateFrom4;
	}

	public void setMandiFeeDateFrom4(String mandiFeeDateFrom4) {
		this.mandiFeeDateFrom4 = mandiFeeDateFrom4;
	}

	public String getMandiFeeDateTo4() {
		return mandiFeeDateTo4;
	}

	public void setMandiFeeDateTo4(String mandiFeeDateTo4) {
		this.mandiFeeDateTo4 = mandiFeeDateTo4;
	}

	public String getClaimMandiFeeExe4() {
		return claimMandiFeeExe4;
	}

	public void setClaimMandiFeeExe4(String claimMandiFeeExe4) {
		this.claimMandiFeeExe4 = claimMandiFeeExe4;
	}

	public String getMandiFeeDateFrom5() {
		return mandiFeeDateFrom5;
	}

	public void setMandiFeeDateFrom5(String mandiFeeDateFrom5) {
		this.mandiFeeDateFrom5 = mandiFeeDateFrom5;
	}

	public String getMandiFeeDateTo5() {
		return mandiFeeDateTo5;
	}

	public void setMandiFeeDateTo5(String mandiFeeDateTo5) {
		this.mandiFeeDateTo5 = mandiFeeDateTo5;
	}

	public String getClaimMandiFeeExe5() {
		return claimMandiFeeExe5;
	}

	public void setClaimMandiFeeExe5(String claimMandiFeeExe5) {
		this.claimMandiFeeExe5 = claimMandiFeeExe5;
	}

}
