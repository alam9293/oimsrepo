package com.cdgtaxi.ibs.acct.ui;


public abstract class CommonEInvoiceEmailWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	
	protected void eInvoiceEmail (String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceSubject, String eInvoiceEmailZipFlag, 
			String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String parentCode, String code) throws Exception {
		
		displayProcessing();
		
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().updateEInvoiceEmail(eInvoiceEmailFlag, eInvoiceEmail, eInvoiceSubject, eInvoiceEmailZipFlag, eInvoiceEmailAttachment, eInvoiceEmailPage, custNo, parentCode, code, userId);
	}
	
	protected void eInvoiceEmail (String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceSubject, String eInvoiceEmailZipFlag, 
			String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String subAcctNo) throws Exception {
		
		displayProcessing();
		
		String userId = this.getUserLoginIdAndDomain();
		this.businessHelper.getAccountBusiness().updateEInvoiceEmail(eInvoiceEmailFlag, eInvoiceEmail, eInvoiceSubject, eInvoiceEmailZipFlag, eInvoiceEmailAttachment, eInvoiceEmailPage, custNo, subAcctNo, userId);
	}

}