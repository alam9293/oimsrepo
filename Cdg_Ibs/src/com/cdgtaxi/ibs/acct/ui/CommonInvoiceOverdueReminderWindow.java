package com.cdgtaxi.ibs.acct.ui;


public abstract class CommonInvoiceOverdueReminderWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;

	protected void invoiceOverdueReminder(String wholeEmail, String overdueInvoiceReminder, String custNo, String parentCode, String code) throws Exception{
		displayProcessing();

		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().updateInvoiceOverdue(custNo, parentCode, code, wholeEmail, overdueInvoiceReminder, userId);
	}

	protected void invoiceOverdueReminder(String wholeEmail, String overdueInvoiceReminder, String custNo, String subAcctNo) throws Exception{
		displayProcessing();

		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().updateInvoiceOverdue(custNo, subAcctNo, wholeEmail, overdueInvoiceReminder, userId);
	}
}
