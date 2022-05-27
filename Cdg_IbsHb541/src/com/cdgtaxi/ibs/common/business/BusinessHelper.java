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

public interface BusinessHelper {
	public GenericBusiness getGenericBusiness();
	public void setGenericBusiness(GenericBusiness genericBusiness);

	//ACL
	public UserBusiness getUserBusiness();
	public void setUserBusiness(UserBusiness userBusiness);
	public RoleBusiness getRoleBusiness();
	public void setRoleBusiness(RoleBusiness roleBusiness);
	public ResourceBusiness getResourceBusiness();
	public void setResourceBusiness(ResourceBusiness resourceBusiness);
	public PasswordBusiness getPasswordBusiness();
	public void setPasswordBusiness(PasswordBusiness passwordBusiness);

	//Account
	public AccountTypeBusiness getAccountTypeBusiness();
	public void setAccountTypeBusiness(AccountTypeBusiness accountTypeBusiness);
	public ApplicationBusiness getApplicationBusiness();
	public void setApplicationBusiness(ApplicationBusiness applicationBusiness);

	// raymond 23 march 2009
	public AccountBusiness getAccountBusiness();
	public void setAccountBusiness(AccountBusiness accountBusiness);

	// Product && Product type
	public ProductTypeBusiness getProductTypeBusiness();
	public ProductBusiness getProductBusiness();

	// Txn Business
	public TxnBusiness getTxnBusiness();
	public void setTxnBusiness(TxnBusiness txnBusiness);


	// Billing Credit Note
	public NoteBusiness getNoteBusiness();
	public void setNoteBusiness(NoteBusiness noteBusiness);
	public PaymentBusiness getPaymentBusiness();
	public void setPaymentBusiness(PaymentBusiness paymentBusiness);
	public InvoiceBusiness getInvoiceBusiness();
	public void setInvoiceBusiness(InvoiceBusiness invoiceBusiness);

	//bill gen
	public BillGenBusiness getBillGenBusiness();
	public void setBillGenBusiness(BillGenBusiness billGenBusiness);

	//report
	public ReportBusiness getReportBusiness();
	public void setReportBusiness(ReportBusiness reportBusiness);

	// Finance
	public FinanceBusiness getFinanceBusiness();
	public void setFinanceBusiness(FinanceBusiness financeBusiness);

	// Inventory
	public InventoryBusiness getInventoryBusiness();
	public void setInventoryBusiness(InventoryBusiness inventoryBusiness);

	// Rewards
	public RewardBusiness getRewardBusiness();
	public void setRewardBusiness(RewardBusiness rewardsBusiness);

	// Enquiry
	public void setEnquiryBusiness(EnquiryBusiness enquiryBusiness);
	public EnquiryBusiness getEnquiryBusiness();

	// Admin
	public AdminBusiness getAdminBusiness();
	public void setAdminBusiness(AdminBusiness adminBusiness);
	
	// Portal User
	public PortalBusiness getPortalBusiness() ;
	public void setPortalBusiness(PortalBusiness portalBusiness) ;
	
	// Non Billable Business
	public NonBillableBusiness getNonBillableBusiness() ;
	public void setNonBillableBusiness(NonBillableBusiness nonBillableBusiness) ;
	
	// Prepaid Business
	public PrepaidBusiness getPrepaidBusiness();
	public void setPrepaidBusiness(PrepaidBusiness prepaidBusiness);
}
