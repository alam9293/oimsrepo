package com.cdgtaxi.ibs.inventory.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.dao.ConcurrencyFailureException;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.CreditTermMasterManager;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoUnavailableException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceFile;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbIssueReqFlow;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

@SuppressWarnings({"serial", "unchecked"})
public class ViewRequestIssuanceWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(ViewRequestIssuanceWindow.class);

	private Div contactDetailsDiv, invoiceDetailsDiv;
	
	//Account Information Labels
	private Label accountNoLbl, accountTypeLbl, accountNameLbl, divisionNameLbl, departmentNameLbl;
	
	//View Request -> Request Detail Labels
	private Label itemTypeNameLabel, requestDateLabel, requestStatusLabel, quantityLabel,
		requestNoLbl, serialNoStartLbl, serialNoEndLbl, expiryDateLbl, handlingFeeLbl, 
		discountLbl, deliveryChargesLbl, requestorLabel, requestorRemarksLbl;
	private Textbox remarksTB;
	private Button rejectButton, approveButton;
	
	//View Request -> Contact Information Labels
	private Label contactPerson1Lbl, jobTitleLbl, officeContactNoLbl, mobileContactNoLbl,
		faxLbl, emailLbl, contactPerson2Lbl, officeContactNo2Lbl, mobileContactNo2Lbl,
		email2Lbl;
	
	//Issue Request (Edit)
	private Label issuanceEditExpiryDateLbl, issuanceEditSerialNoStartLbl,
		issuanceEditSerialNoEndLbl, remarksLabel;
	private Button issueButton;
	
	//Issue Request (View after issued)
	private Label serialStartLabel, serialEndLabel, expiryDateLabel, handlingFeeLabel, 
		discountLabel, deliveryChargesLabel;
	
	private final ImtbIssueReq request;
	
	/* *********************************************
	 * Misc invoice
	 * *********************************************/
	private Constraint cityTBConstraint, stateTBConstraint;
	private final Constraint nullConstraint = null;
	
	private static final String DISCOUNT_GREATER_THAN_TOTAL_PRICE = "* Discount must be less than or equal to the Total Price";
	private static final String COD_DESC = "CASH ON DELIVERY";
	private final int DESCRIPTION_COL = 0, UNIT_PRICE_COL = 1, QUANTITY_COL = 2,
	TOTAL_PRICE_COL = 3, TXN_TYPE_COL = 4, DISCOUNT_COL = 5, 
	GST_COL = 6,  NET_AMOUNT_COL = 7;
	
	private Datebox invoiceDateDB;
	private Combobox creditTermCB, billingContactCB, countryCB;
	private Label totalAmountLabel, contactPersonLabel, jobTitleLabel, officeNoLabel, mobileNoLabel,
		faxNoLabel, emailLabel, contactPerson2Label, officeNo2Label, mobileNo2Label, email2Label;
	private Textbox invoiceRemarksTB, customerNameTB, billedToTB, blockNoTB, unitNoTB, streetNameTB,
		buildingNameTB, areaTB, cityTB, stateTB, postalTB;
	private Rows miscItemRows;
	private Row miscItemRow1, miscItemRow2, miscItemRow3;
	
	private int entityNo;
	private AmtbAccount topLevelAccountWithEntity;
	
	public ViewRequestIssuanceWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer issueReqNo =  (Integer) params.get("issueReqNo");
		logger.info("Request ID = " + issueReqNo);
		request = businessHelper.getInventoryBusiness().getIssuanceRequest(issueReqNo);
		
		AmtbAccount account = request.getAmtbAccount();
		while(account.getAmtbAccount()!=null)
			account = account.getAmtbAccount();
		topLevelAccountWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(account);
		entityNo = topLevelAccountWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		requestorLabel.setValue(request.getCreateBy());
		
		// Account information
		AmtbAccount account = request.getAmtbAccount();
		String accountCategory = account.getAccountCategory();
		accountTypeLbl.setValue(NonConfigurableConstants.ACCOUNT_CATEGORY.get(accountCategory));
		if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
			accountNoLbl.setValue(account.getCustNo());
			accountNameLbl.setValue(account.getAccountName());
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
			accountNoLbl.setValue(account.getAmtbAccount().getCustNo());
			accountNameLbl.setValue(account.getAmtbAccount().getAccountName());
			divisionNameLbl.setValue(account.getAccountName());

			divisionNameLbl.setVisible(true);
			divisionNameLbl.getPreviousSibling().setVisible(true);
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			accountNoLbl.setValue(account.getAmtbAccount().getAmtbAccount().getCustNo());
			accountNameLbl.setValue(account.getAmtbAccount().getAmtbAccount().getAccountName());
			divisionNameLbl.setValue(account.getAmtbAccount().getAccountName());
			departmentNameLbl.setValue(account.getAccountName());

			divisionNameLbl.setVisible(true);
			departmentNameLbl.setVisible(true);
			divisionNameLbl.getPreviousSibling().setVisible(true);
			departmentNameLbl.getPreviousSibling().setVisible(true);
			departmentNameLbl.getParent().setVisible(true);
		}

		// Contact information
		AmtbContactPerson contactPerson = request.getAmtbContactPerson();
		contactPerson1Lbl.setValue(contactPerson.getMainContactName());
		if(contactPerson.getMainContactTitle()!=null && contactPerson.getMainContactTitle().length()>0)
			jobTitleLbl.setValue(contactPerson.getMainContactTitle());
		if(contactPerson.getMainContactTel()!=null && contactPerson.getMainContactTel().length()>0)
			officeContactNoLbl.setValue(contactPerson.getMainContactTel());
		if(contactPerson.getMainContactMobile()!=null && contactPerson.getMainContactMobile().length()>0)
			mobileContactNoLbl.setValue(contactPerson.getMainContactMobile());
		if(contactPerson.getMainContactFax()!=null && contactPerson.getMainContactFax().length()>0)
			faxLbl.setValue(contactPerson.getMainContactFax());
		if(contactPerson.getMainContactEmail()!=null && contactPerson.getMainContactEmail().length()>0)
			emailLbl.setValue(contactPerson.getMainContactEmail());
		if(contactPerson.getSubContactName()!=null && contactPerson.getSubContactName().length()>0)
			contactPerson2Lbl.setValue(contactPerson.getSubContactName());
		if(contactPerson.getSubContactTel()!=null && contactPerson.getSubContactTel().length()>0)
			officeContactNo2Lbl.setValue(contactPerson.getSubContactTel());
		if(contactPerson.getSubContactMobile()!=null && contactPerson.getSubContactMobile().length()>0)
			mobileContactNo2Lbl.setValue(contactPerson.getSubContactMobile());
		if(contactPerson.getSubContactEmail()!=null && contactPerson.getSubContactEmail().length()>0)
			email2Lbl.setValue(contactPerson.getSubContactEmail());

		// Request information
		String reqStatus = request.getImtbIssueReqFlow().getToStatus();
		itemTypeNameLabel.setValue(request.getImtbItemType().getTypeName());
		requestDateLabel.setValue(DateUtil.convertDateToStr(request.getRequestDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
		requestStatusLabel.setValue(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(reqStatus));
		quantityLabel.setValue(StringUtil.numberToString(request.getQuantity(), StringUtil.GLOBAL_INTEGER_FORMAT));
		requestNoLbl.setValue(request.getIssueReqNo().toString());
		serialNoStartLbl.setValue(request.getSerialNoStart().toString());
		serialNoEndLbl.setValue(request.getSerialNoEnd().toString());
		expiryDateLbl.setValue(DateUtil.convertDateToStr(request.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
		handlingFeeLbl.setValue(StringUtil.bigDecimalToString(request.getHandlingFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		discountLbl.setValue(StringUtil.bigDecimalToString(request.getDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		deliveryChargesLbl.setValue(StringUtil.bigDecimalToString(request.getDeliveryCharges(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		requestorRemarksLbl.setValue(request.getRequestorRemarks());
		// Button
		if (reqStatus.equals(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING)) {
			if(checkUriAccess(Uri.APPROVE_INVENTORY_REQUEST_ISSUANCE)){
				approveButton.setVisible(true);
				rejectButton.setVisible(true);
				
				remarksTB.setVisible(true);
				remarksTB.getParent().setVisible(true);
			}
		} 
		else {
			String remarks = (request.getImtbIssueReqFlow().getRemarks() != null) ? request.getImtbIssueReqFlow().getRemarks() : "-";
			remarksLabel.setValue(remarks);
			remarksLabel.setVisible(true);
			remarksLabel.getParent().setVisible(true);
			
			// Issuance information
			if (reqStatus.equals(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED) &&
					this.checkUriAccess(Uri.ISSUE_INVENTORY_INVOICE)) {
				issueButton.setVisible(true);
				contactDetailsDiv.setVisible(false);
				invoiceDetailsDiv.setVisible(true);
				this.populateInvoiceDetails();
			} 
		}

		remarksTB.focus();
	}

	private String buildAddress(AmtbContactPerson contactPerson) {
		String block = contactPerson.getAddressBlock();
		String building = contactPerson.getAddressBuilding();
		String area = contactPerson.getAddressArea();
		String city = contactPerson.getAddressCity();
		String state = contactPerson.getAddressCity();
		String country = contactPerson.getMstbMasterTableByAddressCountry().getMasterValue();
		String postal = contactPerson.getAddressPostal();
		String street = contactPerson.getAddressStreet();
		String unit = contactPerson.getAddressUnit();

		String address = "";

		if(area != null) {
			address = area + ", ";
		}
		if(block != null) {
			address = block + ", ";
		}
		if(street != null) {
			address += street + ", ";
		}
		if(unit!=null) {
			address += unit + ", ";
		}
		if(building != null) {
			address += building + ", ";
		}
		if(country != null) {
			address += country + " ";
		}
		if(state != null) {
			address += state + ", ";
		}
		if(city != null) {
			address += city + ", ";
		}
		if(postal != null) {
			address += postal;
		}
		return address;
	}

	public void approveRequest() throws InterruptedException {
		ImtbIssueReqFlow requestFlow = buildIssueReqFlow();
		try {
			//check if request has been approved or rejected to avoid duplicate approval
			String reqStatus = request.getImtbIssueReqFlow().getToStatus();
			if(!NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING.equals(reqStatus)) {
				throw new ConcurrencyFailureException("Concurrency Error");
			}
			
			displayProcessing();
			businessHelper.getInventoryBusiness().approveRequest(requestFlow);
			Messagebox.show("Request has been successfully approved", "Approve Issuance Request",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
			
		}
		catch (ConcurrencyFailureException e) {
			Messagebox.show("There exists concurrent user approves or rejects the same item issue request. Please check whether the item issue request has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void rejectRequest() throws InterruptedException {
		ImtbIssueReqFlow requestFlow = buildIssueReqFlow();
		try {
			//check if request has been approved or rejected to avoid duplicate approval
			String reqStatus = request.getImtbIssueReqFlow().getToStatus();
			if(!NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING.equals(reqStatus)) {
				throw new ConcurrencyFailureException("Concurrency Error");
			}

			displayProcessing();
			businessHelper.getInventoryBusiness().rejectRequest(requestFlow);
			Messagebox.show("Request has been successfully rejected", "Reject Issuance Request",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		}
		catch (ConcurrencyFailureException e) {
			Messagebox.show("There exists concurrent user approves or rejects the same item issue request. Please check whether the item issue request has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private ImtbIssueReqFlow buildIssueReqFlow() {
		ImtbIssueReqFlow requestFlow = new ImtbIssueReqFlow();
		requestFlow.setImtbIssueReq(request);
		requestFlow.setRemarks(remarksTB.getValue());
		requestFlow.setSatbUser(businessHelper.getUserBusiness().getUserWithRoles(getUserId()));

		return requestFlow;
	}

	public void issueStock() throws Exception {
		ImtbIssue issue = new ImtbIssue();
		issue.setImtbIssueReq(request);
		
		//Handling Fee
		Decimalbox unitPriceDMB = (Decimalbox) miscItemRow2.getChildren().get(UNIT_PRICE_COL);
		Longbox qtyLB = (Longbox) miscItemRow2.getChildren().get(QUANTITY_COL);
		BigDecimal unitPrice = unitPriceDMB.getValue();
		BigDecimal qty = new BigDecimal(qtyLB.getValue());
		issue.setHandlingFee(unitPrice.multiply(qty));
		
		//Discount
		Decimalbox discountDMB = (Decimalbox) miscItemRow2.getChildren().get(DISCOUNT_COL);
		if(discountDMB.isDisabled()==false)
			issue.setDiscount(discountDMB.getValue());
		else
			issue.setDiscount(BigDecimal.ZERO);
		
		//Delivery Charges
		Decimalbox dcUnitPriceDMB = (Decimalbox) miscItemRow3.getChildren().get(UNIT_PRICE_COL);
		Longbox dcQtyLB = (Longbox) miscItemRow3.getChildren().get(QUANTITY_COL);
		BigDecimal dcUnitPrice = dcUnitPriceDMB.getValue();
		BigDecimal dcQty = new BigDecimal(dcQtyLB.getValue());
		issue.setDeliveryCharges(dcUnitPrice.multiply(dcQty));
		issue.setExpiryDate(DateUtil.convertUtilDateToSqlDate(request.getExpiryDate()));
		issue.setSatbUser(businessHelper.getUserBusiness().getUserWithRoles(getUserId()));

		BmtbInvoiceHeader invoiceHeader = this.buildInvoiceHeader();
		if (invoiceHeader == null) {
			return;
		}
		
		try {
			displayProcessing();
			businessHelper.getInventoryBusiness().issueStock(invoiceHeader, issue, request.getSerialNoStart(), request.getSerialNoEnd(), getUserLoginIdAndDomain());
			
			AMedia media = generate(invoiceHeader);
			int response = Messagebox.show(
					"Stock & Misc. Invoice has been successfully issued. The Invoice No. is " +
					invoiceHeader.getInvoiceNo() + ". Do you wish to download the invoice?", "Issue Stock & Misc. Invoice",
					Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
			if (response == Messagebox.YES) {
				Filedownload.save(media);
			}
			this.back();
		} catch (InventorySerialNoUnavailableException e) {
			Messagebox.show(e.getMessage(), "Serial No. Unavailable", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
	
	public void updateMiscItem(Row row) {
		List<Component> cells = row.getChildren();
		
		BigDecimal unitPrice;
		Long quantity;
		if(cells.get(UNIT_PRICE_COL) instanceof Label){
			unitPrice = request.getImtbItemType().getPrice();
			quantity = request.getQuantity();
		}
		else{
			unitPrice = ((Decimalbox) cells.get(UNIT_PRICE_COL)).getValue();
			quantity = ((Longbox) cells.get(QUANTITY_COL)).getValue();
		}

		Combobox txnTypeCB = (Combobox) cells.get(TXN_TYPE_COL);
		Decimalbox discountDB = (Decimalbox) cells.get(DISCOUNT_COL);
		//Checkbox taxableCB = (Checkbox) cells.get(TAXABLE_COL);

		FmtbTransactionCode txnCode = null;
		if (txnTypeCB.getSelectedItem() != null) {
			txnCode = (FmtbTransactionCode) txnTypeCB.getSelectedItem().getValue();
		}

		BigDecimal gstRate = BigDecimal.ZERO;
		if (txnCode != null) {
			// Disable Discount field if Txn Code is not discountable
			discountDB.setDisabled(txnCode.getDiscountable().equals(
					com.cdgtaxi.ibs.acl.Constants.BOOLEAN_NO));
			if (discountDB.isDisabled()) {
				discountDB.setValue(BigDecimal.ZERO);
			}

			// Disable GST field if Txn Code is not taxable
			FmtbTaxCode taxCode = businessHelper.getFinanceBusiness().getEffectiveTaxCode(entityNo, txnCode.getMstbMasterTable().getMasterNo());
			if (taxCode.getTaxRate().signum() > 0)
				gstRate = taxCode.getTaxRate().divide(new BigDecimal(100));
		}

		BigDecimal discount = discountDB.getValue();
		

		BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
		((Label) cells.get(TOTAL_PRICE_COL)).setValue(
				StringUtil.bigDecimalToString(totalPrice, StringUtil.GLOBAL_DECIMAL_FORMAT));

		if (discount.compareTo(totalPrice) > 0) {
			throw new WrongValueException(discountDB, DISCOUNT_GREATER_THAN_TOTAL_PRICE);
		} else {
			discountDB.clearErrorMessage();
		}

		BigDecimal taxableAmount = totalPrice.subtract(discount);
		BigDecimal gst = BigDecimal.ZERO;
		if (gstRate.signum() > 0)
			gst = taxableAmount.multiply(gstRate);
		((Label) cells.get(GST_COL)).setValue(
				StringUtil.bigDecimalToString(gst, StringUtil.GLOBAL_DECIMAL_FORMAT));

		BigDecimal netAmount = taxableAmount.add(gst);
		((Label) cells.get(NET_AMOUNT_COL)).setValue(
				StringUtil.bigDecimalToString(netAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));

		calculateGrandTotal();
	}
	
	private void calculateGrandTotal() {
		BigDecimal grandTotal = BigDecimal.ZERO;
		for (Row row : (List<Row>) miscItemRows.getChildren()) {
			Label netAmountLabel = (Label) row.getChildren().get(NET_AMOUNT_COL);
			BigDecimal netAmount = new BigDecimal(netAmountLabel.getValue().replace(",", ""));
			grandTotal = grandTotal.add(netAmount);
		}

		totalAmountLabel.setValue(
				StringUtil.bigDecimalToString(grandTotal, StringUtil.GLOBAL_DECIMAL_FORMAT));
	}
	
	private void populateInvoiceDetails(){
		//init constraint
		cityTBConstraint = cityTB.getConstraint();
		stateTBConstraint = stateTB.getConstraint();
		
		invoiceDateDB.setValue(DateUtil.getCurrentUtilDate());
		
		creditTermCB = (Combobox) getFellow("creditTermCB");
		Map<Integer, Map<String, String>> map =
			MasterSetup.getCreditTermManager().getCurrentDetails();
		// Hard code the COD
		Comboitem tempItem = new Comboitem(COD_DESC);
		tempItem.setValue(null);
		creditTermCB.appendChild(tempItem);
		for (Entry<Integer, Map<String, String>> entry : map.entrySet()) {
			String description =
				entry.getValue().get(CreditTermMasterManager.MASTER_NAME) + " (" +
				entry.getValue().get(CreditTermMasterManager.DETAIL_CREDIT_TERM) + " days)";
			Comboitem item = new Comboitem(description);
			item.setValue(entry.getKey());
			creditTermCB.appendChild(item);
		}
		
		AmtbAccount account = request.getAmtbAccount();
		AmtbAccount topLevelAccount =
			businessHelper.getAccountBusiness().getTopLevelAccount(account);
		AmtbAccount topLevelAccountWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(topLevelAccount);
		Integer entityNo = topLevelAccountWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();
		
		Combobox txnTypeCB = (Combobox) miscItemRow1.getChildren().get(TXN_TYPE_COL);
		Combobox txnTypeCB2 = (Combobox) miscItemRow2.getChildren().get(TXN_TYPE_COL);
		Combobox txnTypeCB3 = (Combobox) miscItemRow3.getChildren().get(TXN_TYPE_COL);
		
		List<FmtbTransactionCode> codes =
			businessHelper.getFinanceBusiness().getEffectiveManualTxnCodes(entityNo);
		for (FmtbTransactionCode code : codes) {
			Comboitem item = new Comboitem(code.getDescription());
			item.setValue(code);
			txnTypeCB.appendChild(item);
			txnTypeCB2.appendChild((Comboitem)item.clone());
			txnTypeCB3.appendChild((Comboitem)item.clone());
		}
		
		Textbox descriptionTB1 = (Textbox) miscItemRow1.getChildren().get(DESCRIPTION_COL);
		Textbox descriptionTB2 = (Textbox) miscItemRow2.getChildren().get(DESCRIPTION_COL);
		Textbox descriptionTB3 = (Textbox) miscItemRow3.getChildren().get(DESCRIPTION_COL);
		if(request.getSerialNoStart().compareTo(request.getSerialNoEnd()) == 0)
			descriptionTB1.setValue(request.getImtbItemType().getTypeName()+" ("+request.getSerialNoStart().toString()+")");
		else
			descriptionTB1.setValue(request.getImtbItemType().getTypeName()+" ("+request.getSerialNoStart().toString()+" to "+request.getSerialNoEnd().toString()+")");
		descriptionTB2.setValue(ConfigurableConstants.getAllMasterTable(ConfigurableConstants.HANDLING_FEE_DESCRIPTION, ConfigurableConstants.HANDLING_FEE_DESCRIPTION).getMasterValue());
		descriptionTB3.setValue(ConfigurableConstants.getAllMasterTable(ConfigurableConstants.DELIVERY_CHARGES_DESCRIPTION, ConfigurableConstants.DELIVERY_CHARGES_DESCRIPTION).getMasterValue());
		
		Label unitPriceLabel = (Label) miscItemRow1.getChildren().get(UNIT_PRICE_COL);
		unitPriceLabel.setValue(StringUtil.bigDecimalToString(request.getImtbItemType().getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		Decimalbox unitPriceDMB2 = (Decimalbox) miscItemRow2.getChildren().get(UNIT_PRICE_COL);
		unitPriceDMB2.setValue(request.getHandlingFee());
		if(request.getHandlingFee().compareTo(BigDecimal.ZERO) == 0) miscItemRow2.setVisible(false);
		Decimalbox unitPriceDMB3 = (Decimalbox) miscItemRow3.getChildren().get(UNIT_PRICE_COL);
		unitPriceDMB3.setValue(request.getDeliveryCharges());
		if(request.getDeliveryCharges().compareTo(BigDecimal.ZERO) == 0) miscItemRow3.setVisible(false);
		
		Label qtyLabel = (Label) miscItemRow1.getChildren().get(QUANTITY_COL);
		qtyLabel.setValue(request.getQuantity()+"");
		Longbox qtyLB2 = (Longbox) miscItemRow2.getChildren().get(QUANTITY_COL);
		qtyLB2.setRawValue(new Long(1));
		Longbox qtyLB3 = (Longbox) miscItemRow3.getChildren().get(QUANTITY_COL);
		qtyLB3.setRawValue(new Long(1));
		
		Decimalbox discountDMB = (Decimalbox) miscItemRow1.getChildren().get(DISCOUNT_COL);
		discountDMB.setValue(request.getDiscount());
		
		Label totalPriceLabel = (Label) miscItemRow1.getChildren().get(TOTAL_PRICE_COL);
		totalPriceLabel.setValue(StringUtil.bigDecimalToString(
				request.getImtbItemType().getPrice().multiply(new BigDecimal(request.getQuantity()))
				, StringUtil.GLOBAL_DECIMAL_FORMAT)
			);
		
		AmtbContactPerson contactPerson =
			businessHelper.getAccountBusiness().getMainContactByType(
					account.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);

		if (contactPerson != null) {
			contactPersonLabel.setValue(contactPerson.getMainContactName());
			jobTitleLabel.setValue(contactPerson.getMainContactTitle());
			officeNoLabel.setValue(contactPerson.getMainContactTel());
			mobileNoLabel.setValue(contactPerson.getMainContactMobile());
			faxNoLabel.setValue(contactPerson.getMainContactFax());
			emailLabel.setValue(contactPerson.getMainContactEmail());
			contactPerson2Label.setValue(contactPerson.getSubContactName());
			officeNo2Label.setValue(contactPerson.getSubContactTel());
			mobileNo2Label.setValue(contactPerson.getSubContactMobile());
			email2Label.setValue(contactPerson.getSubContactEmail());
		}
		
		String contactCountryCode = null;
		if (contactPerson != null) {
			contactCountryCode = contactPerson.getMstbMasterTableByAddressCountry().getMasterCode();
		}
		Comboitem selectedItem = null;
		List<Comboitem> items = ComponentUtil.convertToComboitems(ConfigurableConstants.getCountries(), true);
		for (Comboitem item : items) {
			if (item.getValue().equals(contactCountryCode)) {
				selectedItem = item;
			}
			countryCB.appendChild(item);
		}
		
		customerNameTB.setValue(topLevelAccount.getAccountName());
		String billedTo = "";
		if(!account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) &&
				!account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
			if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
				AmtbAccount divAcct = account.getAmtbAccount();
				billedTo = divAcct.getAccountName()+" ("+divAcct.getCode()+") / "+
					account.getAccountName()+" ("+account.getCode()+")";
			}
			else billedTo = account.getAccountName()+" ("+account.getCode()+")";
		}
		billedToTB.setValue(billedTo);
		
		if (contactPerson != null) {
			if (contactPerson.getSubContactName() != null && !"".equals(contactPerson.getSubContactName()))
			{
				billingContactCB.setRawValue(contactPerson.getMainContactName() + " / " + contactPerson.getSubContactName());
				billingContactCB.appendItem(contactPerson.getMainContactName() + " / " + contactPerson.getSubContactName());
			}
			else
			{
				billingContactCB.setRawValue(contactPerson.getMainContactName());
				billingContactCB.appendItem(contactPerson.getMainContactName());
			}
			blockNoTB.setRawValue(contactPerson.getAddressBlock());
			unitNoTB.setRawValue(contactPerson.getAddressUnit());
			streetNameTB.setRawValue(contactPerson.getAddressStreet());
			buildingNameTB.setRawValue(contactPerson.getAddressBuilding());
			areaTB.setRawValue(contactPerson.getAddressArea());
			countryCB.setSelectedItem(selectedItem);
			//			countryLB.setSelectedItem(selectedItem);
			cityTB.setRawValue(contactPerson.getAddressCity());
			stateTB.setRawValue(contactPerson.getAddressState());
			postalTB.setRawValue(contactPerson.getAddressPostal());
			checkCountry();
		}
	}
	
	public void checkCountry() {
		if (countryCB.getSelectedItem() == null) {
			return;
		}

		String country = (String) countryCB.getSelectedItem().getValue();
		if (country != null && country.equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)) {
			cityTB.setConstraint(nullConstraint);
			cityTB.setValue(null);
			cityTB.setDisabled(true);

			stateTB.setConstraint(nullConstraint);
			stateTB.setValue(null);
			stateTB.setDisabled(true);
		} else {
			cityTB.setConstraint(cityTBConstraint);
			stateTB.setConstraint(stateTBConstraint);
			cityTB.setDisabled(false);
			stateTB.setDisabled(false);
		}
	}
	
	private BmtbInvoiceHeader buildInvoiceHeader() throws Exception {
		try {
			creditTermCB.getValue(); // just for firing the validation
			Integer creditTerm = (Integer) creditTermCB.getSelectedItem().getValue();
			Date invoiceDate = invoiceDateDB.getValue();
			Date dueDate = null;
			if (creditTerm != null)
			{
				MstbCreditTermDetail creditTermDetail =
					(MstbCreditTermDetail) MasterSetup.getCreditTermManager().getDetail(creditTerm);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(invoiceDate);
				calendar.add(Calendar.DATE, creditTermDetail.getCreditTerm());
				dueDate = calendar.getTime();
			}
			else
				// For COD, the due date is null
				dueDate = null;

			BmtbInvoiceHeader invoiceHeader = new BmtbInvoiceHeader();
			invoiceHeader.setAmtbAccountByAccountNo(request.getAmtbAccount());
			invoiceHeader.setAmtbAccountByDebtTo(request.getAmtbAccount());
			invoiceHeader.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDate));
			if (dueDate != null)
				invoiceHeader.setDueDate(DateUtil.convertUtilDateToSqlDate(dueDate));
			else
				// For COD, the due date is null
				invoiceHeader.setDueDate(null);
			
			invoiceHeader.setRemarks(invoiceRemarksTB.getValue());
			invoiceHeader.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
			countryCB.getValue();
			cityTB.getValue();
			stateTB.getValue();
			invoiceHeader.setUpdatedBy(getUserLoginIdAndDomain());

			if (miscItemRows.getChildren().size() == 0) {
				Messagebox.show("At least one miscellaneous item is required", "Error",
						Messagebox.OK, Messagebox.ERROR);
				return null;
			}

			Set<BmtbInvoiceSummary> summaries = new HashSet<BmtbInvoiceSummary>();
			for (Row row : (List<Row>) miscItemRows.getChildren()) {
				if(!row.isVisible()) continue;
				
					List<Component> cells = row.getChildren();

				String description = ((Textbox) cells.get(DESCRIPTION_COL)).getValue();
				((Combobox) cells.get(TXN_TYPE_COL)).getText(); //to trigger required constraint
				FmtbTransactionCode txnCode = (FmtbTransactionCode) ((Combobox) cells.get(TXN_TYPE_COL)).getSelectedItem().getValue();
				
				BigDecimal unitPrice;
				Long quantity;
				if(cells.get(UNIT_PRICE_COL) instanceof Label){
					unitPrice = request.getImtbItemType().getPrice();
					quantity = request.getQuantity();
				}
				else{
					unitPrice = ((Decimalbox) cells.get(UNIT_PRICE_COL)).getValue();
					quantity = ((Longbox) cells.get(QUANTITY_COL)).getValue();
				}
				
				Decimalbox discountDB = (Decimalbox) cells.get(DISCOUNT_COL);
				BigDecimal discount = discountDB.getValue();
				BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
				if (discount.compareTo(totalPrice) > 0) {
					throw new WrongValueException(discountDB, DISCOUNT_GREATER_THAN_TOTAL_PRICE);
				} else {
					discountDB.clearErrorMessage();
				}

				BigDecimal taxableAmount = totalPrice.subtract(discount);
				BigDecimal gstRate = BigDecimal.ZERO;
				FmtbTaxCode taxCode = businessHelper.getFinanceBusiness().getEffectiveTaxCode(entityNo, txnCode.getMstbMasterTable().getMasterNo());
				if (taxCode.getTaxRate().signum() > 0)
					gstRate = taxCode.getTaxRate().divide(new BigDecimal(100));
				BigDecimal gst = BigDecimal.ZERO;
				
				if (gstRate.signum() > 0) {
					gst = taxableAmount.multiply(gstRate);
				}

				BmtbInvoiceSummary summary = new BmtbInvoiceSummary();
				summary.setSummaryName(description);
				summary.setUnitPrice(unitPrice);
				summary.setQuantity(quantity);
				summary.setDiscount(discount);
				summary.setGst(gst.setScale(2, BigDecimal.ROUND_HALF_UP));
				summaries.add(summary);

				Set<BmtbInvoiceDetail> details = new HashSet<BmtbInvoiceDetail>();
				summary.setBmtbInvoiceDetails(details);

				BmtbInvoiceDetail detail = new BmtbInvoiceDetail();
				//To allow issue misc item for EP, MS, LP and ETC
				//IT team side is to manually control not to display txn type items like fixed $10 with AF as txn type.
				detail.setInvoiceDetailType(txnCode.getTxnType());
				detail.setFmtbTransactionCode(txnCode);
				details.add(detail);
			}
			invoiceHeader.setBmtbInvoiceSummaries(summaries);
			
			//GIRO date
			if(topLevelAccountWithEntity.getMstbMasterTableByDefaultPaymentMode().getMasterCode()
					.equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
				
				IttbGiroSetup giroSetup = this.businessHelper.getAdminBusiness().getInvoiceGiroDay(
						topLevelAccountWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo());
				
				if(giroSetup == null)
					throw new Exception("GIRO SETUP is not found");
				
				invoiceHeader.setGiroBankAcctNo(topLevelAccountWithEntity.getMstbBankMaster().getBankName()
						+" "+topLevelAccountWithEntity.getBankAcctNo());
				
				Calendar giroDateCal = Calendar.getInstance();
				giroDateCal.setTime(invoiceDate);
				
				int giroDay = giroSetup.getInvoiceGiroDay();
				int lastDayOfMonth = giroDateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
				if(lastDayOfMonth < giroSetup.getInvoiceGiroDay()) giroDay = lastDayOfMonth;
				
				invoiceHeader.setGiroDay(DateUtil.getDayInStr(giroDay));
			}
			
			return invoiceHeader;
		} catch(WrongValueException wve) {
			((HtmlBasedComponent) wve.getComponent()).focus();
			throw wve;
		}
	}
	
	private AMedia generate(BmtbInvoiceHeader header) throws HttpException, IOException, InterruptedException, NetException {

		String customerName = customerNameTB.getValue();
		String billedTo = billedToTB.getValue();
		String address1 = null;
		if(blockNoTB.getValue()!=null && blockNoTB.getValue().length()>0) {
			address1 = blockNoTB.getValue() + " " + streetNameTB.getValue();
		} else {
			address1 = streetNameTB.getValue();
		}
		String address2 = null;
		if(buildingNameTB.getValue()!=null && buildingNameTB.getValue().length()>0 &&
				unitNoTB.getValue()!=null && unitNoTB.getValue().length()>0) {
			address2 = unitNoTB.getValue() + " " + buildingNameTB.getValue();
		} else if(unitNoTB.getValue()!=null && unitNoTB.getValue().length()>0) {
			address2 = unitNoTB.getValue();
		} else if(buildingNameTB.getValue()!=null && buildingNameTB.getValue().length()>0) {
			address2 = buildingNameTB.getValue();
		}
		String address3 = null;
		if(areaTB.getValue()!=null && areaTB.getValue().length()>0) {
			address3 = areaTB.getValue();
		}
		String address4 = null;
		if(countryCB.getSelectedItem().getLabel().equals("SINGAPORE")) {
			address4 = countryCB.getSelectedItem().getLabel() + " " + postalTB.getValue();
		} else {
			address4 = countryCB.getSelectedItem().getLabel() + " " + stateTB.getValue() + " " + cityTB.getValue() + " " + postalTB.getValue();
		}
		String postal = postalTB.getValue();
		String billingContact = billingContactCB.getValue();
		billingContact = billingContact.toUpperCase();

		Properties params = new Properties();
		params.put("invoiceHeaderNo", header.getInvoiceHeaderNo().toString());
		params.put("customer name", customerName.replaceAll("'", "''"));
		params.put("bill to account name", billedTo.replaceAll("'", "''"));
		params.put("address 1", address1.replaceAll("'", "''"));
		if(address2!=null) {
			params.put("address 2", address2.replaceAll("'", "''"));
		}
		if(address3!=null) {
			params.put("address 3", address3.replaceAll("'", "''"));
		}
		params.put("address 4", address4.replaceAll("'", "''"));
		params.put("postal", postal);
		params.put("billing contact", billingContact.replaceAll("'", "''"));
		
		Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
		Integer.parseInt((String)pdfGenProperties.get("pdfgen.buffer.duedate"));
		params.put("bufferDays", pdfGenProperties.get("pdfgen.buffer.duedate"));

		String outputFormat = Constants.FORMAT_PDF;
		String reportName = NonConfigurableConstants.REPORT_NAME_INV_MISC;

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, null, outputFormat, params);

		BmtbInvoiceFile newInvoiceFile = new BmtbInvoiceFile();
		newInvoiceFile.setInvoiceFile(Hibernate.createBlob(bytes));
		header.setBmtbInvoiceFile(newInvoiceFile);
		businessHelper.getGenericBusiness().save(newInvoiceFile);
		businessHelper.getGenericBusiness().update(header);

		return new AMedia(reportName + ".pdf", "pdf", outputFormat, bytes);
	}
}
