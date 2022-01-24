package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detailsofpaymentbankabledpr")
public class DetailsOfPaymentBankableDPR implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
	
	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String controlId;
	
	@Column(name="details_of_payment_bankable_boucher_no")
	private String detailsOfPaymentBankableBoucherNo;
	
	@Column(name="details_of_payment_bankable_boucher_date")
	private String detailsOfPaymentBankableBoucherDate;
	
	@Column(name="details_of_payment_bankable_project_amount")
	private String detailsOfPaymentBankableProjectAmount;
	

	@Column(name="details_of_payment_bankable_project_tds")
	private String detailsOfPaymentBankableProjectTDS;
	
	@Column(name="details_of_payment_bankable_total_amount_paid")
	private String detailsOfPaymentBankableTotalAmountPaid;
	
	@Column(name="details_of_payment_bankable_project_elig_cost")
	private String detailsOfPaymentBankableProjectEligCost;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	

	public String getDetailsOfPaymentBankableBoucherNo() {
		return detailsOfPaymentBankableBoucherNo;
	}

	public void setDetailsOfPaymentBankableBoucherNo(String detailsOfPaymentBankableBoucherNo) {
		this.detailsOfPaymentBankableBoucherNo = detailsOfPaymentBankableBoucherNo;
	}

	public String getDetailsOfPaymentBankableBoucherDate() {
		return detailsOfPaymentBankableBoucherDate;
	}

	public void setDetailsOfPaymentBankableBoucherDate(String detailsOfPaymentBankableBoucherDate) {
		this.detailsOfPaymentBankableBoucherDate = detailsOfPaymentBankableBoucherDate;
	}

	public String getDetailsOfPaymentBankableProjectAmount() {
		return detailsOfPaymentBankableProjectAmount;
	}

	public void setDetailsOfPaymentBankableProjectAmount(String detailsOfPaymentBankableProjectAmount) {
		this.detailsOfPaymentBankableProjectAmount = detailsOfPaymentBankableProjectAmount;
	}

	public String getDetailsOfPaymentBankableTotalAmountPaid() {
		return detailsOfPaymentBankableTotalAmountPaid;
	}

	public void setDetailsOfPaymentBankableTotalAmountPaid(String detailsOfPaymentBankableTotalAmountPaid) {
		this.detailsOfPaymentBankableTotalAmountPaid = detailsOfPaymentBankableTotalAmountPaid;
	}

	public String getDetailsOfPaymentBankableProjectTDS() {
		return detailsOfPaymentBankableProjectTDS;
	}

	public void setDetailsOfPaymentBankableProjectTDS(String detailsOfPaymentBankableProjectTDS) {
		this.detailsOfPaymentBankableProjectTDS = detailsOfPaymentBankableProjectTDS;
	}

	public String getDetailsOfPaymentBankableProjectEligCost() {
		return detailsOfPaymentBankableProjectEligCost;
	}

	public void setDetailsOfPaymentBankableProjectEligCost(String detailsOfPaymentBankableProjectEligCost) {
		this.detailsOfPaymentBankableProjectEligCost = detailsOfPaymentBankableProjectEligCost;
	}
	
	
}
