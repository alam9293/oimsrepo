package com.cdgtaxi.ibs.common.dao;

import com.cdgtaxi.ibs.acl.dao.PasswordDao;
import com.cdgtaxi.ibs.acl.dao.ResourceDao;
import com.cdgtaxi.ibs.acl.dao.RoleDao;
import com.cdgtaxi.ibs.acl.dao.UserDao;

public interface DaoHelper {
	public GenericDao getGenericDao();
	public void setGenericDao(GenericDao genericDao);

	//ACL
	public UserDao getUserDao();
	public void setUserDao(UserDao userDao);
	public RoleDao getRoleDao();
	public void setRoleDao(RoleDao roleDao);
	public ResourceDao getResourceDao();
	public void setResourceDao(ResourceDao resourceDao);
	public PasswordDao getPasswordDao();
	public void setPasswordDao(PasswordDao passwordDao);

	//Account
	public AccountTypeDao getAccountTypeDao();
	public void setAccountTypeDao(AccountTypeDao accountTypeDao);
	public ApplicationDao getApplicationDao();
	public void setApplicationDao(ApplicationDao applicationDao);

	public AccountDao getAccountDao();
	public void setAccountDao(AccountDao accountDao);

	// Billing Credit Note
	public NoteDao getNoteDao();

	//Product_type
	public ProductTypeDao getProductTypeDao();
	public void setProductTypeDao(ProductTypeDao productTypeDao);

	//Product
	public ProductDao getProductDao();
	public void setProductDao(ProductDao productTypeDao);

	//Invoice
	public InvoiceDao getInvoiceDao();
	public void setInvoiceDao(InvoiceDao invoiceDao);

	//payment
	public PaymentReceiptDao getPaymentReceiptDao();
	public void setPaymentReceiptDao(PaymentReceiptDao paymentReceiptDao);

	//Txn
	public TxnDao getTxnDao();
	public void setTxnDao(TxnDao txnDao);

	//Bill Gen
	public BillGenRequestDao getBillGenRequestDao();
	public void setBillGenRequestDao(BillGenRequestDao billGenRequestDao);
	public BillGenErrorDao getBillGenErrorDao();
	public void setBillGenErrorDao(BillGenErrorDao billGenErrorDao);

	//Bank Code
	public BankCodeDao getBankCodeDao();
	public void setBankCodeDao(BankCodeDao bankCodeDao);

	//Finance Code
	public FinanceDao getFinanceDao();
	public void setFinanceDao(FinanceDao transactionCodeDao);

	//Deposit
	public DepositDao getDepositDao();
	public void setDepositDao(DepositDao depositDao);

	//Rewards
	public RewardDao getRewardDao();
	public void setRewardDao(RewardDao rewardDao);

	//Inventory
	public InventoryDao getInventoryDao();
	public void setInventoryDao(InventoryDao inventoryDao);

	// Enquiry
	public void setEnquiryDao(EnquiryDao enquiryDao);
	public EnquiryDao getEnquiryDao();

	//Report
	public ReportDao getReportDao();
	public void setReportDao(ReportDao reportDao);

	// Admin
	public AdminDao getAdminDao();
	public void setAdminDao(AdminDao adminDao);
	
	//Portal
	public PortalDao getPortalDao() ;
	public void setPortalDao(PortalDao portalDao) ;
	
	//Non Billable
	public NonBillableDao getNonBillableDao() ;
	public void setNonBillableDao(NonBillableDao nonBillableDao) ;
	
	//Prepaid
	public PrepaidDao getPrepaidDao();
	public void setPrepaidDao(PrepaidDao prepaidDao);
	
}



