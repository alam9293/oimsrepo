package com.cdgtaxi.ibs.billing.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.api.HtmlBasedComponent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.CreditTermMasterManager;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceFile;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

@SuppressWarnings("serial")
public class IssueMiscInvoiceWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(IssueMiscInvoiceWindow.class);
	private static final String DISCOUNT_GREATER_THAN_TOTAL_PRICE = "* Discount must be less than or equal to the Total Price";
	private static final String COD_DESC = "CASH ON DELIVERY";

	private AmtbAccount account;
	private int entityNo;
	private AmtbAccount topLevelAccountWithEntity;

	private Datebox invoiceDateDB;
	private Combobox creditTermCB, billingContactCB, countryCB;
	//	private Listbox countryLB;
	private Label totalAmountLabel;
	private Textbox remarksTB, customerNameTB, blockNoTB, unitNoTB, streetNameTB, billedToTB,
	buildingNameTB, areaTB, cityTB, stateTB, postalTB;
	private Rows miscItemRows;
	private Row newMiscItemRowTemplate;
	private int miscItemCount;

	private Constraint cityTBConstraint, stateTBConstraint;
	private final Constraint nullConstraint = null;

	private final int DESCRIPTION_COL = 0, UNIT_PRICE_COL = 1, QUANTITY_COL = 2,
	TOTAL_PRICE_COL = 3, TXN_TYPE_COL = 4, DISCOUNT_COL = 5, 
	GST_COL = 6,  NET_AMOUNT_COL = 7;

	@SuppressWarnings("unchecked")
	public IssueMiscInvoiceWindow() {
		Map params = Executions.getCurrent().getArg();
		account = (AmtbAccount) params.get("account");
		account = businessHelper.getAccountBusiness().getAccountWithParent(account);
	}

	public void onCreate() {
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountNameLabel = (Label) getFellow("accountNameLabel");
		Label billedToLabel = (Label) getFellow("billedToLabel");

		AmtbAccount topLevelAccount =
			businessHelper.getAccountBusiness().getTopLevelAccount(account);
		accountNoLabel.setValue(topLevelAccount.getCustNo());
		accountNameLabel.setValue(topLevelAccount.getAccountName());
		if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(account.getAccountCategory()) || 
				NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(account.getAccountCategory()) || 
				NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(account.getAccountCategory()))
		{
			billedToLabel.setValue(account.getAccountName() + " (" + account.getCode() + ")");
		}
		else
			billedToLabel.setValue(account.getAccountName());
		
		
		// set entity no
		topLevelAccountWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(topLevelAccount);
		entityNo = topLevelAccountWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();

		Date currentDate = new Date();

		invoiceDateDB = (Datebox) getFellow("invoiceDateDB");
		invoiceDateDB.setValue(currentDate);

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

		totalAmountLabel = (Label) getFellow("totalAmountLabel");
		totalAmountLabel.setValue(StringUtil.bigDecimalToString(
				BigDecimal.ZERO, StringUtil.GLOBAL_DECIMAL_FORMAT));

		remarksTB = (Textbox) getFellow("remarksTB");

		AmtbContactPerson contactPerson =
			businessHelper.getAccountBusiness().getMainContactByType(
					account.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);

		// Static fields
		Label contactPersonLabel = (Label) getFellow("contactPersonLabel");
		Label jobTitleLabel = (Label) getFellow("jobTitleLabel");
		Label officeNoLabel = (Label) getFellow("officeNoLabel");
		Label mobileNoLabel = (Label) getFellow("mobileNoLabel");
		Label faxNoLabel = (Label) getFellow("faxNoLabel");
		Label emailLabel = (Label) getFellow("emailLabel");
		Label contactPerson2Label = (Label) getFellow("contactPerson2Label");
		Label officeNo2Label = (Label) getFellow("officeNo2Label");
		Label mobileNo2Label = (Label) getFellow("mobileNo2Label");
		Label email2Label = (Label) getFellow("email2Label");

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

		// Misc items
		Combobox productCB = (Combobox) getFellow("productCB");
		List<Comboitem> items = ComponentUtil.convertToComboitems(
				ConfigurableConstants.getMiscInvoiceItems(), true);
		for (Comboitem item : items) {
			productCB.appendChild(item);
		}

		Combobox txnTypeCB = (Combobox) getFellow("txnTypeCB");
		List<FmtbTransactionCode> codes =
			businessHelper.getFinanceBusiness().getEffectiveManualTxnCodes(entityNo);
		for (FmtbTransactionCode code : codes) {
			Comboitem item = new Comboitem(code.getDescription());
			item.setValue(code);
			txnTypeCB.appendChild(item);
		}

		miscItemRows = (Rows) getFellow("miscItemRows");
		Row row = (Row) getFellow("miscItemRow");
		newMiscItemRowTemplate = (Row) row.clone();
		miscItemRows.removeChild(row);
		miscItemCount = 0;
		newMiscItem(null);

		// Input / dynamic fields
		customerNameTB = (Textbox) getFellow("customerNameTB");
		billingContactCB = (Combobox) getFellow("billingContactCB");
		blockNoTB = (Textbox) getFellow("blockNoTB");
		unitNoTB = (Textbox) getFellow("unitNoTB");
		streetNameTB = (Textbox) getFellow("streetNameTB");
		buildingNameTB = (Textbox) getFellow("buildingNameTB");
		areaTB = (Textbox) getFellow("areaTB");
		cityTB = (Textbox) getFellow("cityTB");
		countryCB = (Combobox) getFellow("countryCB");
		stateTB = (Textbox) getFellow("stateTB");
		postalTB = (Textbox) getFellow("postalTB");
		cityTBConstraint = cityTB.getConstraint();
		stateTBConstraint = stateTB.getConstraint();
		billedToTB = (Textbox) getFellow("billedToTB");

		String contactCountryCode = null;
		if (contactPerson != null) {
			contactCountryCode = contactPerson.getMstbMasterTableByAddressCountry().getMasterCode();
		}
		Comboitem selectedItem = null;
		items = ComponentUtil.convertToComboitems(ConfigurableConstants.getCountries(), true);
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

	@SuppressWarnings("unchecked")
	public void newMiscItem(Button button) {
		miscItemCount++;
		Row newRow = (Row) newMiscItemRowTemplate.clone();
		newRow.setId(newRow.getId() + "_" + miscItemCount);
		for (Component component : (List<Component>) newRow.getChildren()) {
			component.setId(component.getId() + "_" + miscItemCount);
		}

		miscItemRows.appendChild(newRow);
		if (button != null) {
			((Combobox) newRow.getChildren().get(DESCRIPTION_COL)).setFocus(true);
		}
		//		logger.info("Added Misc. Item Row " + rowNo);
	}

	public void dropMiscItem(Row row) throws InterruptedException {
		int response = Messagebox.show("Are you sure you wish to drop this item?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION);

		if (response != Messagebox.OK) {
			return;
		}

		miscItemRows.removeChild(row);
		//		logger.info("Dropped Misc. Item Row " + row.getValue());
		row.detach();
		calculateGrandTotal();
	}

	@SuppressWarnings("unchecked")
	public void updateMiscItem(Row row) {
		List<Component> cells = row.getChildren();

		BigDecimal unitPrice = ((Decimalbox) cells.get(UNIT_PRICE_COL)).getValue();
		Long quantity = ((Longbox) cells.get(QUANTITY_COL)).getValue();

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
		if (gstRate.signum() > 0){
			gst = taxableAmount.multiply(gstRate);
			gst = gst.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		((Label) cells.get(GST_COL)).setValue(
				StringUtil.bigDecimalToString(gst, StringUtil.GLOBAL_DECIMAL_FORMAT));

		BigDecimal netAmount = taxableAmount.add(gst);
		((Label) cells.get(NET_AMOUNT_COL)).setValue(
				StringUtil.bigDecimalToString(netAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));

		calculateGrandTotal();
	}

	@SuppressWarnings("unchecked")
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

	public void issueMiscInvoice() throws Exception {
		BmtbInvoiceHeader invoiceHeader = buildInvoiceHeader();
		if (invoiceHeader == null) {
			return;
		}

		int response = Messagebox.show(
				"Generate invoice?", "Confirmation",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		if (response != Messagebox.OK) {
			return;
		}

		displayProcessing();

		try {
			businessHelper.getAccountBusiness().generateMiscInvoice(invoiceHeader);
			
			AMedia media = generate(invoiceHeader);
			response = Messagebox.show(
					"Invoice has been successfully issued and generated. The Invoice No. is " +
					invoiceHeader.getInvoiceNo() + ". Do you wish to download the invoice?", "Issue Misc. Invoice",
					Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
			if (response == Messagebox.YES) {
				Filedownload.save(media);
			}
			this.back();
		} 
		catch (WrongValueException wve){
			throw wve;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
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
			invoiceHeader.setAmtbAccountByAccountNo(account);
			invoiceHeader.setAmtbAccountByDebtTo(account);
			invoiceHeader.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDate));
			if (dueDate != null)
				invoiceHeader.setDueDate(DateUtil.convertUtilDateToSqlDate(dueDate));
			else
				// For COD, the due date is null
				invoiceHeader.setDueDate(null);
			
			invoiceHeader.setRemarks(remarksTB.getValue());
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

			Set<BmtbInvoiceSummary> summaries = new LinkedHashSet<BmtbInvoiceSummary>();
			Integer counter = 0;
			for (Row row : (List<Row>) miscItemRows.getChildren()) {
				List<Component> cells = row.getChildren();

				String description = ((Combobox) cells.get(DESCRIPTION_COL)).getValue();
				((Combobox) cells.get(TXN_TYPE_COL)).getText(); //to trigger required constraint
				FmtbTransactionCode txnCode = (FmtbTransactionCode) ((Combobox) cells.get(TXN_TYPE_COL)).getSelectedItem().getValue();
				BigDecimal unitPrice = ((Decimalbox) cells.get(UNIT_PRICE_COL)).getValue();
				Long quantity = ((Longbox) cells.get(QUANTITY_COL)).getValue();

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
				Timestamp t = new Timestamp(new Date().getTime());
				t.setNanos(++counter);
				summary.setCreatedDt(t);
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

	@Override
	public void refresh() throws InterruptedException {
	}
}
