package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subsidycalforroadtransporttqm")
public class SubsidyCalForRoadTransportTQM implements Serializable{
	
	@Id
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	@Column(name="road_transport_concor_inv_no")
	private String subCalRoadTransportConcorInvNo;
	
	@Column(name="road_transport_concor_date")
	private String subCalRoadTransportDate;
	

	@Column(name="road_transport_basic_price")
	private String subCalRoadTransportBasicPrice;

	@Column(name="sub_cal_road_transport_abated_value")
	private String subCalRoadTransportAbatedValue;
	
	@Column(name="road_transport_cgst")
	private String subCalRoadTransportCGST;
	
	@Column(name="road_transport_sgst")
	private String subCalRoadTransportSGST;
	
	@Column(name="road_transport_total_amount")
	private String subCalTotalAmount;
	
	@Column(name="road_transport_eligible_cost_in_lacs")
	private String subCalRoadTransportEligibleCostInLacs;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	public String getSubCalRoadTransportConcorInvNo() {
		return subCalRoadTransportConcorInvNo;
	}

	public void setSubCalRoadTransportConcorInvNo(String subCalRoadTransportConcorInvNo) {
		this.subCalRoadTransportConcorInvNo = subCalRoadTransportConcorInvNo;
	}

	public String getSubCalRoadTransportDate() {
		return subCalRoadTransportDate;
	}

	public void setSubCalRoadTransportDate(String subCalRoadTransportDate) {
		this.subCalRoadTransportDate = subCalRoadTransportDate;
	}

	
	public String getSubCalRoadTransportAbatedValue() {
		return subCalRoadTransportAbatedValue;
	}

	public void setSubCalRoadTransportAbatedValue(String subCalRoadTransportAbatedValue) {
		this.subCalRoadTransportAbatedValue = subCalRoadTransportAbatedValue;
	}

	public String getSubCalRoadTransportBasicPrice() {
		return subCalRoadTransportBasicPrice;
	}

	public void setSubCalRoadTransportBasicPrice(String subCalRoadTransportBasicPrice) {
		this.subCalRoadTransportBasicPrice = subCalRoadTransportBasicPrice;
	}

	

	public String getSubCalRoadTransportCGST() {
		return subCalRoadTransportCGST;
	}

	public void setSubCalRoadTransportCGST(String subCalRoadTransportCGST) {
		this.subCalRoadTransportCGST = subCalRoadTransportCGST;
	}

	public String getSubCalRoadTransportSGST() {
		return subCalRoadTransportSGST;
	}

	public void setSubCalRoadTransportSGST(String subCalRoadTransportSGST) {
		this.subCalRoadTransportSGST = subCalRoadTransportSGST;
	}

	public String getSubCalTotalAmount() {
		return subCalTotalAmount;
	}

	public void setSubCalTotalAmount(String subCalTotalAmount) {
		this.subCalTotalAmount = subCalTotalAmount;
	}

	public String getSubCalRoadTransportEligibleCostInLacs() {
		return subCalRoadTransportEligibleCostInLacs;
	}

	public void setSubCalRoadTransportEligibleCostInLacs(String subCalRoadTransportEligibleCostInLacs) {
		this.subCalRoadTransportEligibleCostInLacs = subCalRoadTransportEligibleCostInLacs;
	}
	
	

}
