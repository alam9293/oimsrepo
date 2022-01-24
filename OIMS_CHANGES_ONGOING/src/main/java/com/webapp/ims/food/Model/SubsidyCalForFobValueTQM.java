package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subsidycalforfobvaluetqm")
public class SubsidyCalForFobValueTQM implements Serializable{
	
	@Id
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	@Column(name="sub_cal_for_fob_value_shipping_bill_no")
	private String subCalForFobValueShippingBillNo;
	
	
	@Column(name="sub_cal_for_fob_value_date")
	private String subCalForFobValueDate;
	
	@Column(name="sub_cal_for_fob_value")
	private String subCalForFobValue;
	
	
	@Column(name="sub_cal_for_fob_value_cgst")
	private String subCalForFobValueCGST;
	
	
	@Column(name="sub_cal_for_fob_value_sgst")
	private String subCalForFobValueSGST;
	
	@Column(name="sub_cal_for_fob_value_total_amount")
	private String subCalForFobValueTotalAmount;
	
	@Column(name="sub_cal_for_fob_value_eligible_cost_in_lacs")
	private String subCalForFobValueEligibleCostInLacs;

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

	public String getSubCalForFobValueShippingBillNo() {
		return subCalForFobValueShippingBillNo;
	}

	public void setSubCalForFobValueShippingBillNo(String subCalForFobValueShippingBillNo) {
		this.subCalForFobValueShippingBillNo = subCalForFobValueShippingBillNo;
	}

	public String getSubCalForFobValueDate() {
		return subCalForFobValueDate;
	}

	public void setSubCalForFobValueDate(String subCalForFobValueDate) {
		this.subCalForFobValueDate = subCalForFobValueDate;
	}

	public String getSubCalForFobValue() {
		return subCalForFobValue;
	}

	public void setSubCalForFobValue(String subCalForFobValue) {
		this.subCalForFobValue = subCalForFobValue;
	}

	public String getSubCalForFobValueCGST() {
		return subCalForFobValueCGST;
	}

	public void setSubCalForFobValueCGST(String subCalForFobValueCGST) {
		this.subCalForFobValueCGST = subCalForFobValueCGST;
	}

	public String getSubCalForFobValueSGST() {
		return subCalForFobValueSGST;
	}

	public void setSubCalForFobValueSGST(String subCalForFobValueSGST) {
		this.subCalForFobValueSGST = subCalForFobValueSGST;
	}

	public String getSubCalForFobValueTotalAmount() {
		return subCalForFobValueTotalAmount;
	}

	public void setSubCalForFobValueTotalAmount(String subCalForFobValueTotalAmount) {
		this.subCalForFobValueTotalAmount = subCalForFobValueTotalAmount;
	}

	public String getSubCalForFobValueEligibleCostInLacs() {
		return subCalForFobValueEligibleCostInLacs;
	}

	public void setSubCalForFobValueEligibleCostInLacs(String subCalForFobValueEligibleCostInLacs) {
		this.subCalForFobValueEligibleCostInLacs = subCalForFobValueEligibleCostInLacs;
	}
	
	
	

}
