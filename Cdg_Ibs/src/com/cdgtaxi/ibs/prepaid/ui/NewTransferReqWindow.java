package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.GstUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class NewTransferReqWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(NewTransferReqWindow.class);

	private Listbox fromCardField, toCardField;

	private AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private AccountLabelMacroComponent accountMC2;
	private Label cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2;

	private Label txferableValueLabel, txferableCashplusLabel;
	
	private Textbox remarksField;
	private Row transferValueCashPlusRow;
	private Decimalbox transferValueField, transferCashplusField;
	private Label transferFeeLabel;
	private Checkbox waiveTransferFeeField, transferAllField;
	private Label valueAfterTxfLabel, cashplusAfterTxfLabel;

	private PmtbProduct fromProduct;
	private PmtbProduct toProduct;
	private AmtbAccount acct;
	private BigDecimal txferableValue = null;
	private BigDecimal txferableCashplus = null;
	private BigDecimal transferValue = null;
	private BigDecimal transferCashplus = null;
	private BigDecimal transferFeeValue = BigDecimal.ZERO;
	
	public NewTransferReqWindow() throws InterruptedException {
		super();

	}

	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);

		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {

		Map<String, Object> map = Executions.getCurrent().getArg();

		Integer acctNo = (Integer) map.get("accountNo");
		acct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acctNo));

		List<BigDecimal> productNoLists = (List<BigDecimal>) map.get("productNoList");
		logger.debug("Product no list: " + Joiner.on(", ").join(productNoLists));

		List<PmtbProduct> productList = this.businessHelper.getPrepaidBusiness().getProductsForTransferReq(productNoLists);

		logger.debug("Product list size: " + productList.size());

		Map<PmtbProduct, String> cardNoMap = Maps.newLinkedHashMap();
		for (PmtbProduct p : productList) {
			cardNoMap.put(p, p.getCardNo());
		}
		ComponentUtil.buildListbox(fromCardField, cardNoMap, true);
		ComponentUtil.buildListbox(toCardField, cardNoMap, true);

		// default to check the waive transfer fee
		ComponentUtil.setChecked(waiveTransferFeeField, true);
		
		//default to check the transfer all flag
		ComponentUtil.setChecked(transferAllField, true);
		
	}

	@SuppressWarnings("unchecked")
	public void onSelectFromCard() {

		fromProduct = ComponentUtil.getSelectedItem(fromCardField);
		
		if(fromProduct!=null){
			
			try {
				
				if(toProduct!=null){
					if(toProduct.getCardNo().equals(fromProduct.getCardNo())){
						throw new WrongValueException("No transfer request within the same card is allowed!");
					}
				}
				
				displayCardDetails(fromProduct, accountMC, cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel);
				
				//initialize the transfer fee
				BigDecimal transferFee = fromProduct.getPmtbProductType().getTransferFee();
				transferFeeValue = transferFee;
				
				BigDecimal gstRate = new BigDecimal("0.00");
				BigDecimal gst = new BigDecimal("0.00");
				AmtbAccount acctx = this.businessHelper.getAccountBusiness().getFmtbArContCodeMaster(acct.getCustNo());
				FmtbEntityMaster entity = acctx.getFmtbArContCodeMaster().getFmtbEntityMaster();
				gstRate = this.businessHelper.getPrepaidBusiness().getLatestGST(entity.getEntityNo(), null, DateUtil.getCurrentTimestamp(), NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_TRANSFER_FEE);
				gst = GstUtil.forwardCalculateGst(transferFeeValue, gstRate);
				transferFeeValue = transferFeeValue.add(gst);
				
				transferFeeLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(transferFeeValue));
				
				TransferableAmtInfo transferInfo = this.businessHelper.getPrepaidBusiness().calculateTransferableValueAndCashPlus(fromProduct);
				txferableValue = transferInfo.getCardValue();
				txferableCashplus = transferInfo.getCashplus();
				
				txferableValueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(txferableValue));
				txferableCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(txferableCashplus));
			
			}
			catch(WrongValueException e){
				//revert back the select field to null
				fromCardField.setSelectedIndex(0);
				fromProduct = null;
				resetCardDetails( accountMC, cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel);
				ComponentUtil.reset(txferableValueLabel, txferableCashplusLabel);
				
				throw e;
			}
		
		} else{
			resetCardDetails(accountMC, cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel);
		}
		
		calculateValueAndCashbackAfterTxf();
		
		//auto select card 2 based on card 1 selection
		List<Listitem> toProductList = toCardField.getItems();
		
		int toProductListSize = toProductList.size();
		
		for(int i=0; i<toProductListSize; i++){
		
			Listitem listItem = toProductList.get(i);
			Object obj = listItem.getValue();
			if(obj!=null){
				PmtbProduct tProduct = (PmtbProduct) listItem.getValue();
				if(!tProduct.getProductNo().equals(fromProduct.getProductNo())){
					ComponentUtil.setSelectedIndex(toCardField, i);
					break;
				}
			}
		}
		
	}

	public void onSelectToCard() {

		toProduct = ComponentUtil.getSelectedItem(toCardField);
		if(toProduct!=null){
			
			try {
			
				if(fromProduct!=null){
					if(toProduct.getCardNo().equals(fromProduct.getCardNo())){
						throw new WrongValueException("No transfer request within the same card is allowed!");
					}
				}

				displayCardDetails(toProduct, accountMC2, cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2);
			} catch(WrongValueException e){
				//revert back the select field to null
				toCardField.setSelectedIndex(0);
				toProduct = null;
				resetCardDetails(accountMC2, cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2);
				throw e;
			}

		}
		else{
			resetCardDetails(accountMC2, cardNoLabel2, nameOnCardLabel2, valueLabel2, cashplusLabel2, balanceExpiryDateLabel2, expiryDateLabel2);
		}
		
		
		calculateValueAndCashbackAfterTxf();
	}
	
	
	private void resetCardDetails(AccountLabelMacroComponent accountMC, Label cardNoLabel, Label nameOnCardLabel, Label valueLabel, Label cashplusLabel, Label balanceExpiryDateLabel, Label expiryDateLabel) {

		accountMC.reset();
		
		ComponentUtil.reset(
			cardNoLabel, 
			nameOnCardLabel,
			valueLabel,
			cashplusLabel,
			balanceExpiryDateLabel,
			expiryDateLabel
		);		
		
	}
	
	
	
	
	private void displayCardDetails(PmtbProduct product,  AccountLabelMacroComponent accountMC, Label cardNoLabel, Label nameOnCardLabel, Label valueLabel, Label cashplusLabel, Label balanceExpiryDateLabel, Label expiryDateLabel) {

		AmtbAccount acct = product.getAmtbAccount();

		accountMC.populateDetails(acct);

		cardNoLabel.setValue(product.getCardNo());
		nameOnCardLabel.setValue(product.getNameOnProduct());
		
		valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(product.getCardValue()));
		cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(product.getCashplus()));
		
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(product.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));

	}
	


	public void submit() throws InterruptedException, ParseException {

		if(fromProduct==null || toProduct==null){
			throw new WrongValueException("Card 1 (From) and Card 2 (To) must be selected before proceed to submit.");
		}
		
		
		if (fromProduct.getCardNo().equals(toProduct.getCardNo())) {
			throw new WrongValueException("No transfer request within the same card is allowed!");
		}

		validateTransferValueAndCashPlus();
		
		if(transferValueCashPlusRow.isVisible()){
			if(transferValueField.getValue().doubleValue()==0 && transferCashplusField.getValue().doubleValue() == 0){
				throw new WrongValueException("Transfer value and cash plus cannot be 0.");
			}
		}
		
		String warningMsg = "";
		BigDecimal valueAfterTxf = StringUtil.stringToBigDecimal(valueAfterTxfLabel.getValue());
		if(valueAfterTxf.doubleValue() <= 0){
			//prompt warning message.
			warningMsg = "New Value After Txf <= $0! ";
		}
		
		
		//check whether card has been expired
		java.util.Date currentBalanceExpiryDate = fromProduct.getBalanceExpiryDate();
		Date currentDate = new Date();
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

		if (!ComponentUtil.confirmBox(warningMsg + "Do you confirm to submit the request?", "New Transfer Up Request")) {
			return;
		}
		
		PmtbTransferReq request = new PmtbTransferReq();
		request.setAmtbAccount(acct);
		request.setRequestRemarks(remarksField.getValue());

		request.setTransferFee(transferFeeValue);
		request.setWaiveTransferFeeFlag(NonConfigurableConstants.getBooleanFlag(waiveTransferFeeField.isChecked()));

		request.setFromPmtbProduct(fromProduct);
		request.setToPmtbProduct(toProduct);

		//snapshot from card value, cash plus, to card value, cash plus
		request.setFromCardValue(fromProduct.getCardValue());
		request.setFromCashplus(fromProduct.getCashplus());
		request.setToCardValue(toProduct.getCardValue());
		request.setToCashplus(toProduct.getCashplus());
		
		request.setTxferableCardValue(txferableValue);
		request.setTxferableCashplus(txferableCashplus);
		
		request.setTransferCardValue(transferValue);
		request.setTransferCashplus(transferCashplus);
		request.setTransferAllFlag(NonConfigurableConstants.getBooleanFlag(transferAllField.isChecked()));
		
		//we ignore amount transfer validation here and do it only at approval process.
		this.businessHelper.getPrepaidBusiness().createTransferBalanceRequest(request);

		Messagebox.show("Transfer request submitted successfully", "New Transfer Request", Messagebox.OK, Messagebox.INFORMATION);

		CommonWindow window = this.back();
		if (window instanceof NewTransferReqSearchWindow) {
			((NewTransferReqSearchWindow) window).reset();
		}

	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public void onCheckWaiveTransferFee() {
		
		calculateValueAndCashbackAfterTxf();
	}


	public void onCheckTransferAll() {
		
		boolean isTransferAll = transferAllField.isChecked();
		
		transferValueCashPlusRow.setVisible(!isTransferAll);
		
		calculateValueAndCashbackAfterTxf();
	}
	
	private void calculateValueAndCashbackAfterTxf() {

		try {
		
			if(fromProduct!=null && toProduct!=null){
			
				//initialize the transfer fee
				BigDecimal transferFee = fromProduct.getPmtbProductType().getTransferFee();
				transferFeeValue = transferFee;

				BigDecimal gstRate = new BigDecimal("0.00");
				BigDecimal gst = new BigDecimal("0.00");
				AmtbAccount acctx = this.businessHelper.getAccountBusiness().getFmtbArContCodeMaster(acct.getCustNo());
				FmtbEntityMaster entity = acctx.getFmtbArContCodeMaster().getFmtbEntityMaster();
				gstRate = this.businessHelper.getPrepaidBusiness().getLatestGST(entity.getEntityNo(), null, DateUtil.getCurrentTimestamp(), NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_TRANSFER_FEE);
				gst = GstUtil.forwardCalculateGst(transferFeeValue, gstRate);
				transferFeeValue = transferFeeValue.add(gst);
				
				transferFeeLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(transferFeeValue));
				validateTransferValueAndCashPlus();
				if(transferAllField.isChecked()){
					transferValue = txferableValue;
					transferCashplus = txferableCashplus;
				} else{
					transferValue = transferValueField.getValue();
					transferCashplus = transferCashplusField.getValue();
				}
				
				// calculate the value after transfer
				String toValueStr = (!Strings.isNullOrEmpty(valueLabel2.getValue()))?valueLabel2.getValue():"0";
				String toCashplusStr = (!Strings.isNullOrEmpty(cashplusLabel2.getValue()))?cashplusLabel2.getValue():"0";

				BigDecimal toBalance =  StringUtil.stringToBigDecimal(toValueStr);
				BigDecimal toCashplus = StringUtil.stringToBigDecimal(toCashplusStr);
				BigDecimal valueAfterTxf = toBalance.add(transferValue);
				BigDecimal cashbackAfterTxf = toCashplus.add(transferCashplus);
				
				if (!waiveTransferFeeField.isChecked()) {
					valueAfterTxf = valueAfterTxf.subtract(transferFeeValue);
				}
				valueAfterTxfLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(valueAfterTxf));
				cashplusAfterTxfLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(cashbackAfterTxf));
			
			}
		}catch(Exception e){
			
			if(e instanceof WrongValueException){
				throw (WrongValueException) e;
			} else {
				LoggerUtil.printStackTrace(logger, e);
				throw new WrongValueException("System error. Please contact System Administartor.");
			}
		}
	}
	
	public void onChangeCardValueOrCashplus(){
		
		calculateValueAndCashbackAfterTxf();
	}
	
	
	public void validateTransferValueAndCashPlus(){
		
		if(fromProduct!=null && toProduct!=null){
		
			if(transferValueCashPlusRow.isVisible()){
				//check the transfer value cannot be more than the transferable value
				BigDecimal transferValue = transferValueField.getValue();
				BigDecimal transferCashPlus = transferCashplusField.getValue();
				
				if(transferValue==null){
					throw new WrongValueException("Transfer value cannot be null");
				}
				
				if(transferCashPlus==null){
					throw new WrongValueException("Transfer cashplus cannot be null");
				}
				
				if(transferValue.doubleValue() > txferableValue.doubleValue()){
					transferValueField.setValue(null);
					transferValueField.focus();
					
					throw new WrongValueException("Transfer value cannot more than transferable value.");
				}
				
				if(transferCashPlus.doubleValue() > txferableCashplus.doubleValue()){
					transferCashplusField.setValue(null);
					transferCashplusField.focus();
					
					throw new WrongValueException("Transfer cashplus cannot more than transferable cashplus.");
				}
			}
		
		}
	}
	
	
}
