package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.api.Intbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.ProductUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class NewExtendBalanceExpiryDateReqWindow extends CommonWindow implements AfterCompose {

	protected AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private Listbox newBalanceExpiryDateDurationTypeField;
	private Intbox newBalanceExpiryDateDurationLengthField;
	private PmtbProduct selectedProduct;
	
	protected Listbox expiryHourListBox, expiryMinListBox;
	protected Datebox newBalanceExpiryDateField, newBalanceExpiryDateTimeField;
	protected Row balanceExpiryDateRow, balanceExpiryDateTimeRow, durationLenRow;
	
	private static final Logger logger = Logger.getLogger(NewExtendBalanceExpiryDateReqWindow.class);

	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);
		init();
	}
	
	
	@SuppressWarnings("unchecked")
	public NewExtendBalanceExpiryDateReqWindow() throws InterruptedException {
		super();
		Map<String, Object> map = Executions.getCurrent().getArg();
		BigDecimal productNo = (BigDecimal) map.get("productNo");

		selectedProduct = this.businessHelper.getPrepaidBusiness().getPrepaidProduct(productNo);
		
	}
	
	
	public void init() {

		ComponentUtil.buildListbox(newBalanceExpiryDateDurationTypeField, NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE, false);
		ComponentUtil.setSelectedItem(newBalanceExpiryDateDurationTypeField, NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION);
		
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
		
		onSelectNewBalanceExpiryDateDurationTypeField();

		AmtbAccount acct = selectedProduct.getAmtbAccount();
		acct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acct.getAccountNo()));

		accountMC.populateDetails(acct);

		cardNoLabel.setValue(selectedProduct.getCardNo());
		nameOnCardLabel.setValue(selectedProduct.getNameOnProduct());
		valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCardValue()));
		cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCashplus()));
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));

	}

	public void submit() throws InterruptedException {

		java.util.Date currentDate = new java.util.Date();
		//just in case, balance EXPIRY date for PREPAID card should not be null
		java.util.Date currentBalanceExpiryDate = selectedProduct.getBalanceExpiryDate();
		String type = ComponentUtil.getSelectedItem(newBalanceExpiryDateDurationTypeField);
		
		logger.debug("current balance expiry date: " + currentBalanceExpiryDate);
		if(currentBalanceExpiryDate==null){
			if(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION.equals(type)){
				throw new WrongValueException("Current balance expiry date is null. Not extend balance expiry date by Duration is allowed.");
			}
		}
		
		//check whether card has been expired
		if(currentBalanceExpiryDate!=null){
			if(currentDate.after(currentBalanceExpiryDate)){
				
				Calendar expiryWithGracePeriod = Calendar.getInstance();
				expiryWithGracePeriod.setTime(currentBalanceExpiryDate);
				MstbMasterTable gracePeriodMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.BALANCE_EXPIRY_GRACE_PERIOD_MASTER_TYPE, 
						ConfigurableConstants.BALANCE_EXPIRY_GRACE_PERIOD_GP);
				
				Integer gracePeriod = new Integer(gracePeriodMaster.getMasterValue());
				expiryWithGracePeriod.add(Calendar.MONTH, gracePeriod);
				
				//check whether card has crossed grace period
				if(currentDate.after(expiryWithGracePeriod.getTime())){
					throw new WrongValueException("Card has been expired and crossed the grace period.");
				}
			}
		}
		

		PmtbExtBalExpDateReq request = new PmtbExtBalExpDateReq();
		request.setPmtbProduct(selectedProduct);
		request.setAmtbAccount(selectedProduct.getAmtbAccount());
		
		request.setOldBalExpDate(selectedProduct.getBalanceExpiryDate());
		request.setNewBalExpDateDurType(type);

		Date newBalExpDate = null;
		
		if(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DATE.equals(type)){
	
			if (balanceExpiryDateRow.isVisible()) {
				newBalExpDate = newBalanceExpiryDateField.getValue();
				newBalExpDate = DateUtil.convertDateTo2359Hours(newBalExpDate);
				newBalExpDate = DateUtil.getLastUtilDateOfMonth(newBalExpDate);
				
			} else if(balanceExpiryDateTimeRow.isVisible()){
				
				newBalExpDate = newBalanceExpiryDateTimeField.getValue();
				Integer hour = ComponentUtil.getSelectedItem(expiryHourListBox);
				Integer min = ComponentUtil.getSelectedItem(expiryMinListBox);
				Calendar expiryCal = Calendar.getInstance();
				expiryCal.setTime(newBalExpDate);
				expiryCal.set(Calendar.HOUR_OF_DAY, hour);
				expiryCal.set(Calendar.MINUTE, min);
				expiryCal.set(Calendar.SECOND, 59);
				expiryCal.set(Calendar.MILLISECOND, 999);
				newBalExpDate = expiryCal.getTime();

			}
		
			
		} else if(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION.equals(type)){
			request.setNewBalExpDateDurLen(newBalanceExpiryDateDurationLengthField.getValue());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(request.getOldBalExpDate());
			cal.add(Calendar.MONTH, request.getNewBalExpDateDurLen());
			newBalExpDate = DateUtil.getLastUtilDateOfMonth(cal.getTime());
		} else {
			logger.debug("Invalid type: " + type);
		}
		
		ProductUtil.validateBalanceExpiryDateNoPast(newBalExpDate);
		ProductUtil.validateNoEarlierThanCurrentBalanceExpiryDate(newBalExpDate, selectedProduct.getBalanceExpiryDate());

		request.setNewBalExpDate(newBalExpDate);

		if (!ComponentUtil.confirmBox("Do you confirm to submit the request?", "New Extend Balance Expiry Date Request")) {
			return;
		}

		this.businessHelper.getPrepaidBusiness().createExtendBalanceExpiryDateRequest(request);

		Messagebox.show("Extend balance expiry date request submitted successfully", "New Extend Balance Expiry Date Request", Messagebox.OK, Messagebox.INFORMATION);
		this.back();

	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void onSelectNewBalanceExpiryDateDurationTypeField() {

		String newBalanceExpiryDateDurationType = ComponentUtil.getSelectedItem(newBalanceExpiryDateDurationTypeField);

		if (newBalanceExpiryDateDurationType.equals(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION)) {
			durationLenRow.setVisible(true);
			balanceExpiryDateRow.setVisible(false);
			balanceExpiryDateTimeRow.setVisible(false);
		} else if (newBalanceExpiryDateDurationType.equals(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DATE)) {
			
			durationLenRow.setVisible(false);
			PmtbProductType productType = selectedProduct.getPmtbProductType();
			String validityPeriod = productType.getValidityPeriod();
			if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)){
				balanceExpiryDateRow.setVisible(true);
				balanceExpiryDateTimeRow.setVisible(false);
			} else if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)){
				balanceExpiryDateRow.setVisible(false);
				balanceExpiryDateTimeRow.setVisible(true);
			}
			
		} else {
			throw new WrongValueException("Not supported new balance expiry date duration type.");
		}
	}

}
