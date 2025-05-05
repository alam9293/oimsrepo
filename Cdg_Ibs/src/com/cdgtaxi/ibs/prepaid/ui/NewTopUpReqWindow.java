package com.cdgtaxi.ibs.prepaid.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Intbox;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.ui.CommonSearchByDivisionDepartmentAccountWindow;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.elixirtech.net.NetException;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class NewTopUpReqWindow extends CommonSearchByDivisionDepartmentAccountWindow implements AfterCompose, ListDataListener {


	private static final Logger logger = Logger.getLogger(NewTopUpReqWindow.class);
	
	private Listbox newBalanceExpiryDateDurationTypeField;
	private Intbox newBalanceExpiryDateDurationLengthField;
	private Textbox remarksField;
	private ListModelList cardModels;
	private Listbox cardListbox;
	private Checkbox checkAllBox;
	private Checkbox paidByCreditCard;
	private Checkbox waiveTopUpFeecCheckAllBox;
	private Combobox creditTermBox;
	private Label totalAmountLabel;
	private Listbox productTypeField;
	private PmtbProductType selectedProductType;
	
	protected Listbox expiryHourListBox, expiryMinListBox;
	protected Datebox newBalanceExpiryDateField, newBalanceExpiryDateTimeField;
	protected Row balanceExpiryDateRow, balanceExpiryDateTimeRow, durationLenRow;
	private Button clicktoApplyNewBalanceExpiryDateBtn;
	
	public class ResultRenderer implements ListitemRenderer {
	    public void render(Listitem row, Object obj) {
	    	final PmtbTopUpReqCard model = (PmtbTopUpReqCard) obj;
	    	PmtbProduct product = model.getPmtbProduct();
	    
	    	Listcell checkboxListcell = new Listcell();
			Checkbox checkbox = new Checkbox();
			checkbox.setChecked(model.isSelected());
			checkbox.addEventListener(Events.ON_CHECK, new EventListener() {
				public void onEvent(Event e) throws Exception {
					Checkbox self = (Checkbox) e.getTarget();
					model.setSelected(self.isChecked());
				}
			});
			checkboxListcell.appendChild(checkbox);
			row.appendChild(checkboxListcell);
	    	
			row.appendChild(new Listcell(product.getCardNo()));
	    	row.appendChild(new Listcell(product.getNameOnProduct()));
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpValue())));
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpCashplus())));

	    	Set<MstbPromotionCashPlus> mstbPromotionCashPluses = model.getMstbPromotionCashPluses();
	    	Set<String> promotionCodeSet = Sets.newHashSet();
	    	for(MstbPromotionCashPlus cashPlus : mstbPromotionCashPluses){
	    		promotionCodeSet.add(cashPlus.getPromoCode());
	    	}
	    	String codeLabel = Joiner.on(", ").join(promotionCodeSet);	
	    	if(Strings.isNullOrEmpty(codeLabel)){
	    		codeLabel = "-";
	    	}
	    	row.appendChild(new Listcell(codeLabel));
	    	
	    	
	    	String balanceExpiryDateStr = DateUtil.convertDateToStr(model.getNewBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT);
	    	if(Strings.isNullOrEmpty(balanceExpiryDateStr)){
	    		balanceExpiryDateStr = "-";
	    	}
	    	row.appendChild(new Listcell(balanceExpiryDateStr));
	    	
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpFee())));
	    	
	    	Listcell waiveTopUpFeeListcell = new Listcell();
	    	Checkbox waiveTopUpCheckbox = new Checkbox();
	    	waiveTopUpCheckbox.setChecked(NonConfigurableConstants.getBoolean(model.getWaiveTopUpFeeFlag()));
	    	waiveTopUpCheckbox.addEventListener(Events.ON_CHECK, new EventListener() {
	    	
				public void onEvent(Event e) throws Exception {
					Checkbox self = (Checkbox)e.getTarget();
					model.setWaiveTopUpFeeFlag(NonConfigurableConstants.getBooleanFlag(self.isChecked()));
					calculateAndShowTotalAmount();
				}
			});
	    	waiveTopUpFeeListcell.appendChild(waiveTopUpCheckbox);
	    	row.appendChild(waiveTopUpFeeListcell);
	    	row.setValue(model);
	    }
	}


	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		cardListbox.setItemRenderer(new ResultRenderer());
		cardModels = new ListModelList();
		cardModels.addListDataListener(this);
		cardListbox.setModel(cardModels);
		
		init();
		
		
	}
	
	
	public void init(){
		
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
		
		ComponentUtil.buildListbox(newBalanceExpiryDateDurationTypeField, NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE, false);
		ComponentUtil.setSelectedItem(newBalanceExpiryDateDurationTypeField, NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION);
		onSelectNewBalanceExpiryDateDurationTypeField();
		
		Map<MstbCreditTermMaster, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMastersWithCOD();	
		ComponentUtil.buildCombobox(creditTermBox, creditTermMasters, false);
		

		totalAmountLabel.setValue("0");
	
		paidByCreditCard.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onChangingCreditPayment();
			}
		});
	}
	
	public void onChangingCreditPayment() throws InterruptedException{
		if(paidByCreditCard.isChecked()) {
			creditTermBox.setSelectedIndex(0);
			creditTermBox.setDisabled(true);
			
		}else {
			creditTermBox.setDisabled(false);
		}	
	}

	private void buildSubscribeProductTypeListBox(AmtbAccount selectedAccount){
		
		List<PmtbProductType> prepaidProductTypes = Lists.newArrayList();
		if(selectedAccount!=null) {
			prepaidProductTypes = this.businessHelper.getProductBusiness().getIssuableProductTypes(String.valueOf(selectedAccount.getAccountNo()), true);
		}
		
		Map<PmtbProductType, String> productTypeMap = Maps.newTreeMap(new Comparator<PmtbProductType>() {
			public int compare(PmtbProductType o1, PmtbProductType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		for(PmtbProductType productType: prepaidProductTypes){
			productTypeMap.put(productType, productType.getName());
		}
		
		
		ComponentUtil.buildListbox(productTypeField, productTypeMap, true);

	}
	@Override
	public void onSelectAccountName() throws InterruptedException {
		
		super.onSelectAccountName();

		AmtbAccount acct = getSelectedAccount();
		if(acct!=null){
			//The credit term value is retrieved and defaulted to the Account’s credit term.
			List<AmtbAcctCredTerm> creditTerms = this.businessHelper.getAccountBusiness().getCreditTerms(getSelectedAccount().getAccountNo());
			
			AmtbAcctCredTerm effectiveCreditTerm = this.businessHelper.getAccountBusiness().getEffectiveCreditTerm(creditTerms);
			if(effectiveCreditTerm!=null){
				ComponentUtil.setSelectedItem(creditTermBox, effectiveCreditTerm.getMstbCreditTermMaster());
			}
		} else {
			if(!creditTermBox.getChildren().isEmpty()){
				creditTermBox.setSelectedIndex(0);
			}
		}
		
		buildSubscribeProductTypeListBox(selectedAccount);
		
		cardModels.clear();
	}

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String name) {
		return this.businessHelper.getProductBusiness().getActiveAccountList(custNo, name);
	}

	
	public void addNewCard() throws InterruptedException{
		Map<String,Object> map = new HashMap<String,Object>();
		
		//before user able to add new card, he have to insert the account no first
		checkAccountNotNull();
		
		if(selectedProductType==null){
			throw new WrongValueException("You must select product type before proceed to add card.");
		}
		
		Integer acctNo = selectedAccount.getAccountNo();
		map.put("acctNo", acctNo);
		String productTypeId = selectedProductType.getProductTypeId();
		map.put("productTypeId", productTypeId);
		map.put("mode", NonConfigurableConstants.MODE_NEW);
		
		this.forward(Uri.TOP_UP_REQ_ADD_NEW_CARD, map);
	}
	
	
	public void addNewCardIntoCardListBox(PmtbTopUpReqCard card) throws InterruptedException{
	
		//check whether need to update or add
		@SuppressWarnings("unchecked")
		List<PmtbTopUpReqCard> cardList = cardModels.getInnerList();
	
		int updateIndex = cardList.indexOf(card);
		
		if(updateIndex!=-1){
			cardModels.set(updateIndex, card);
		} else {
			cardModels.add(card);
		}
	}
	
	public void removeNewCardFromCardListBox() throws InterruptedException{
		logger.debug("removeNewCardFromCardListBox");
		
		List<PmtbTopUpReqCard> checkedItems = Lists.newArrayList();
		for(Object obj: cardModels.getInnerList()){
			PmtbTopUpReqCard card = (PmtbTopUpReqCard) obj;
			if(card.isSelected()){
				checkedItems.add(card);
			}
		}
		
		cardModels.removeAll(checkedItems);
	}
	
	
	public void checkAllWaiveTopUpFee() throws InterruptedException{
		
		//this will only display the tick in the check box, it will not set waive issuance fee of card to yes or not
		ComponentUtil.toggelCheckAllBox(waiveTopUpFeecCheckAllBox, cardListbox);
		
		
	}
	
	
	public void checkAll() throws InterruptedException{
		
		ComponentUtil.toggelCheckAllBox(checkAllBox, cardListbox);
		
		
	}
	
	
	public void previewMiscInvoice() throws NetException, IOException, SuspendNotAllowedException, InterruptedException{
		
		displayProcessing();
		
		PmtbTopUpReq request = new PmtbTopUpReq();
		validateAndBind(request);
		
		request.setPmtbTopUpReqCards(Sets.newHashSet(request.getHolderCardList()));
		BmtbDraftInvHeader draftHeader = (BmtbDraftInvHeader) this.businessHelper.getPrepaidBusiness().createTopUpInvoice(request, getUserLoginIdAndDomain(), true);
			
		byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(request.getAmtbAccount(), draftHeader.getInvoiceHeaderNo().toString(), true);
		this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, draftHeader);

		AMedia media = new AMedia(NonConfigurableConstants.REPORT_NAME_INV_MISC + ".pdf", "pdf", Constants.FORMAT_PDF, bytes);
		Filedownload.save(media);
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void validateAndBind(PmtbTopUpReq request){

		//check whether account is empty
		checkAccountNotNull();
		
		//check balance expire date of cards
		List<PmtbTopUpReqCard> cardList = Lists.newArrayList(cardModels.iterator());
		
		Date currentDate = new Date();
		for(PmtbTopUpReqCard card: cardList){
			
			PmtbProduct product = card.getPmtbProduct();
			Date currentExpiryDate = product.getBalanceExpiryDate();
			
			Date newExpiryDate = card.getNewBalanceExpiryDate();
			String cardNo = card.getPmtbProduct().getCardNo();
			
			if(newExpiryDate==null){
				throw new WrongValueException("New Balance Expiry Date of card " +  cardNo + " cannot be null");
			}
			
			if(newExpiryDate.before(currentDate)){
				throw new WrongValueException("New Balance Expiry Date of card " +  cardNo + " shouldn't be earlier than current date time.");
			}
			
			if(newExpiryDate.before(currentExpiryDate)){
				throw new WrongValueException("New Balance Expiry Date of card " +  cardNo + " shouldn't be earlier than current balance expiry date.");
			}
		}
		
		// whether the total amount is negative when discount is too much
		BigDecimal totalAmount = calculateTotalAmount();
		if (totalAmount.doubleValue() < 0) {
			throw new WrongValueException("Total amount is negative value");
		}
		
		//create a request
		AmtbAccount acct = selectedAccount;
		request.setAmtbAccount(acct);
		request.setRequestRemarks(remarksField.getValue());
		request.setTotalAmount(totalAmount);
		
		MstbCreditTermMaster creditTermMaster = ComponentUtil.getSelectedItem(creditTermBox);
		request.setMstbCreditTermMaster(creditTermMaster);
		
		
		request.setHolderCardList(cardList);

	}
	

	public void submit() throws Exception{
		
		
		PmtbTopUpReq request = new PmtbTopUpReq();
		
		validateAndBind(request);
		
		if(!ComponentUtil.confirmBox("Do you confirm to submit the request?", "New Top Up Request")){
			return;
		}

		if(paidByCreditCard.isChecked()) {

		        // duplicate function of submit() from line 467
		        displayProcessing();

			    String link = getHttpServletRequest().getHeader("Origin")+"/ibs/redirect.zul?";
			  
			   
	
				this.businessHelper.getPrepaidBusiness().createTopUpCreditRequest(request,link);
				
				try {					
					this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(request);
				} catch (Exception e){
					LoggerUtil.printStackTrace(logger, e);
					logger.debug("Failed to generate report for request " + request.getReqNo());
				}
				
				//if the request is not pending but completed without approval, and its credit term is not COD, product will has been issued. 
				String msg = "Top Up request submitted successfully.";
		
				Messagebox.show(msg, "New Top Up Request", Messagebox.OK, Messagebox.INFORMATION);

				this.back();
			
		}else {
			displayProcessing();
			this.businessHelper.getPrepaidBusiness().createTopUpRequest(request);
			
			try {
				this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(request);
			} catch (Exception e){
				LoggerUtil.printStackTrace(logger, e);
				logger.debug("Failed to generate report for request " + request.getReqNo());
			}
			
			//if the request is not pending but completed without approval, and its credit term is not COD, product will has been issued. 
			String msg = "Top Up request submitted successfully.";
			//if the request is not pending but completed without approval
			if (!NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
				
				msg = " No approval required.";
				//and its credit term is not COD, then product will has been created. 
				if(request.getMstbCreditTermMaster() != null){
					int createdProductSize = request.getPmtbTopUpReqCards().size();
					msg += " As credit term is not COD, total " + createdProductSize +" card(s) were top up before payment received.";
				} else {
					msg += " As the credit term is COD, product(s) will only top up upon full payment.";
				}
			}
			
			Messagebox.show(msg, "New Top Up Request", Messagebox.OK, Messagebox.INFORMATION);
		
			
			this.back();
		}
	}
	

	public void onClickApplyNewBalanceExpiryDate(){
		
		List<PmtbTopUpReqCard> selectedCardList = Lists.newArrayList();
		for(Object obj: cardModels.getInnerList()){
			PmtbTopUpReqCard card = (PmtbTopUpReqCard) obj;
			if(card.isSelected()){
				selectedCardList.add(card);
			}
		}

		if(selectedCardList==null || selectedCardList.isEmpty()){
			throw new WrongValueException("Please select at least one card(s) from Card Details to apply for New Balance Expiry Date!");
		}
		
		String type = ComponentUtil.getSelectedItem(newBalanceExpiryDateDurationTypeField);
		
		for(PmtbTopUpReqCard card :selectedCardList){
		
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
				int extendMonth = newBalanceExpiryDateDurationLengthField.getValue();
				PmtbProduct product = card.getPmtbProduct();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(product.getBalanceExpiryDate());
				cal.add(Calendar.MONTH, extendMonth);
				newBalExpDate = DateUtil.getLastUtilDateOfMonth(cal.getTime());
			} else {
				logger.debug("Invalid type: " + type);
			}
			
			card.setNewBalanceExpiryDate(newBalExpDate);
		}
		
		ComponentUtil.reloadListModelList(cardModels);

	}
	
	private BigDecimal calculateTotalAmount() {

		BigDecimal totalAmount = new BigDecimal("0.00");

		@SuppressWarnings("unchecked")
		List<PmtbTopUpReqCard> cardList = cardModels.getInnerList();
		for (PmtbTopUpReqCard card : cardList) {
			totalAmount = totalAmount.add(card.getTopUpValue());
			// cash back should not null here as it should handle it with 0 during add card if there is no cash back
			totalAmount = totalAmount.add(card.getTopUpCashplus());

			if (!NonConfigurableConstants.getBoolean(card.getWaiveTopUpFeeFlag())) {
				totalAmount = totalAmount.add(card.getTopUpFee());
			}
		}
		
		return totalAmount;
	}

	
	public void calculateAndShowTotalAmount(){
		
		logger.debug("calculateAndShowTotalAmount");
		
		BigDecimal totalAmount = calculateTotalAmount();
		totalAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(totalAmount));	
	
	}
	
	// this would be call every time there is add/remove/modification on the list
	public void onChange(ListDataEvent e) {
		calculateAndShowTotalAmount();
	}
	
	
	public void selectCard() throws InterruptedException{
		
		logger.debug("onselect");
		PmtbTopUpReqCard selectedItem = ComponentUtil.getSelectedItem(cardListbox);
	
		cardListbox.clearSelection();
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("acctNo", selectedAccount.getAccountNo());
		map.put("productTypeId", selectedProductType.getProductTypeId());

		map.put("card", selectedItem);
		map.put("mode", NonConfigurableConstants.MODE_EDIT);
		forward(Uri.TOP_UP_REQ_ADD_NEW_CARD, map);
		
	}

	public List<AmtbAccount> populateDivisionAccounts(AmtbAccount account) {
		return this.businessHelper.getPaymentBusiness().searchBillableAccountByParentAccount(account);
	}


	public List<AmtbAccount> populateDepartmentAccounts(AmtbAccount account) {
		return this.businessHelper.getPaymentBusiness().searchBillableAccountByGrandParentAccount(account);
	}
	
	public void onSelectNewBalanceExpiryDateDurationTypeField() {

		String newBalanceExpiryDateDurationType = ComponentUtil.getSelectedItem(newBalanceExpiryDateDurationTypeField);

		if (newBalanceExpiryDateDurationType.equals(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DURATION)) {
			durationLenRow.setVisible(true);
			balanceExpiryDateRow.setVisible(false);
			balanceExpiryDateTimeRow.setVisible(false);
		} else if (newBalanceExpiryDateDurationType.equals(NonConfigurableConstants.NEW_BALANCE_EXPIRY_DATE_DURATION_TYPE_DATE)) {
			
			durationLenRow.setVisible(false);
			balanceExpiryDateRow.setVisible(false);
			balanceExpiryDateTimeRow.setVisible(false);
			
			if(selectedProductType==null){
				throw new WrongValueException("You must select product type before select duration type as date");
			}
			
			PmtbProductType productType = selectedProductType;
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
	
	
	public void onSelectProductType(){
		
		selectedProductType = ComponentUtil.getSelectedItem(productTypeField);
		
		boolean disabledButton = selectedProductType==null;
		clicktoApplyNewBalanceExpiryDateBtn.setDisabled(disabledButton);
		
		cardModels.clear();
	}
	
}
