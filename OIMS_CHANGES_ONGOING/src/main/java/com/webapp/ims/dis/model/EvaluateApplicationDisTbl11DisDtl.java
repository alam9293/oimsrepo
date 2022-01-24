package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.sql.Time;
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
@Table(name = "Tbl_Tbl11_Dis_Amt_Dtl", schema = "loc")
public class EvaluateApplicationDisTbl11DisDtl implements Serializable {

    private static final long serialVersionUID = 1L;


	@Column(name = "Evaluate_Id")
	private String Evaluate_Id;

	@Column(name = "Tbl11_Dis_LndrBank")
	private String Tbl11_Dis_LndrBank;
	
	@Column(name = "Tbl11_Dis_Date")
	@Temporal(TemporalType.DATE)
	private java.util.Date Tbl11_Dis_Date;

	@Column(name = "Tbl11_Dis_Amt")
	private Double Tbl11_Dis_Amt;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Evaluate_Id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private DisViewEvaluate DisViewEvaluate;

	@Id
	@GeneratedValue(
		    strategy = GenerationType.IDENTITY,
		    generator = "Tbl11_Dis_Id" 
		)
	@SequenceGenerator(name = "tbl11_Dis_Id", sequenceName = "loc.tbl11_Dis_Id",allocationSize=1)
	@GenericGenerator(name = "Tbl11_Dis_Id", strategy = "uuid2")
	@Column(name="Tbl11_Dis_Id", unique=true, nullable=false, precision=10, scale=0)
	private String id;
	
	@Column(name = "Creator")
	private String Creator;
	
	@Column(name = "Creator_Time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date Creator_Time;

	public String getTbl11_Dis_Id() {
		return ""+id;
	}

	
	public String getEvaluate_Id() {
		return Evaluate_Id;
	}

	public void setEvaluate_Id(String evaluate_Id) {
		Evaluate_Id = evaluate_Id;
	}

	public String getTbl11_Dis_LndrBank() {
		return Tbl11_Dis_LndrBank;
	}

	public void setTbl11_Dis_LndrBank(String tbl11_Dis_LndrBank) {
		Tbl11_Dis_LndrBank = tbl11_Dis_LndrBank;
	}

	public Double getTbl11_Dis_Amt() {
		return Tbl11_Dis_Amt;
	}

	public void setTbl11_Dis_Amt(Double tbl11_Dis_Amt) {
		Tbl11_Dis_Amt = tbl11_Dis_Amt;
	}
	
	public java.util.Date getTbl11_Dis_Date() {
		return Tbl11_Dis_Date;
	}

	public void setTbl11_Dis_Date(java.util.Date Disate) {
		Tbl11_Dis_Date = Disate;
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
