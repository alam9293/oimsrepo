package com.cdgtaxi.ibs.enquiry.as.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.NoIBSProdOrAcctFoundException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwAccount;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwProductExpiryDate;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class EnqCardWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(EnqCardWindow.class);
	private static final long serialVersionUID = 1L;
	private AmtbAccount currAcct = null;
	private AmtbAccount divAcct = null;
	private AmtbAccount corpAcct = null;
	private AmtbAccount deptAcct = null;
	private PmtbProduct currProd = null;

	public EnqCardWindow() throws InterruptedException{
	}

	public void refresh() throws InterruptedException {
		logger.debug("Enq Card Window refresh()");
		//init();
	}
	
	public void viewCorpAccountAging() throws InterruptedException{
		
		try{
			if(currAcct!=null){
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("accountNo", currAcct.getAccountNo().toString());
				final Window win = (Window) Executions.createComponents(Uri.VIEW_ACCOUNT_AGING, null, map);
				win.setClosable(true);
				win.doModal();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void viewDivAccountAging() throws InterruptedException{
		
		try{
			if(currAcct!=null){
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("accountNo", divAcct.getAccountNo().toString());
				final Window win = (Window) Executions.createComponents(Uri.VIEW_ACCOUNT_AGING, null, map);
				win.setClosable(true);
				win.doModal();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void viewDeptAccountAging() throws InterruptedException{
		
		try{
			if(currAcct!=null){
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("accountNo", deptAcct.getAccountNo().toString());
				final Window win = (Window) Executions.createComponents(Uri.VIEW_ACCOUNT_AGING, null, map);
				win.setClosable(true);
				win.doModal();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void search() throws InterruptedException {
		logger.debug("Enq Card Window search()");
		// resetting everything except card number
		// Edited by Yiming, 19th May 2011 10:57am
		// AF/0511/007
		resetExcludeCardNo();
		try
		{
			// Retrieve the card No
			String cardNo = ((CapsTextbox) this.getFellow("cardNo")).getValue();
			// Clear the previous value
			currAcct = null;
			divAcct = null;
			corpAcct = null;
			deptAcct = null;
			
			
			AsvwProduct asvwProduct = this.businessHelper.getEnquiryBusiness().getAsvwProduct(cardNo);
	
			if (asvwProduct != null)
			{
				// Open up the grid
				((Grid)this.getFellow("acctInfo")).setVisible(true);
				((Grid)this.getFellow("cardInfo")).setVisible(true);
				
				// Get the current Account ID and Account type to identify the level
				String acctType = asvwProduct.getAsvwAccount().getAcctType();
				String acctId = asvwProduct.getAsvwAccount().getAcctId();
				// Note: Dept = Account ID + 4 char Div + 4 char Dept
				if (NonConfigurableConstants.AS_ACCOUNT_CATEGORY_CORPORATE.equals(acctType) || 
						NonConfigurableConstants.AS_ACCOUNT_CATEGORY_APPLICANT.equals(acctType))
				{
					// Store the amtbAccount
					// Internal Account No is stored as custNo + divcode + dept code in AS database
					String custNo = acctId.trim();
					currAcct = (AmtbAccount) this.businessHelper.getEnquiryBusiness().getAccount(custNo, 0, null, null);
					if (currAcct != null)
					{
						this.setCorporateDetails(currAcct, asvwProduct.getAsvwAccount());
						corpAcct = currAcct;
					}
					else
						throw new NoIBSProdOrAcctFoundException();
				}
				else if (NonConfigurableConstants.AS_ACCOUNT_CATEGORY_DIVISION.equals(acctType))
						
				{
					String divCode = acctId.substring(acctId.length()-4, acctId.length());
					String custNo = acctId.substring(0, acctId.length()-4);
					currAcct = (AmtbAccount) this.businessHelper.getEnquiryBusiness().getAccount(custNo, 1, null, divCode);
					if (currAcct != null)
					{
						this.setCorporateDetails(currAcct.getAmtbAccount(), asvwProduct.getAsvwAccount().getAsvwAccount());
						this.setDivisionVisible(currAcct, asvwProduct.getAsvwAccount());
						corpAcct = currAcct.getAmtbAccount();
						divAcct = currAcct;
					}
					else
						throw new NoIBSProdOrAcctFoundException();
				}
				else if (NonConfigurableConstants.AS_ACCOUNT_CATEGORY_DEPARTMENT.equals(acctType))
				{
					String deptCode = acctId.substring(acctId.length()-4, acctId.length());
					String divCode = acctId.substring(acctId.length()-8, acctId.length()-4);
					String custNo = acctId.substring(0, acctId.length()-8);
					currAcct = (AmtbAccount) this.businessHelper.getEnquiryBusiness().getAccount(custNo, 2, divCode, deptCode);
					if (currAcct != null)
					{
						this.setCorporateDetails(currAcct.getAmtbAccount().getAmtbAccount(), asvwProduct.getAsvwAccount().getAsvwAccount().getAsvwAccount());
						this.setDivisionVisible(currAcct.getAmtbAccount(), asvwProduct.getAsvwAccount().getAsvwAccount());
						this.setDepartmentVisible(currAcct, asvwProduct.getAsvwAccount());
						corpAcct = currAcct.getAmtbAccount().getAmtbAccount();
						divAcct = currAcct.getAmtbAccount();
						deptAcct = currAcct;
					}
					else
						throw new NoIBSProdOrAcctFoundException();
				}
				else if (NonConfigurableConstants.AS_ACCOUNT_CATEGORY_SUB_APPLICANT.equals(acctType))
				{
					String divCode = acctId.substring(acctId.length()-4, acctId.length());
					String custNo = acctId.substring(0, acctId.length()-4);
					currAcct = (AmtbAccount) this.businessHelper.getEnquiryBusiness().getAccount(custNo, 1, null, divCode);
					if (currAcct != null)
					{
						this.setCorporateDetails(currAcct.getAmtbAccount(), asvwProduct.getAsvwAccount().getAsvwAccount());
						this.setSubApplicantVisible(currAcct, asvwProduct.getAsvwAccount());
						corpAcct = currAcct.getAmtbAccount();
						divAcct = currAcct;
					}
					else
						throw new NoIBSProdOrAcctFoundException();
				}
				
				if (currAcct != null)
				{
					this.setContact(currAcct);
					currProd = (PmtbProduct) this.businessHelper.getEnquiryBusiness().getProduct(cardNo, DateUtil.getCurrentTimestamp());
					if (currProd != null)
						this.setProductDetails(currProd, asvwProduct);
					else
						throw new NoIBSProdOrAcctFoundException();
				}
				else
					throw new NoIBSProdOrAcctFoundException();
				
			}
			else
			{
				// No such card found
				// Clear all results
				Messagebox.show("No matching record found", 
						"AS Enquiry", Messagebox.OK, Messagebox.INFORMATION);
				this.resetExcludeCardNo();
			}
		}
		catch (NoIBSProdOrAcctFoundException pafe)
		{
			Messagebox.show("No equivalent IBS Account or Product found. Please contact Administrator", "AS Enquiry", Messagebox.OK, Messagebox.EXCLAMATION);
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch (WrongValueException wve)
		{
			Messagebox.show("Please enter the mandatory fields", "AS Enquiry", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
	}
	
	private void setCorporateDetails(AmtbAccount amtbAccount, AsvwAccount asvwAccount)
	{
		((Label)this.getFellow("acctNameNo")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCustNo() + ")");
		if (amtbAccount.getCreditLimit()!= null)
			((Label)this.getFellow("acctCreditLimit")).setValue(StringUtil.bigDecimalToString(amtbAccount.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("acctCreditLimit")).setValue("-");
		if (asvwAccount.getCreditLimit()!= null)
			((Label)this.getFellow("acctCreditBalance")).setValue(StringUtil.bigDecimalToString(asvwAccount.getCreditLimit().subtract(asvwAccount.getTotalTxnAmt()), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("acctCreditBalance")).setValue("-");
	}
	
	private void setDivisionVisible(AmtbAccount amtbAccount, AsvwAccount asvwAccount){
		((Row)this.getFellow("divNameNoRow")).setVisible(true);
		((Row)this.getFellow("divCreditLimitRow")).setVisible(true);
		((Label)this.getFellow("divNameNo")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
		if (amtbAccount.getCreditLimit()!= null)
			((Label)this.getFellow("divCreditLimit")).setValue(StringUtil.bigDecimalToString(amtbAccount.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("divCreditLimit")).setValue("-");
		if (asvwAccount.getCreditLimit()!= null)
			((Label)this.getFellow("divCreditBalance")).setValue(StringUtil.bigDecimalToString(asvwAccount.getCreditLimit().subtract(asvwAccount.getTotalTxnAmt()), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("divCreditBalance")).setValue("-");
	}
	
	private void setDivisionInvisible()
	{
		((Row)this.getFellow("divNameNoRow")).setVisible(false);
		((Row)this.getFellow("divCreditLimitRow")).setVisible(false);
		((Label)this.getFellow("divNameNo")).setValue("-");
		((Label)this.getFellow("divCreditLimit")).setValue("-");
		((Label)this.getFellow("divCreditBalance")).setValue("-");
	}
	
	private void setDepartmentVisible(AmtbAccount amtbAccount, AsvwAccount asvwAccount)
	{
		((Row)this.getFellow("deptNameNoRow")).setVisible(true);
		((Row)this.getFellow("deptCreditLimitRow")).setVisible(true);
		((Label)this.getFellow("deptNameNo")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
		if (amtbAccount.getCreditLimit() != null)
			((Label)this.getFellow("deptCreditLimit")).setValue(StringUtil.bigDecimalToString(amtbAccount.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("deptCreditLimit")).setValue("-");
		if (asvwAccount.getCreditLimit()!= null)
			((Label)this.getFellow("deptCreditBalance")).setValue(StringUtil.bigDecimalToString(asvwAccount.getCreditLimit().subtract(asvwAccount.getTotalTxnAmt()), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("deptCreditBalance")).setValue("-");
	}
	
	private void setDepartmentInvisible()
	{
		((Row)this.getFellow("deptNameNoRow")).setVisible(false);
		((Row)this.getFellow("deptCreditLimitRow")).setVisible(false);
		((Label)this.getFellow("deptNameNo")).setValue("-");
		((Label)this.getFellow("deptCreditLimit")).setValue("-");
		((Label)this.getFellow("deptCreditBalance")).setValue("-");
	}
	
	private void setSubApplicantVisible(AmtbAccount amtbAccount, AsvwAccount asvwAccount)
	{
		((Row)this.getFellow("subApplicantNameNoRow")).setVisible(true);
		((Row)this.getFellow("subApplicantCreditLimitRow")).setVisible(true);
		((Label)this.getFellow("subApplicantNameNo")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
		if (amtbAccount.getCreditLimit() != null)
			((Label)this.getFellow("subApplicantCreditLimit")).setValue(StringUtil.bigDecimalToString(amtbAccount.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("subApplicantCreditLimit")).setValue("-");
		if (asvwAccount.getCreditLimit()!= null)
			((Label)this.getFellow("subApplicantCreditBalance")).setValue(StringUtil.bigDecimalToString(asvwAccount.getCreditLimit().subtract(asvwAccount.getTotalTxnAmt()), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("subApplicantCreditBalance")).setValue("-");
	}
	
	private void setSubApplicantInvisible()
	{
		((Row)this.getFellow("subApplicantNameNoRow")).setVisible(false);
		((Row)this.getFellow("subApplicantCreditLimitRow")).setVisible(false);
		((Label)this.getFellow("subApplicantNameNo")).setValue("-");
		((Label)this.getFellow("subApplicantCreditLimit")).setValue("-");
		((Label)this.getFellow("subApplicantCreditBalance")).setValue("-");
	}
	
	private void setProductDetails(PmtbProduct pmtbProduct, AsvwProduct asvwProduct)
	{
		// Derive the status
		if (asvwProduct.getUsed() == null || "".equalsIgnoreCase(asvwProduct.getUsed()))
		{
			// Used and Active - show as used
			if (NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE.equalsIgnoreCase(asvwProduct.getStatus()))
			{
				((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.AS_PRODUCT_ACTIVE);
				((Label)this.getFellow("reason")).setValue("-");
			}
			else
			{
				((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.AS_PRODUCT_INACTIVE);
				((Label)this.getFellow("reason")).setValue(asvwProduct.getReasonCode());
			}
		}
		else if (NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE.equals(asvwProduct.getStatus()) && NonConfigurableConstants.BOOLEAN_YES.equals(asvwProduct.getUsed()))
		{
			// Used and Active - show as used
			((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.PRODUCT_STATUS.get(NonConfigurableConstants.PRODUCT_STATUS_USED));
			((Label)this.getFellow("reason")).setValue("-");
		}
		else if ((NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE.equals(asvwProduct.getStatus()) && NonConfigurableConstants.BOOLEAN_YES.equals(asvwProduct.getUsed())))
		{
			// Used and Inactive - show as used
			((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.PRODUCT_STATUS.get(NonConfigurableConstants.PRODUCT_STATUS_USED));
			((Label)this.getFellow("reason")).setValue("-");
		}
		else if ((NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE.equals(asvwProduct.getStatus()) && NonConfigurableConstants.BOOLEAN_NO.equals(asvwProduct.getUsed())))
		{
			// Active but not used - show as active
			((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.PRODUCT_STATUS.get(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE));
			((Label)this.getFellow("reason")).setValue("-");
		}
		else
		{
			((Label)this.getFellow("cardStatus")).setValue(NonConfigurableConstants.AS_PRODUCT_INACTIVE);
			((Label)this.getFellow("reason")).setValue(asvwProduct.getReasonCode());
			// Inactive and not used - show as Inactive
		}
		if (pmtbProduct.getCardNo() != null && !"".equalsIgnoreCase(pmtbProduct.getCardNo()))
			((Label)this.getFellow("newCardNo")).setValue(pmtbProduct.getCardNo());
		else
			((Label)this.getFellow("newCardNo")).setValue("-");

		if (asvwProduct.getAsvwProductType().getProdName() != null && !"".equalsIgnoreCase(asvwProduct.getAsvwProductType().getProdName()))
			((Label)this.getFellow("productType")).setValue(asvwProduct.getAsvwProductType().getProdName());
		else
			((Label)this.getFellow("productType")).setValue("-");
			
		if (pmtbProduct.getNameOnProduct() != null && !"".equalsIgnoreCase(pmtbProduct.getNameOnProduct()))
			((Label)this.getFellow("nameOnCard")).setValue(pmtbProduct.getNameOnProduct());
		else
			((Label)this.getFellow("nameOnCard")).setValue("-");

		String expiryDt = "";
		if (asvwProduct.getAsvwProductExpiryDates() != null)
		{
			Iterator<AsvwProductExpiryDate> asvwProductExpiryDateList = asvwProduct.getAsvwProductExpiryDates().iterator();

			boolean first = true;				
			while (asvwProductExpiryDateList.hasNext())
			{	
				AsvwProductExpiryDate tempExpiryDt = asvwProductExpiryDateList.next();
				if (first)
				{
					// Swap Expiry Dt order of display from YYMM (in DB) to MMYY on screen
					expiryDt = tempExpiryDt.getExpiryDate();
					String swapExpiryDt = expiryDt.substring(2, 4) + expiryDt.substring(0, 2);
					expiryDt = swapExpiryDt;
					first = false;
				}
				else
				{
					String swapExpiryDt = tempExpiryDt.getExpiryDate().substring(2, 4) + tempExpiryDt.getExpiryDate().substring(0, 2);
					expiryDt = expiryDt + ", " + swapExpiryDt;
				}
			}
		}

		if (expiryDt != null && !"".equals(expiryDt))
			((Label)this.getFellow("expiryDt")).setValue(expiryDt);
		else
			((Label)this.getFellow("expiryDt")).setValue("-");			
		
		if (pmtbProduct.getCreditLimit() != null)
			((Label)this.getFellow("cardCreditLimit")).setValue(StringUtil.bigDecimalToString(pmtbProduct.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("cardCreditLimit")).setValue("-");
		if (asvwProduct.getCreditLimit() != null)
			((Label)this.getFellow("cardCreditBalance")).setValue(StringUtil.bigDecimalToString(asvwProduct.getCreditLimit().subtract(asvwProduct.getTotalTxnAmt()), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("cardCreditBalance")).setValue("-");
		if (asvwProduct.getFixedValue() != null)
			((Label)this.getFellow("fixedValue")).setValue(StringUtil.bigDecimalToString(asvwProduct.getFixedValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("fixedValue")).setValue("-");

		
		if (asvwProduct.getOfflineCountLimit() != null)
			((Label)this.getFellow("offlineCount")).setValue(String.valueOf(asvwProduct.getOfflineCountLimit()));
		else
			((Label)this.getFellow("offlineCount")).setValue("-");
		if (asvwProduct.getOfflineAmtLimit() != null)
			((Label)this.getFellow("offlineAmountAccumulative")).setValue(StringUtil.bigDecimalToString(asvwProduct.getOfflineAmtLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("offlineAmountAccumulative")).setValue("-");
		if (asvwProduct.getOfflineTxnLimit()!= null)
			((Label)this.getFellow("offlineAmountPerTxn")).setValue(StringUtil.bigDecimalToString(asvwProduct.getOfflineTxnLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		else
			((Label)this.getFellow("offlineAmountPerTxn")).setValue("-");
		if (asvwProduct.getForceOnline() != null)
			((Label)this.getFellow("forceOnline")).setValue(asvwProduct.getForceOnline());
		else
			((Label)this.getFellow("forceOnline")).setValue("-");
	}
	
	
	public void init() throws InterruptedException
	{
		
	}
	
	/*
	 * This reset is inherited from extended class.
	 * This method will reset everything.
	 */
	public void reset() throws InterruptedException
	{
		((Label)this.getFellow("acctNameNo")).setValue("-");
		((Label)this.getFellow("acctCreditLimit")).setValue("-");
		((Label)this.getFellow("acctCreditBalance")).setValue("-");
		((Label)this.getFellow("newCardNo")).setValue("-");
		((Label)this.getFellow("productType")).setValue("-");
		((Label)this.getFellow("nameOnCard")).setValue("-");
		((Label)this.getFellow("cardStatus")).setValue("-");
		((Label)this.getFellow("reason")).setValue("-");
		((CapsTextbox)this.getFellow("cardNo")).setConstraint("");
		((CapsTextbox)this.getFellow("cardNo")).setValue(null);
		((CapsTextbox)this.getFellow("cardNo")).setConstraint( new com.cdgtaxi.ibs.web.constraint.RequiredNumericConstraint());
		this.setDivisionInvisible();
		this.setDepartmentInvisible();
		this.setSubApplicantInvisible();
		// Close grid
		((Grid)this.getFellow("acctInfo")).setVisible(false);
		((Grid)this.getFellow("cardInfo")).setVisible(false);
	}
	
	/*
	 * This reset will exclude card no because when user doing searching,
	 * with no result a reset of the result tables and other labels but not card no.
	 * Reason because user want to edit from there and do a reset.
	 * When user really clicks the RESET button, then full reset is required.
	 */
	public void resetExcludeCardNo() throws InterruptedException{
		((Label)this.getFellow("acctNameNo")).setValue("-");
		((Label)this.getFellow("acctCreditLimit")).setValue("-");
		((Label)this.getFellow("acctCreditBalance")).setValue("-");
		((Label)this.getFellow("newCardNo")).setValue("-");
		((Label)this.getFellow("productType")).setValue("-");
		((Label)this.getFellow("nameOnCard")).setValue("-");
		((Label)this.getFellow("cardStatus")).setValue("-");
		((Label)this.getFellow("reason")).setValue("-");
		this.setDivisionInvisible();
		this.setDepartmentInvisible();
		this.setSubApplicantInvisible();
		// Close grid
		((Grid)this.getFellow("acctInfo")).setVisible(false);
		((Grid)this.getFellow("cardInfo")).setVisible(false);
	}
	
	private void setContact(AmtbAccount amtbAccount)
	{
		// There should be more than 1 main contact for all accounts.
		// Edited by Yiming, 10th Feb 2011 5:15pm
		// AF/0211/010
		for(AmtbAcctMainContact mainContact : amtbAccount.getAmtbAcctMainContacts()){
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				String mainContactName = mainContact.getAmtbContactPerson().getMainContactName();
				String secContactName = mainContact.getAmtbContactPerson().getSubContactName();
				Label billContact = (Label) this.getFellow("contact");
				if (mainContactName != null && !"".equals(mainContactName))
				{
					if (secContactName != null && !"".equals(secContactName))
					{
						billContact.setValue(mainContactName + "/" + secContactName);
					}
					else
					{
						billContact.setValue(mainContactName);							
					}
				}
				break;
			}
		}
//		// Get the first main contact since there should only be 1.
//		Iterator<AmtbAcctMainContact> mainContacts = null;
//		mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
//		// Set main contact information
//		AmtbAcctMainContact amtbAccountMainContact = mainContacts.next();
//		String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
//		String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
//		Label billContact = (Label) this.getFellow("contact");
//		if (mainContactName != null && !"".equals(mainContactName))
//		{
//			if (secContactName != null && !"".equals(secContactName))
//			{
//				billContact.setValue(mainContactName + "/" + secContactName);
//			}
//			else
//			{
//				billContact.setValue(mainContactName);							
//			}
//		}
	}
}
