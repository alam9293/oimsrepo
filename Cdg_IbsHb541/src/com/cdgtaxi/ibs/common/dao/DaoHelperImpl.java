package com.cdgtaxi.ibs.common.dao;

import com.cdgtaxi.ibs.acl.dao.PasswordDao;
import com.cdgtaxi.ibs.acl.dao.ResourceDao;
import com.cdgtaxi.ibs.acl.dao.RoleDao;
import com.cdgtaxi.ibs.acl.dao.UserDao;

public class DaoHelperImpl implements DaoHelper{
	GenericDao genericDao;
	public GenericDao getGenericDao() { return genericDao; }
	public void setGenericDao(GenericDao genericDao) { this.genericDao = genericDao; }

	//ACL
	UserDao userDao;
	RoleDao roleDao;
	ResourceDao resourceDao;
	PasswordDao passwordDao;
	public UserDao getUserDao() { return userDao; }
	public void setUserDao(UserDao userDao) { this.userDao = userDao; }
	public RoleDao getRoleDao() { return roleDao; }
	public void setRoleDao(RoleDao roleDao) { this.roleDao = roleDao; }
	public ResourceDao getResourceDao() { return resourceDao; }
	public void setResourceDao(ResourceDao resourceDao) { this.resourceDao = resourceDao; }
	public PasswordDao getPasswordDao() { return passwordDao; }
	public void setPasswordDao(PasswordDao passwordDao) { this.passwordDao = passwordDao; }

	//Account
	private AccountTypeDao accountTypeDao;
	private ApplicationDao applicationDao;
	public AccountTypeDao getAccountTypeDao() {	return this.accountTypeDao; }
	public void setAccountTypeDao(AccountTypeDao accountTypeDao) { this.accountTypeDao = accountTypeDao; }
	public ApplicationDao getApplicationDao() {	return this.applicationDao; }
	public void setApplicationDao(ApplicationDao applicationDao) { this.applicationDao = applicationDao; }
	private AccountDao accountDao;
	public AccountDao getAccountDao() {return this.accountDao; }
	public void setAccountDao(AccountDao accountDao) { this.accountDao = accountDao; }

	// Billing Credit Note
	private NoteDao noteDao;
	public NoteDao getNoteDao() { return noteDao; }
	public void setNoteDao(NoteDao noteDao) { this.noteDao = noteDao; }

	//Product_Type
	private ProductTypeDao productTypeDao;
	public ProductTypeDao getProductTypeDao() {return this.productTypeDao; }
	public void setProductTypeDao(ProductTypeDao productTypeDao) { this.productTypeDao = productTypeDao; }

	//Product
	private ProductDao productDao;
	public ProductDao getProductDao() {return this.productDao; }
	public void setProductDao(ProductDao productDao) { this.productDao = productDao; }

	//Invoice
	private InvoiceDao invoiceDao;
	public InvoiceDao getInvoiceDao() {return this.invoiceDao; }
	public void setInvoiceDao(InvoiceDao invoiceDao) { this.invoiceDao = invoiceDao; }

	//Payment
	private PaymentReceiptDao paymentReceiptDao;
	public PaymentReceiptDao getPaymentReceiptDao() { return paymentReceiptDao; }
	public void setPaymentReceiptDao(PaymentReceiptDao paymentReceiptDao) {	this.paymentReceiptDao = paymentReceiptDao;	}

	//Txn
	private TxnDao txnDao;
	public TxnDao getTxnDao() {return this.txnDao; }
	public void setTxnDao(TxnDao txnDao) { this.txnDao = txnDao; }

	//Bill Gen
	private BillGenRequestDao billGenRequestDao;
	public BillGenRequestDao getBillGenRequestDao() { return billGenRequestDao;	}
	public void setBillGenRequestDao(BillGenRequestDao billGenRequestDao) {	this.billGenRequestDao = billGenRequestDao;	}
	private BillGenErrorDao billGenErrorDao;
	public BillGenErrorDao getBillGenErrorDao() { return billGenErrorDao; }
	public void setBillGenErrorDao(BillGenErrorDao billGenErrorDao) { this.billGenErrorDao = billGenErrorDao; }

	//Bank Code
	private BankCodeDao bankCodeDao;
	public BankCodeDao getBankCodeDao() { return bankCodeDao; }
	public void setBankCodeDao(BankCodeDao bankCodeDao) { this.bankCodeDao = bankCodeDao; }

	// Finance
	private FinanceDao financeDao;
	public FinanceDao getFinanceDao() {	return financeDao;	}
	public void setFinanceDao(FinanceDao financeDao) { this.financeDao = financeDao; }

	//Deposit
	private DepositDao depositDao;
	public DepositDao getDepositDao() {	return depositDao; }
	public void setDepositDao(DepositDao depositDao) { this.depositDao = depositDao; }

	//Reward
	private RewardDao rewardDao;
	public RewardDao getRewardDao() {
		return rewardDao;
	}
	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
	}

	//Inventory
	private InventoryDao inventoryDao;
	public InventoryDao getInventoryDao() {
		return inventoryDao;
	}
	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	//Enquiry
	private EnquiryDao enquiryDao;
	public EnquiryDao getEnquiryDao() {
		return enquiryDao;
	}
	public void setEnquiryDao(EnquiryDao enquiryDao) {
		this.enquiryDao = enquiryDao;
	}

	//Report
	private ReportDao reportDao;
	public ReportDao getReportDao() {
		return reportDao;
	}
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	// Admin
	private AdminDao adminDao;
	public AdminDao getAdminDao() {
		return adminDao;
	}
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	//Portal
	private PortalDao portalDao;
	public PortalDao getPortalDao() {
		return portalDao;
	}
	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}
	
	//Non Billable
	private NonBillableDao nonBillableDao;
	public NonBillableDao getNonBillableDao() {
		return nonBillableDao;
	}
	public void setNonBillableDao(NonBillableDao nonBillableDao) {
		this.nonBillableDao = nonBillableDao;
	}
	
	//Prepaid
	private PrepaidDao prepaidDao;
	public PrepaidDao getPrepaidDao() {
		return prepaidDao;
	}
	public void setPrepaidDao(PrepaidDao prepaidDao) {
		this.prepaidDao = prepaidDao;
	}
}
