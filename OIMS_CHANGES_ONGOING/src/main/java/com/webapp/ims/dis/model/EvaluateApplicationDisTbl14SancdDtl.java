package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Tbl_Tbl14_Sancd_Amt_Dtl", schema = "loc")
public class EvaluateApplicationDisTbl14SancdDtl implements Serializable {

    private static final long serialVersionUID = 1L;


	@Column(name = "Evaluate_Id")
	private String Evaluate_Id;

	@Column(name = "Tbl14_Sancd_LndrBank")
	private String Tbl14_Sancd_LndrBank;
	
	@Column(name = "Tbl14_Sancd_Date")
	@Temporal(TemporalType.DATE)
	private java.util.Date Tbl14_Sancd_Date;

	@Column(name = "Tbl14_Sancd_Amt")
	private Double Tbl14_Sancd_Amt;

	@Column(name = "Tbl14_Sancd_IntrstRate")
	private Double Tbl14_Sancd_IntrstRate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Evaluate_Id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private DisViewEvaluate DisViewEvaluate;

	@Id
	@GeneratedValue(
		    strategy = GenerationType.IDENTITY,
		    generator = "Tbl14_Sacnd_Id" 
		)
	@SequenceGenerator(name = "Tbl14_Sancd_Id", sequenceName = "loc.Tbl14_Sancd_Id",allocationSize=1)
	@GenericGenerator(name = "Tbl14_Sacnd_Id", strategy = "uuid2")
	@Column(name="Tbl14_Sancd_Id", unique=true, nullable=false, precision=10, scale=0)
	private String id;
	
	@Column(name = "Creator")
	private String Creator;
	
	@Column(name = "Creator_Time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date Creator_Time;

	public String getTbl14_Sancd_Id() {
		return ""+id;
	}

	
	public String getEvaluate_Id() {
		return Evaluate_Id;
	}

	public void setEvaluate_Id(String evaluate_Id) {
		Evaluate_Id = evaluate_Id;
	}

	public String getTbl14_Sancd_LndrBank() {
		return Tbl14_Sancd_LndrBank;
	}

	public void setTbl14_Sancd_LndrBank(String tbl14_Sancd_LndrBank) {
		Tbl14_Sancd_LndrBank = tbl14_Sancd_LndrBank;
	}

	public Double getTbl14_Sancd_Amt() {
		return Tbl14_Sancd_Amt;
	}

	public void setTbl14_Sancd_Amt(Double tbl14_Sancd_Amt) {
		Tbl14_Sancd_Amt = tbl14_Sancd_Amt;
	}

	public Double getTbl14_Sancd_IntrstRate() {
		return Tbl14_Sancd_IntrstRate;
	}

	public void setTbl14_Sancd_IntrstRate(Double tbl14_Sancd_IntrstRate) {
		Tbl14_Sancd_IntrstRate = tbl14_Sancd_IntrstRate;
	}
	
	public java.util.Date getTbl14_Sancd_Date() {
		return Tbl14_Sancd_Date;
	}

	public void setTbl14_Sancd_Date(java.util.Date Sancdate) {
		Tbl14_Sancd_Date = Sancdate;
	}
	
	public String getCreator_Id() {
		return Creator;
	}

	public void setCreator_Id(String creator) {
		Creator = creator;
	}
	
	public void setCreator_Time(Date Creator_time) {
		Creator_Time = Creator_time;
	}
}
