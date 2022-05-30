package com.cdgtaxi.ibs.common.business;

import com.cdgtaxi.ibs.acct.business.AccountBusiness;
import com.cdgtaxi.ibs.acct.business.AccountTypeBusiness;
import com.cdgtaxi.ibs.acct.business.ApplicationBusiness;
import com.cdgtaxi.ibs.acl.business.PasswordBusiness;
import com.cdgtaxi.ibs.acl.business.ResourceBusiness;
import com.cdgtaxi.ibs.acl.business.RoleBusiness;
import com.cdgtaxi.ibs.acl.business.UserBusiness;
import com.cdgtaxi.ibs.admin.business.AdminBusiness;
import com.cdgtaxi.ibs.billgen.business.BillGenBusiness;
import com.cdgtaxi.ibs.billing.business.InvoiceBusiness;
import com.cdgtaxi.ibs.billing.business.NoteBusiness;
import com.cdgtaxi.ibs.billing.business.PaymentBusiness;
import com.cdgtaxi.ibs.enquiry.as.business.EnquiryBusiness;
import com.cdgtaxi.ibs.finance.business.FinanceBusiness;
import com.cdgtaxi.ibs.inventory.business.InventoryBusiness;
import com.cdgtaxi.ibs.nonbillable.business.NonBillableBusiness;
import com.cdgtaxi.ibs.portal.business.PortalBusiness;
import com.cdgtaxi.ibs.prepaid.business.PrepaidBusiness;
import com.cdgtaxi.ibs.product.business.ProductBusiness;
import com.cdgtaxi.ibs.product.business.ProductTypeBusiness;
import com.cdgtaxi.ibs.report.business.ReportBusiness;
import com.cdgtaxi.ibs.reward.business.RewardBusiness;
import com.cdgtaxi.ibs.txn.business.TxnBusiness;

public class BusinessHelperImpl implements BusinessHelper{
	GenericBusiness genericBusiness;
	public GenericBusiness getGenericBusiness() { return genericBusiness; }
	public void setGenericBusiness(GenericBusiness genericBusiness) { this.genericBusiness = genericBusiness; }

	//ACL
	UserBusiness userBusiness;
	RoleBusiness roleBusiness;
	ResourceBusiness resourceBusiness;
	PasswordBusiness passwordBusiness;
	PrepaidBusiness prepaidBusiness;
	public UserBusiness getUserBusiness() { return userBusiness; }
	public void setUserBusiness(UserBusiness userBusiness) { this.userBusiness = userBusiness; }
	public RoleBusiness getRoleBusiness() { return roleBusiness; }
	public void setRoleBusiness(RoleBusiness roleBusiness) { this.roleBusiness = roleBusiness; }
	public ResourceBusiness getResourceBusiness() { return resourceBusiness; }
	public void setResourceBusiness(ResourceBusiness resourceBusiness) { this.resourceBusiness = resourceBusiness; }
	public PasswordBusiness getPasswordBusiness() { return passwordBusiness; }
	public void setPasswordBusiness(PasswordBusiness passwordBusiness) { this.passwordBusiness = passwordBusiness; }

	//Account
	AccountTypeBusiness accountTypeBusiness;
	ApplicationBusiness applicationBusiness;
	public AccountTypeBusiness getAccountTypeBusiness() { return accountTypeBusiness; }
	public void setAccountTypeBusiness(AccountTypeBusiness accountTypeBusiness) { this.accountTypeBusiness = accountTypeBusiness; }
	public ApplicationBusiness getApplicationBusiness() { return applicationBusiness; }
	public void setApplicationBusiness(ApplicationBusiness applicationBusiness) { this.applicationBusiness = applicationBusiness; }

	//raymond
	AccountBusiness accountBusiness;
	public AccountBusiness getAccountBusiness() { return accountBusiness;}
	public void setAccountBusiness(AccountBusiness accountBusiness) { this.accountBusiness = accountBusiness; }

	//Billing
	NoteBusiness noteBusiness;
	PaymentBusiness paymentBusiness;
	InvoiceBusiness invoiceBusiness;
	public NoteBusiness getNoteBusiness() { return noteBusiness;}
	public void setNoteBusiness(NoteBusiness noteBusiness) { this.noteBusiness = noteBusiness; }
	public PaymentBusiness getPaymentBusiness() { return paymentBusiness; }
	public void setPaymentBusiness(PaymentBusiness paymentBusiness) { this.paymentBusiness = paymentBusiness; }
	public InvoiceBusiness getInvoiceBusiness() { return invoiceBusiness; }
	public void setInvoiceBusiness(InvoiceBusiness invoiceBusiness) { this.invoiceBusiness = invoiceBusiness; }

	//Products and Product types
	//Setters and getters for Business
	ProductTypeBusiness productTypeBusiness;
	public ProductTypeBusiness getProductTypeBusiness() { return productTypeBusiness; }
	public void setProductTypeBusiness (ProductTypeBusiness  productTypeBusiness) { this.productTypeBusiness = productTypeBusiness; }

	ProductBusiness productBusiness;
	public ProductBusiness getProductBusiness() { return productBusiness; }
	public void setProductBusiness (ProductBusiness  productBusiness) { this.productBusiness = productBusiness; }

	// Txn Business
	TxnBusiness txnBusiness;
	public TxnBusiness getTxnBusiness() { return txnBusiness; }
	public void setTxnBusiness (TxnBusiness  txnBusiness) { this.txnBusiness = txnBusiness; }

	// Bill Gen Business
	BillGenBusiness billGenBusiness;
	public BillGenBusiness getBillGenBusiness() { return billGenBusiness; }
	public void setBillGenBusiness(BillGenBusiness billGenBusiness) { this.billGenBusiness = billGenBusiness; }

	// Report Business
	ReportBusiness reportBusiness;
	public ReportBusiness getReportBusiness() {	return reportBusiness; }
	public void setReportBusiness(ReportBusiness reportBusiness) { this.reportBusiness = reportBusiness; }

	// Finance Business
	FinanceBusiness financeBusiness;
	public FinanceBusiness getFinanceBusiness() {
		return financeBusiness;
	}
	public void setFinanceBusiness(FinanceBusiness financeBusiness) {
		this.financeBusiness = financeBusiness;
	}

	// Inventory Business
	InventoryBusiness inventoryBusiness;
	public InventoryBusiness getInventoryBusiness() {
		return inventoryBusiness;
	}
	public void setInventoryBusiness(InventoryBusiness inventoryBusiness) {
		this.inventoryBusiness = inventoryBusiness;
	}

	// Rewards Business
	RewardBusiness rewardBusiness;
	public RewardBusiness getRewardBusiness() {
		return rewardBusiness;
	}
	public void setRewardBusiness(RewardBusiness rewardBusiness) {
		this.rewardBusiness = rewardBusiness;
	}

	// Enquiry Business
	EnquiryBusiness enquiryBusiness;
	public EnquiryBusiness getEnquiryBusiness() {
		return enquiryBusiness;
	}
	public void setEnquiryBusiness(EnquiryBusiness enquiryBusiness) {
		this.enquiryBusiness = enquiryBusiness;
	}

	// Admin Business
	AdminBusiness adminBusiness;
	public AdminBusiness getAdminBusiness() {
		return adminBusiness;
	}
	public void setAdminBusiness(AdminBusiness adminBusiness) {
		this.adminBusiness = adminBusiness;
	}
	
	// Portal Business
	PortalBusiness portalBusiness;
	public PortalBusiness getPortalBusiness() {
		return portalBusiness;
	}
	public void setPortalBusiness(PortalBusiness portalBusiness) {
		this.portalBusiness = portalBusiness;
	}
	
	// Non Billable Business
	NonBillableBusiness nonBillableBusiness;
	public NonBillableBusiness getNonBillableBusiness() {
		return nonBillableBusiness;
	}
	public void setNonBillableBusiness(NonBillableBusiness nonBillableBusiness) {
		this.nonBillableBusiness = nonBillableBusiness;
	}
	public PrepaidBusiness getPrepaidBusiness() {
		return prepaidBusiness;
	}
	public void setPrepaidBusiness(PrepaidBusiness prepaidBusiness) {
		this.prepaidBusiness = prepaidBusiness;
		
	}
}
