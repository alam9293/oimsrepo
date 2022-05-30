package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.forms.GstInfo;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.GstUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Combobox;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class NewAdjustmentReqWindow extends CommonWindow implements AfterCompose {

	protected AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, valueLabel, cashplusLabel, balanceExpiryDateLabel, expiryDateLabel;
	private Textbox remarksField;
	private Decimalbox adjustValueAmountField, adjustCashplusAmountField;
	private PmtbProduct selectedProduct;
	private AmtbAccount selectedProductAcct;
	
	private Combobox adjustValueTxnCodeField, adjustCashplusTxnCodeField;
	private Decimalbox adjustValueGstField, adjustCashplusGstField;
	
	private static final Logger logger = Logger.getLogger(NewAdjustmentReqWindow.class);


	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		init();

	}
	
	@SuppressWarnings("unchecked")
	public void init(){
		
		Map<String, Object> map = Executions.getCurrent().getArg();
		BigDecimal productNo = (BigDecimal) map.get("productNo");
		
		
		selectedProduct = this.businessHelper.getPrepaidBusiness().getPrepaidProduct(productNo);
		AmtbAccount acct = selectedProduct.getAmtbAccount();
		selectedProductAcct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acct.getAccountNo()));
		
		accountMC.populateDetails(acct);
		
		AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
		//the transaction code is populate based on the account's entity
		AmtbAccount topAcctWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(topAcct);
		Integer entityNo = topAcctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();
		List<FmtbTransactionCode> codes = businessHelper.getFinanceBusiness().getEffectiveManualTxnCodes(entityNo);
		
		Map<FmtbTransactionCode, String> codeMap = Maps.newLinkedHashMap();
		for(FmtbTransactionCode code: codes ){
			codeMap.put(code, code.getDescription());
		}
		
		ComponentUtil.buildCombobox(adjustValueTxnCodeField, codeMap, true);
		ComponentUtil.buildCombobox(adjustCashplusTxnCodeField, codeMap, true);
		
		cardNoLabel.setValue(selectedProduct.getCardNo());
		nameOnCardLabel.setValue(selectedProduct.getNameOnProduct());
		valueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCardValue()));
		cashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCashplus()));
		balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
		expiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
	}

	public void submit() throws InterruptedException {
		
		//check whether the downward adjustment is not enough to reduce the card’s value 
		BigDecimal adjustValue = adjustValueAmountField.getValue();
		BigDecimal adjustCashplus = adjustCashplusAmountField.getValue();
		
		FmtbTransactionCode adjustValueTxnCode = ComponentUtil.getSelectedItem(adjustValueTxnCodeField);
		FmtbTransactionCode adjustCashplusTxnCode = ComponentUtil.getSelectedItem(adjustCashplusTxnCodeField);
		
		
		if(adjustValue.doubleValue() != 0){
			
			if(adjustValueTxnCode==null){
				throw new WrongValueException("Adjust Value Transaction Code cannot be empty when Adjust Value is not zero.");
			}
		}
		
		if(adjustCashplus.doubleValue() != 0){
			
			if(adjustCashplusTxnCode==null){
				throw new WrongValueException("Adjust CashPlus Transaction Code cannot be empty when Adjust CashPlus is not zero.");
			}
		}
				
		PmtbAdjustmentReq request = new PmtbAdjustmentReq();
		request.setPmtbProduct(selectedProduct);
		request.setAmtbAccount(selectedProduct.getAmtbAccount());
		request.setAdjustValueAmount(adjustValue);
		request.setAdjustCashplusAmount(adjustCashplus);
		request.setRequestRemarks(remarksField.getValue());

		request.setAdjustValueTxnCode(adjustValueTxnCode);
		request.setAdjustCashplusTxnCode(adjustCashplusTxnCode);
		
		request.setOriValueAmount(selectedProduct.getCardValue());
		request.setOriCashplusAmount(selectedProduct.getCashplus());
		
		BigDecimal adjustValueGst = adjustValueGstField.getValue();
		BigDecimal adjustCashplusGst = adjustCashplusGstField.getValue();
		
		if(adjustValueGst==null){
			adjustValueGst = BigDecimal.ZERO;
		}
		if(adjustCashplusGst==null){
			adjustCashplusGst = BigDecimal.ZERO;
		}
		request.setAdjustValueGst(adjustValueGst);
		request.setAdjustCashplusGst(adjustCashplusGst);
		
		if(!ComponentUtil.confirmBox("Do you confirm to submit the request?", "New Adjustment Request")){
			return;
		}
		
		this.businessHelper.getPrepaidBusiness().createAdjustmentRequest(request);
		
		Messagebox.show("Adjustment request submitted successfully", "New Adjustment Request", Messagebox.OK, Messagebox.INFORMATION);
		this.back();

	}

	@Override
	public void refresh() throws InterruptedException {
		
		
	}
	
	
	
	public void calculateAdjustValueGst(){
		
		AmtbAccount topAccount = AccountUtil.getTopLevelAccount(selectedProductAcct);
		AmtbAccount acctWithEntity = this.businessHelper.getAccountBusiness().getAccountWithEntity(topAccount);
		FmtbEntityMaster entity = acctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster();
		
		FmtbTransactionCode adjustValueTxnCode = ComponentUtil.getSelectedItem(adjustValueTxnCodeField);
		BigDecimal adjustValue = adjustValueAmountField.getValue();
		
		if(adjustValueTxnCode!=null && adjustValue!=null){
		
			BigDecimal adjustValueGstRate = this.businessHelper.getFinanceBusiness().getLatestGST(entity.getEntityNo(), DateUtil.getCurrentTimestamp(), adjustValueTxnCode.getTxnCode());
			GstInfo adjustValueGstInfo = GstUtil.forwardCalculateGstInfo(adjustValue, adjustValueGstRate);
			
			BigDecimal  adjustValueGst = adjustValueGstInfo.getGst();
			adjustValueGstField.setValue(adjustValueGst);
			
			adjustValueGstField.setDisabled(adjustValueGst==null);
		
		}
		
		
	}
	
	public void calculateAdjustCashplusGst(){
		
		AmtbAccount topAccount = AccountUtil.getTopLevelAccount(selectedProductAcct);
		AmtbAccount acctWithEntity = this.businessHelper.getAccountBusiness().getAccountWithEntity(topAccount);
		FmtbEntityMaster entity = acctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster();
		
		FmtbTransactionCode adjustCashplusTxnCode = ComponentUtil.getSelectedItem(adjustCashplusTxnCodeField);
		BigDecimal adjustCashplus = adjustCashplusAmountField.getValue();
		
		if(adjustCashplusTxnCode!=null && adjustCashplus!=null){
		
			BigDecimal adjustCashplusGstRate = this.businessHelper.getFinanceBusiness().getLatestGST(entity.getEntityNo(), DateUtil.getCurrentTimestamp(), adjustCashplusTxnCode.getTxnCode());
			GstInfo adjustCashplusGstInfo = GstUtil.forwardCalculateGstInfo(adjustCashplus, adjustCashplusGstRate);
			
			BigDecimal  adjustCashplusGst = adjustCashplusGstInfo.getGst();
			adjustCashplusGstField.setValue(adjustCashplusGst);
			
			adjustCashplusGstField.setDisabled(adjustCashplusGst==null);
		}
		
	}
	
	
	
	
	
	
	
}
