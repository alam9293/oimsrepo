package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm.IssueType;
import com.cdgtaxi.ibs.common.ui.CommonSearchByDivisionDepartmentAccountWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.CardNoGenerator;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.ProductUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public abstract class CommonIssueProductWindow extends CommonSearchByDivisionDepartmentAccountWindow implements AfterCompose {

	protected Grid commonProductDetailsGridTitle, commonProductDetailsGrid;

	protected Label nameOnCardFieldLabel, nameOnCardLabel;
	protected Label parentCreditLimitLabel;
	protected Label binLabel, subBinLabel;
	protected Label cashplusFieldLabel;
	protected Label waiveSubscFeeLabel;

	protected Label expiryDateTitleLabel, expiryDateTimeTitleLabel;
	
	protected Row acctDetailsRow;
	protected Row binAndSubBinRow, creditLimitRow, fixedValueRow;
	protected Row expiryDateRow, expiryDateTimeRow;
	protected Row offlineCountRow, offlineAmountAccumulativeRow, offlineAmountPerTxnRow, embossNameRow;
	protected Row issueByCountRow, issueByRangeRow, nameOnCardRow;
	protected Row coporateDataFirstRow, coporateDataSecondRow, coporateDataThirdRow, coporateDataForthRow;
	protected Row cardValueRow;

	protected Listbox expiryHourListBox, expiryMinListBox;
	protected Datebox expiryDateField, expiryDayMonthYearDBField;
	protected Checkbox coporateCheckBox, waiveSubscFeeChkBox, waiveIssuanceFeeChkBox, embossNameOnCardChkBox;
	protected Decimalbox creditLimitField, fixedValueField, offlineAmountAccumulativeField, offlineAmountPerTxnField;
	protected Listbox productTypeListBox, salutationListBox, smsExpiryFlagListBox, smsTopUpFlagListBox;
	protected Intbox issueByCountField, cardNoStartField, cardNoEndField, offlineCountField;
	protected Combobox cardHolderNameField, cardDetailsNameOnCardField;
	protected Textbox positionField, telephoneField, mobileField, emailField, employeeIdField;
	protected Decimalbox cardValueField;

	protected Listbox balanceExpiryHourListBox, balanceExpiryMinListBox;
	protected Datebox balanceExpiryDateField,balanceExpiryDateTimeField;
	protected Row balanceExpiryDateRow, balanceExpiryDateTimeRow;
	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	@Override
	public void onCreate(CreateEvent ce) throws Exception {

		super.onCreate(ce);

		ComponentUtil.buildListbox(salutationListBox, ConfigurableConstants.getSalutations(), true);
		ComponentUtil.buildListbox(smsExpiryFlagListBox, NonConfigurableConstants.SMS_FLAG, false);
		ComponentUtil.buildListbox(smsTopUpFlagListBox, NonConfigurableConstants.SMS_FLAG, false);

		Map<Integer, String> expiryHourMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 24; i++) {
			expiryHourMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryHourListBox, expiryHourMap, false);
		ComponentUtil.setSelectedItem(expiryHourListBox, 23);

		Map<Integer, String> expiryMinMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 60; i++) {
			expiryMinMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryMinListBox, expiryMinMap, false);
		ComponentUtil.setSelectedItem(expiryMinListBox, 59);

		Map<Integer, String> balanceExpiryHourMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 24; i++) {
			balanceExpiryHourMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(balanceExpiryHourListBox, balanceExpiryHourMap, false);
		ComponentUtil.setSelectedItem(balanceExpiryHourListBox, 23);

		Map<Integer, String> balanceExpiryMinMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 60; i++) {
			balanceExpiryMinMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(balanceExpiryMinListBox, balanceExpiryMinMap, false);
		ComponentUtil.setSelectedItem(balanceExpiryMinListBox, 59);
		
		
		populateAcctDetails();
	}

	protected IssueProductForm getIssueProductForm() {

		IssueProductForm form = new IssueProductForm();

		// Getting Product Type ID from Product Type List
		PmtbProductType productType = ComponentUtil.getSelectedItem(productTypeListBox);

		// if Corporate Card
		String corpCardHolderName = "";
		String corpCardHolderSalutation = "";
		if (coporateDataFirstRow.isVisible()) {
			// Getting card holder's name
			if (cardHolderNameField.getSelectedItem() != null) {
				corpCardHolderName = ComponentUtil.getSelectedItem(cardHolderNameField);
			} else if (!Strings.isNullOrEmpty(cardHolderNameField.getText())) {
				// To retrieve the type-written string instead of from the selection
				corpCardHolderName = (String) cardHolderNameField.getValue();
			}

			corpCardHolderName = corpCardHolderName.trim().toUpperCase();

			if (Strings.isNullOrEmpty(corpCardHolderName)) {
				throw new WrongValueException("Card Holder Name Cannot Be Blank ");
			}
			// Getting card holder's salutation
			corpCardHolderSalutation = ComponentUtil.getSelectedItem(salutationListBox);
		}

		/*
		 * Moved from within corporate card out to here to cater for all product types. 15 April 2011 - AF/0411/016
		 */
		String corpCardHolderPosition = "";
		String corpCardHolderTelephone = "";
		if (coporateDataSecondRow.isVisible()) {
			// Getting card holder's position (card holder title column in DB)
			corpCardHolderPosition = positionField.getValue();
			corpCardHolderPosition = corpCardHolderPosition.trim();

			// Getting card holder's telephone
			corpCardHolderTelephone = telephoneField.getValue();
			corpCardHolderTelephone = corpCardHolderTelephone.trim();

		}

		String corpcardHolderMobile = "";
		String corpCardHolderEmail = "";
		if (coporateDataThirdRow.isVisible()) {
			// Getting card holder's email
			corpCardHolderEmail = emailField.getValue();
			corpCardHolderEmail = corpCardHolderEmail.trim();

			// Getting card holder's email
			corpcardHolderMobile = mobileField.getValue();
			corpcardHolderMobile = corpcardHolderMobile.trim();

		}
		String employeeId = "";
		if (coporateDataForthRow.isVisible()) {

			// Getting employee Id
			if(employeeIdField != null)
			{
				employeeId = employeeIdField.getValue();
				employeeId = employeeId.trim();
			}
		}

		// Getting card holder's NAME ON CARD
		String cardHoldernNameOnCard = "";
		if (nameOnCardRow.isVisible()) {
			
			if (cardDetailsNameOnCardField.getSelectedItem() != null) {
				cardHoldernNameOnCard = ComponentUtil.getSelectedItem(cardDetailsNameOnCardField);
			} else if (!Strings.isNullOrEmpty(cardDetailsNameOnCardField.getText())) {
				// To retrieve the type-written string instead of from the selection
				cardHoldernNameOnCard = (String) cardDetailsNameOnCardField.getValue();
			}

			cardHoldernNameOnCard = cardHoldernNameOnCard.trim().toUpperCase();
			
			if (Strings.isNullOrEmpty(cardHoldernNameOnCard)) {
				throw new WrongValueException(("Name On Card cannot be blank"));
			}
		}
		
		//check mobile no
		if(((Label) this.getFellow("mobileLabel")) != null)
		{
			if((productType.getVirtualProduct().equalsIgnoreCase("Y"))
					&& (((Label) this.getFellow("mobileLabel")).getSclass().equalsIgnoreCase("fieldLabel required")))
			{
				if (Strings.isNullOrEmpty(mobileField.getValue())) {
					throw new WrongValueException(("Mobile cannot be blank"));
				}
			}
		}
		
		BigDecimal fixedValueNumber = null;
		if (fixedValueRow.isVisible()) {
			fixedValueNumber = fixedValueField.getValue();
			if (fixedValueNumber == null || fixedValueNumber.doubleValue() < 0) {
				throw new WrongValueException("Fixed value format is wrong it should be greater than 0.");
			}
		}

		BigDecimal creditLimitNumber = null;
		if (creditLimitRow.isVisible()) {

			creditLimitNumber = creditLimitField.getValue();

			if (creditLimitNumber == null || creditLimitNumber.doubleValue() < 0) {
				throw new WrongValueException("Credit Limit format is wrong it should be greater than 0. ");
			}

			if (fixedValueNumber != null) {
				logger.info("fixedValueNumber : " + fixedValueNumber);
				logger.info("creditLimitNumber : " + creditLimitNumber);
				logger.info("compare value" + creditLimitNumber.compareTo(fixedValueNumber));
				if (creditLimitNumber.compareTo(fixedValueNumber) < 0) {
					throw new WrongValueException("Fixed Value should not be greater than credit limit");
				}

			}

			if (creditLimitNumber.compareTo(selectedAccount.getCreditLimit()) > 0) {
				throw new WrongValueException("Credit Limit is greater than the parent credit limit.");
			}

		}

		String smsExpiryFlag = ComponentUtil.getSelectedItem(smsExpiryFlagListBox);
		String smsTopUpFlag = ComponentUtil.getSelectedItem(smsTopUpFlagListBox);
		String waiveSubscribeFeeFlag = NonConfigurableConstants.getBooleanFlag(waiveSubscFeeChkBox.isChecked());
		String waiveIssuanceFeeFlag = NonConfigurableConstants.getBooleanFlag(waiveIssuanceFeeChkBox.isChecked());

		Integer offlineCount = null;
		if (offlineCountRow.isVisible()) {
			offlineCount = offlineCountField.getValue();
		}

		BigDecimal offlineAmountAccumulative = null;
		if (offlineAmountAccumulativeRow.isVisible()) {
			offlineAmountAccumulative = offlineAmountAccumulativeField.getValue();
		}

		BigDecimal offlineAmountPerTxn = null;
		if (offlineAmountPerTxnRow.isVisible()) {
			offlineAmountPerTxn = offlineAmountPerTxnField.getValue();
		}

		String embossNameOnCardFlag = NonConfigurableConstants.BOOLEAN_YES;
		if (embossNameRow.isVisible()) {
			embossNameOnCardFlag = NonConfigurableConstants.getBooleanFlag(embossNameOnCardChkBox.isChecked());
		}

		Date expiryDate = null;
		Timestamp expiryTime = null;
		if (expiryDateRow.isVisible()) {
			expiryDate = expiryDateField.getValue();

			if (expiryDate == null) {
				throw new WrongValueException("Expiry Date shouldn't be blank.");
			}

			// put the EXPIRY date into calendar to do validation and modification
			Calendar expiryDateCal = Calendar.getInstance();
			expiryDateCal.setTime(expiryDate);
			Calendar currentCal = Calendar.getInstance();

			if (expiryDateCal.get(Calendar.YEAR) < currentCal.get(Calendar.YEAR) || (expiryDateCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR) && expiryDateCal.get(Calendar.MONTH) < currentCal.get(Calendar.MONTH))) {
				throw new WrongValueException("Expiry Date shouldn't be earlier than current month.");
			}

			// set the EXPIRY date to maximum date of month
			expiryDateCal.set(Calendar.DATE, expiryDateCal.getActualMaximum(Calendar.DATE));
			expiryDate = expiryDateCal.getTime();

			logger.info("Expiry Date: " + expiryDate);

		} else if (expiryDateTimeRow.isVisible()) {
			Date expiryUptoDay = expiryDayMonthYearDBField.getValue();
			expiryDate = DateUtil.convertUtilDateToSqlDate(expiryUptoDay);
			new RequiredEqualOrLaterThanCurrentDateConstraint().validate(expiryDayMonthYearDBField, expiryUptoDay);

			Integer hour = ComponentUtil.getSelectedItem(expiryHourListBox);
			Integer min = ComponentUtil.getSelectedItem(expiryMinListBox);
			Calendar expiryCal = Calendar.getInstance();
			expiryCal.setTime(expiryUptoDay);
			expiryCal.set(Calendar.HOUR_OF_DAY, hour);
			expiryCal.set(Calendar.MINUTE, min);
			expiryCal.set(Calendar.SECOND, 0);
			expiryCal.set(Calendar.MILLISECOND, 0);

			Calendar currentCal = Calendar.getInstance();

			if (expiryCal.compareTo(currentCal) <= 0) {
				throw new WrongValueException("Expiry Date and Time cannot be earlier than current date and time.");
			}

			logger.info("Expiry Date Time: " + expiryCal);
			expiryTime = new Timestamp(expiryCal.getTimeInMillis());
		}


		Date balExpDate= null;
		if (balanceExpiryDateRow.isVisible()) {
			balExpDate = balanceExpiryDateField.getValue();
			
			if(balExpDate!=null){
				balExpDate = DateUtil.convertDateTo2359Hours(balExpDate);
				balExpDate = DateUtil.getLastUtilDateOfMonth(balExpDate);
			}
			
		} else if(balanceExpiryDateTimeRow.isVisible()){
			
			balExpDate = balanceExpiryDateTimeField.getValue();
			Integer hour = ComponentUtil.getSelectedItem(balanceExpiryHourListBox);
			Integer min = ComponentUtil.getSelectedItem(balanceExpiryMinListBox);
			
			if(balExpDate!=null && hour!=null && min!=null){
				
				Calendar balExpiryCal = Calendar.getInstance();
				balExpiryCal.setTime(balExpDate);
				balExpiryCal.set(Calendar.HOUR_OF_DAY, hour);
				balExpiryCal.set(Calendar.MINUTE, min);
				balExpiryCal.set(Calendar.SECOND, 59);
				balExpiryCal.set(Calendar.MILLISECOND, 999);
				balExpDate = balExpiryCal.getTime();
			
			} else if(balExpDate==null && hour==null && min==null){
				//do nothing
			} else {
				throw new WrongValueException("Both balance expiry date and balance expiry time must have value if either one is not empty.");
			}
		}
			
		if(balExpDate!=null){
			ProductUtil.validateBalanceExpiryDateNoPast(balExpDate);
			form.setBalanceExpiryDate(balExpDate);
		}
		

		// bind value with form
		form.setAmtbAccount(selectedAccount);
		form.setPmtbProductType(productType);

		form.setCreditLimit(creditLimitNumber);
		form.setFixedValue(fixedValueNumber);

		form.setExpiryDate(DateUtil.convertUtilDateToSqlDate(expiryDate));
		form.setExpiryTime(expiryTime);

		form.setNameOnProduct(cardHoldernNameOnCard);
		form.setIsIndividualCard(NonConfigurableConstants.getBooleanFlag(coporateCheckBox.isChecked()));
		form.setCardHolderMobile(corpcardHolderMobile);
		form.setCardHolderEmail(corpCardHolderEmail);
		form.setCardHolderTel(corpCardHolderTelephone);
		form.setCardHolderName(corpCardHolderName);
		form.setCardHolderTitle(corpCardHolderPosition);
		form.setCardHolderSalutation(corpCardHolderSalutation);

		form.setSmsExpiryFlag(smsExpiryFlag);
		form.setSmsTopupFlag(smsTopUpFlag);

		form.setOfflineCount(offlineCount);
		form.setOfflineAmount(offlineAmountAccumulative);
		form.setOfflineTxnAmount(offlineAmountPerTxn);

		form.setEmbossNameOnCard(embossNameOnCardFlag);
		form.setWaiveSubscFeeFlag(waiveSubscribeFeeFlag);
		form.setWaiveIssuanceFeeFlag(waiveIssuanceFeeFlag);
		form.setEmployeeId(employeeId);

		if(cardValueRow.isVisible()){
			
			form.setCardValue(cardValueField.getValue());
			
			String cashPlusStr = cashplusFieldLabel.getValue();
			BigDecimal cashPlus = null;
			if (!Strings.isNullOrEmpty(cashPlusStr)) {
				cashPlus = new BigDecimal(cashPlusStr);
			}

			form.setCashplus(cashPlus);
		}

		IssueType issueType = null;
		Integer noOfCards = null;
		Integer cardNoStart = null;
		Integer cardNoEnd = null;
		
		if(!issueByCountRow.isVisible() && !issueByRangeRow.isVisible()){
			noOfCards = 1;
			issueType = IssueType.COUNT;
		} else if (issueByCountRow.isVisible()) {

			noOfCards = issueByCountField.getValue();
			if (noOfCards == null) {
				throw new WrongValueException("Issue by Count should not be blank.");
			}
			if (noOfCards <= 0) {
				throw new WrongValueException("Issue by Count should be greater than 0.");
			}
			issueType = IssueType.COUNT;

		} else if (issueByRangeRow.isVisible()) {

			cardNoStart = cardNoStartField.getValue();
			cardNoEnd = cardNoEndField.getValue();
			int totalCard = cardNoEnd - cardNoStart + 1;

			if (cardNoStart.compareTo(cardNoEnd) > 0) {
				throw new WrongValueException("Card No End should be greater than Card No Start");
			}

			String checkStartCard = CardNoGenerator.generateCardNo(cardNoStart, productType);
			String checkEndCard = CardNoGenerator.generateCardNo(cardNoEnd, productType);
			if (NonConfigurableConstants.getBoolean(productType.getLuhnCheck())) {
				checkStartCard = checkStartCard.substring(0, checkStartCard.length()-1) + "0";
				checkEndCard = checkEndCard.substring(0, checkEndCard.length()-1) + "0";
			}

			int countCreatedCards = this.businessHelper.getProductBusiness().getCountIssuedCards(checkStartCard, checkEndCard, productType);

			if (countCreatedCards == totalCard) {
				throw new WrongValueException("Card No are used up for this range.");

			} else if (countCreatedCards > 0) {
				throw new WrongValueException("There Are " + countCreatedCards + " cards already issued within this range.");
			}

			issueType = IssueType.RANGE;
		}

		form.setIssueType(issueType);
		form.setNoOfCards(noOfCards);
		form.setCardNoStart(cardNoStart);
		form.setCardNoEnd(cardNoEnd);

		return form;

	}

	protected void populateAccount(AmtbAccount acct) {

		logger.debug("populate acct: " + acct.getAccountNo());
		
		AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
		accountNoIntBox.setText(topAcct.getCustNo());

		Events.sendEvent(new Event(Events.ON_CHANGE, accountNoIntBox, topAcct.getCustNo()));
		String category = acct.getAccountCategory();

		if (divDeptWindowType.equals(TYPE_CENTRAL)) {
			if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				ComponentUtil.setSelectedItem(divisionComboBox, acct);
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				ComponentUtil.setSelectedItem(departmentComboBox, acct);
			}
		} else if (divDeptWindowType.equals(TYPE_DEPEND)){

			if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				ComponentUtil.setSelectedItem(divisionComboBox, acct);
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {

				AmtbAccount divAccount = acct.getAmtbAccount();
				ComponentUtil.setSelectedItem(divisionComboBox, divAccount);
				ComponentUtil.setSelectedItem(departmentComboBox, acct);
			}
		}
	}

	protected void populateIssueProductForm(IssueProductForm form) {

		AmtbAccount acct = form.getAmtbAccount();

		populateAccount(acct);

		logger.debug("start populating");

		cardHolderNameField.setRawValue(form.getCardHolderName());
		positionField.setRawValue(form.getCardHolderTitle());
		telephoneField.setRawValue(form.getCardHolderTel());
		emailField.setRawValue(form.getCardHolderEmail());
		
		if(employeeIdField != null && form.getEmployeeId() != null)
			employeeIdField.setRawValue(form.getEmployeeId());
		
		mobileField.setRawValue(form.getCardHolderMobile());

		cardDetailsNameOnCardField.setRawValue(form.getNameOnProduct());
		fixedValueField.setRawValue(form.getFixedValue());

		ComponentUtil.setSelectedItem(salutationListBox, form.getCardHolderSalutation());
		ComponentUtil.setSelectedItem(smsExpiryFlagListBox, form.getSmsExpiryFlag());
		ComponentUtil.setSelectedItem(smsTopUpFlagListBox, form.getSmsTopupFlag());

		waiveSubscFeeChkBox.setChecked(NonConfigurableConstants.getBoolean(form.getWaiveSubscFeeFlag()));
		waiveIssuanceFeeChkBox.setChecked(NonConfigurableConstants.getBoolean(form.getWaiveIssuanceFeeFlag()));
		offlineCountField.setRawValue(form.getOfflineCount());
		offlineAmountAccumulativeField.setRawValue(form.getOfflineAmount());
		offlineAmountPerTxnField.setRawValue(form.getOfflineTxnAmount());

		embossNameOnCardChkBox.setChecked(NonConfigurableConstants.getBoolean(form.getEmbossNameOnCard()));

		expiryDateField.setRawValue(form.getExpiryDate());

		Timestamp expiryTime = form.getExpiryTime();
		if (expiryTime != null) {

			expiryDayMonthYearDBField.setRawValue(expiryTime);

			Calendar cal = Calendar.getInstance();
			cal.setTime(expiryTime);

			Integer hour = cal.get(Calendar.HOUR_OF_DAY);
			Integer minute = cal.get(Calendar.MINUTE);

			ComponentUtil.setSelectedItem(expiryHourListBox, hour);
			ComponentUtil.setSelectedItem(expiryMinListBox, minute);

		}

		coporateCheckBox.setChecked(NonConfigurableConstants.getBoolean(form.getIsIndividualCard()));

		ComponentUtil.setSelectedItem(productTypeListBox, form.getPmtbProductType());

		cardValueField.setRawValue(form.getCardValue());

		
		String validityPeriod = form.getPmtbProductType().getValidityPeriod();
		Date balanceExpiryDate = form.getBalanceExpiryDate();
		
		if(balanceExpiryDate!=null){
			if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)) {
				
				balanceExpiryDateField.setRawValue(balanceExpiryDate);
				balanceExpiryDateTimeField.setRawValue(null);
				
			} else if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)) {
			
				balanceExpiryDateField.setRawValue(null);
				balanceExpiryDateTimeField.setRawValue(balanceExpiryDate);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(balanceExpiryDate);
				
				Integer hour = cal.get(Calendar.HOUR_OF_DAY);
				Integer minute = cal.get(Calendar.MINUTE);
	
				ComponentUtil.setSelectedItem(balanceExpiryHourListBox, hour);
				ComponentUtil.setSelectedItem(balanceExpiryMinListBox, minute);
				
			} else {
				throw new WrongValueException("Unsupported validity period: " + validityPeriod);
			}
		}
		
		logger.debug("end populating");
	}

	public void searchCardHolderName(String name) throws InterruptedException {
		logger.info("");

		// Retrieve entered input
		if (name == null || "".equals(name) || (name.length() < 3))
			return;

		if (cardHolderNameField.getSelectedItem() != null) {
			String selectedName = (String) cardHolderNameField.getSelectedItem().getValue();
			if (name.equalsIgnoreCase(selectedName))
				return;
		}

		cardHolderNameField.getChildren().clear();

		try {

			// Name is not required
			List<String> cardHolderNames = this.businessHelper.getProductBusiness().getProducts(String.valueOf(accountNoIntBox.getValue()), name);
			if (!cardHolderNames.isEmpty()) {
				for (String cardHolderName : cardHolderNames) {
					Comboitem item = new Comboitem(cardHolderName.toUpperCase());
					item.setValue(cardHolderName.toUpperCase());
					cardHolderNameField.appendChild(item);
				}
				if (cardHolderNames.size() == 1) {
					cardHolderNameField.setSelectedIndex(0);
				} else
					cardHolderNameField.open();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	
	public void searchNameOnCard(String name) throws InterruptedException {
		logger.info("searchNameOnCard");

		// Retrieve entered input
		if (name == null || "".equals(name) || (name.length() < 3))
			return;

		if (cardDetailsNameOnCardField.getSelectedItem() != null) {
			String selectedName = (String) cardDetailsNameOnCardField.getSelectedItem().getValue();
			if (name.equalsIgnoreCase(selectedName))
				return;
		}

		cardDetailsNameOnCardField.getChildren().clear();

		try {
			String custNo = String.valueOf(accountNoIntBox.getValue());
			// Name is not required
			List<String> nameOnCards = this.businessHelper.getProductBusiness().getDistinctNameOnCards(custNo, name);
			if (!nameOnCards.isEmpty()) {
				for (String nameOnCard : nameOnCards) {
					Comboitem item = new Comboitem(nameOnCard.toUpperCase());
					item.setValue(nameOnCard.toUpperCase());
					cardDetailsNameOnCardField.appendChild(item);
				}
				if (nameOnCards.size() == 1) {
					cardDetailsNameOnCardField.setSelectedIndex(0);
				} else
					cardDetailsNameOnCardField.open();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}

	protected void populateAcctDetails() {

		boolean showAcctDetails = false;
		if (acctDetailsRow.isVisible() && selectedAccount != null) {

			showAcctDetails = true;
			String category = selectedAccount.getAccountCategory();

			String nameOnCardLbl = "";

			if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)) {
				nameOnCardLbl = "Corporate";
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				nameOnCardLbl = "Department";
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
				nameOnCardLbl = "Division";
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				nameOnCardLbl = "Applicant";
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				nameOnCardLbl = "Sub Applicant";
			}

			nameOnCardLbl = "Name on Card (" + nameOnCardLbl + ")";

			nameOnCardLabel.setValue(nameOnCardLbl);
			nameOnCardFieldLabel.setValue(selectedAccount.getNameOnCard());

			BigDecimal creditLimit = selectedAccount.getCreditLimit();

			if (creditLimit != null) {
				parentCreditLimitLabel.setValue(StringUtil.bigDecimalToString(creditLimit, StringUtil.GLOBAL_DECIMAL_FORMAT));
			} else {
				parentCreditLimitLabel.setValue("-");
			}

		}

		acctDetailsRow.setVisible(showAcctDetails);

	}

	public abstract List<PmtbProductType> getIssuableProductTypes(AmtbAccount account);

	public String emptyIssuableProductTypeErrorMsg() {
		return "This Account doesn't subscribe any product type.";
	}

	protected void populateProductDetails() {

		logger.debug("populateProductDetails");
		boolean showProductDetails = false;
		try {

			if (selectedAccount != null) {

				logger.debug("populateProductDetails - selected account is not null");
				
				// get issue able product type
				List<PmtbProductType> issuableProductTypes = getIssuableProductTypes(selectedAccount);

				if (issuableProductTypes.isEmpty()) {
					throw new WrongValueException(emptyIssuableProductTypeErrorMsg());
				}

				showProductDetails = true;
				// build product type list box
				Map<PmtbProductType, String> productTypeMap = Maps.newHashMap();
				for (PmtbProductType type : issuableProductTypes) {
					productTypeMap.put(type, type.getName());
				}
				productTypeListBox.getItems().clear();
				ComponentUtil.buildListbox(productTypeListBox, productTypeMap, false);

				// set corporate card as default if exists
				@SuppressWarnings("rawtypes")
				List productTypeList = productTypeListBox.getItems();
				int productTypeListSize = productTypeList.size();
				for (int i = 0; i < productTypeListSize; i++) {
					Listitem item = (Listitem) productTypeList.get(i);
					PmtbProductType type = (PmtbProductType) item.getValue();

					if (NonConfigurableConstants.CORPORATE_CARD_ID.equals(type.getProductTypeId())) {
						productTypeListBox.setSelectedIndex(i);
						break;
					}
				}

				coporateCheckBox.setChecked(false);
				waiveSubscFeeChkBox.setChecked(true);

				onCheckCorporate();
				onSelectProductType();
			}

		} catch (WrongValueException e) {
			throw e;
		} finally {
			commonProductDetailsGridTitle.setVisible(showProductDetails);
			commonProductDetailsGrid.setVisible(showProductDetails);

			afterPopulatedProductDetails();
		}

	}

	public void afterPopulatedProductDetails() {

	}

	public void onSelectProductType() {

		logger.debug("onSelectProductType");

		String accountCategory = selectedAccount.getAccountCategory();

		PmtbProductType selectedProductType = ComponentUtil.getSelectedItem(productTypeListBox);

		boolean isCorporateCard = selectedProductType.getProductTypeId().equals(NonConfigurableConstants.CORPORATE_CARD_ID);
		coporateDataFirstRow.setVisible(isCorporateCard);

		boolean isNameOnProduct = NonConfigurableConstants.getBoolean(selectedProductType.getNameOnProduct());
		nameOnCardRow.setVisible(isNameOnProduct);

		boolean isBinRangeNA = selectedProductType.getBinRange().equals(NonConfigurableConstants.BOOLEAN_NA);
		binAndSubBinRow.setVisible(!isBinRangeNA);
		if (binAndSubBinRow.isVisible()) {
			binLabel.setValue(selectedProductType.getBinRange());
			subBinLabel.setValue(selectedProductType.getSubBinRange());
		}

		boolean isCreditLimit = NonConfigurableConstants.getBoolean(selectedProductType.getCreditLimit());
		boolean isPrepaid = NonConfigurableConstants.getBoolean(selectedProductType.getPrepaid());

		logger.debug("isCreditLimit:" + isCreditLimit);
		logger.debug("isPrepaid:" + isPrepaid);

		creditLimitRow.setVisible(isCreditLimit);
		cardValueRow.setVisible(isPrepaid);

		if (isPrepaid) {
			creditLimitRow.setVisible(false);
		}

		boolean isFixedValue = NonConfigurableConstants.getBoolean(selectedProductType.getFixedValue());
		fixedValueRow.setVisible(isFixedValue);

		// tired of think too much, to develop in easier way and avoid from possibly issues happen, PREPAID does not support issue by count and range
		boolean isIssueByCount = !isPrepaid && selectedProductType.getIssueType().equalsIgnoreCase(NonConfigurableConstants.ISSUE_TYPE_COUNT);
		issueByCountRow.setVisible(isIssueByCount);

		boolean isIssueByRange = !isPrepaid && selectedProductType.getIssueType().equalsIgnoreCase(NonConfigurableConstants.ISSUE_TYPE_RANGE);
		issueByRangeRow.setVisible(isIssueByRange);

		if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(accountCategory) || NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(accountCategory) || NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(accountCategory)) {
			embossNameOnCardChkBox.setChecked(false);
		} else {
			embossNameOnCardChkBox.setChecked(true);
		}

		boolean isContactLess = NonConfigurableConstants.getBoolean(selectedProductType.getContactless());
		offlineCountRow.setVisible(isContactLess);
		offlineAmountAccumulativeRow.setVisible(isContactLess);
		offlineAmountPerTxnRow.setVisible(isContactLess);
		embossNameRow.setVisible(isContactLess);

		if (isPrepaid) {
			expiryDateTitleLabel.setValue("Card Expiry (MM/YY)");
			expiryDateTimeTitleLabel.setValue("Card Expiry (DD/MM/YYYY)");
		} else {
			expiryDateTitleLabel.setValue("Expiry Date (MM/YY)");
			expiryDateTimeTitleLabel.setValue("Expiry Date (DD/MM/YYYY)");
		}
		
		
		String validityPeriod = selectedProductType.getValidityPeriod();
		int defaultValidPeriod = selectedProductType.getDefaultValidPeriod();

		if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_NO.equals(validityPeriod)) {
			expiryDateRow.setVisible(false);
			expiryDateTimeRow.setVisible(false);
		} else if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)) {
			expiryDateRow.setVisible(true);
			expiryDateTimeRow.setVisible(false);

		} else if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)) {
			expiryDateRow.setVisible(false);
			expiryDateTimeRow.setVisible(true);
		}
		
		
		
		if(isPrepaid){
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, selectedProductType.getDefaultBalanceExpMonths());
			Date defaultBalanceExpiryDate = cal.getTime(); 
			
			if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)) {
				balanceExpiryDateRow.setVisible(true);
				balanceExpiryDateTimeRow.setVisible(false);
				balanceExpiryDateField.setValue(defaultBalanceExpiryDate);
				balanceExpiryDateTimeField.setValue(null);

				
			} else if (NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)) {
				balanceExpiryDateRow.setVisible(false);
				balanceExpiryDateTimeRow.setVisible(true);
				balanceExpiryDateField.setValue(null);
				balanceExpiryDateTimeField.setValue(defaultBalanceExpiryDate);
				ComponentUtil.setSelectedItem(balanceExpiryHourListBox, new Integer(23));
				ComponentUtil.setSelectedItem(balanceExpiryMinListBox, new Integer(59));
				
			} else {
				throw new WrongValueException("Unsupported validity period: " + validityPeriod);
			}
		} else {
			balanceExpiryDateRow.setVisible(false);
			balanceExpiryDateTimeRow.setVisible(false);
			balanceExpiryDateField.setValue(null);
			balanceExpiryDateTimeField.setValue(null);
		
		}
		

		if (defaultValidPeriod >= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, defaultValidPeriod);

			if (expiryDateRow.isVisible()) {
				expiryDateField.setValue(calendar.getTime());
			} else if (expiryDateTimeRow.isVisible()) {
				expiryDayMonthYearDBField.setValue(calendar.getTime());
			}
		}

		// for PREPAID, hide and hard code waive subscription fee to to check
		if (isPrepaid) {
			waiveSubscFeeLabel.setVisible(false);
			waiveSubscFeeChkBox.setVisible(false);
			waiveSubscFeeChkBox.setChecked(true);
		}

		acctDetailsRow.setVisible(!isPrepaid);

	}

	public void onCheckCorporate() {

		boolean isCorporate = coporateCheckBox.isChecked();
		coporateDataSecondRow.setVisible(isCorporate);
		coporateDataThirdRow.setVisible(isCorporate);
		coporateDataForthRow.setVisible(isCorporate);

	}

	@Override
	public void onSelectAccountName() throws InterruptedException {
		super.onSelectAccountName();
		displayProcessing();
		populateDetails();

	}

	@Override
	public void onSelectDivision() throws InterruptedException {
		super.onSelectDivision();
		populateDetails();
	}

	@Override
	public void onSelectDepartment() throws InterruptedException {
		super.onSelectDepartment();
		populateDetails();
	};

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String accountName) {
		return this.businessHelper.getProductBusiness().getActiveAccountList(custNo, accountName);
	}

	public void resetProductDetails() {

		ComponentUtil.reset(binLabel, subBinLabel, cardHolderNameField, salutationListBox, positionField, telephoneField, mobileField, emailField, employeeIdField, cardDetailsNameOnCardField, coporateCheckBox, creditLimitField, expiryDateField, expiryDayMonthYearDBField, waiveSubscFeeChkBox, waiveIssuanceFeeChkBox,
				smsExpiryFlagListBox, smsTopUpFlagListBox, issueByCountField, cardNoStartField, cardNoEndField, fixedValueField, offlineCountField, offlineAmountAccumulativeField, offlineAmountPerTxnField, embossNameOnCardChkBox);
	}

	public void populateDetails() throws SuspendNotAllowedException, InterruptedException {
		populateAcctDetails();
		populateProductDetails();
	}

}
