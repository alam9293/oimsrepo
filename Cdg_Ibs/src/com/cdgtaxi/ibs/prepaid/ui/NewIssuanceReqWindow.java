package com.cdgtaxi.ibs.prepaid.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Decimalbox;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReqCard;
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

@SuppressWarnings("serial")
public class NewIssuanceReqWindow extends CommonSearchByDivisionDepartmentAccountWindow implements AfterCompose, ListDataListener {
	
	private static final Logger logger = Logger.getLogger(NewIssuanceReqWindow.class);

	private Decimalbox discountField, deliveryChargeField;
	private Textbox remarksField;
	private ListModelList cardModels;
	private Listbox cardListbox;
	private Checkbox waiveIssuanceFeecCheckAllBox;
	private Checkbox checkAllBox;
	private Combobox creditTermBox;
	private Label totalAmountLabel;

	private Combobox deliveryChargeTxnCodeField;
	
	public NewIssuanceReqWindow(){
	}
	
	
	public class ResultRenderer implements ListitemRenderer {
		public void render(Listitem row, Object obj) {
			final PmtbIssuanceReqCard model = (PmtbIssuanceReqCard) obj;
			
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
			
			row.appendChild(new Listcell(model.getNameOnProduct()));
			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCardValue())));
			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCashplus())));

			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = model.getMstbPromotionCashPluses();
			Set<String> promotionCodeSet = Sets.newHashSet();
			for (MstbPromotionCashPlus cashPlus : mstbPromotionCashPluses) {
				promotionCodeSet.add(cashPlus.getPromoCode());
			}
			String codeLabel = Joiner.on(", ").join(promotionCodeSet);
			if (Strings.isNullOrEmpty(codeLabel)) {
				codeLabel = "-";
			}
			row.appendChild(new Listcell(codeLabel));
			
			row.appendChild(new Listcell(DateUtil.convertDateToStr(model.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT)));
			
			Timestamp expiryTime = model.getExpiryTime();
			if(expiryTime!=null){
				row.appendChild(new Listcell(DateUtil.convertDateToStr(expiryTime, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT)));
			} else {
				row.appendChild(new Listcell(DateUtil.convertDateToStr(model.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT)));
			}

			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getIssuanceFee())));

			Listcell waiveIssuanceFeeListcell = new Listcell();
			Checkbox waiveIssuanceCheckbox = new Checkbox();

			waiveIssuanceCheckbox.setChecked(NonConfigurableConstants.getBoolean(model.getWaiveIssuanceFeeFlag()));
			waiveIssuanceCheckbox.addEventListener(Events.ON_CHECK, new EventListener() {
				public void onEvent(Event e) throws Exception {
					Checkbox self = (Checkbox) e.getTarget();
					model.setWaiveIssuanceFeeFlag(NonConfigurableConstants.getBooleanFlag(self.isChecked()));
					calculateAndShowTotalAmount();
				}
			});

			waiveIssuanceFeeListcell.appendChild(waiveIssuanceCheckbox);
			row.appendChild(waiveIssuanceFeeListcell);
			row.setValue(model);
			
			
		}
	}
	
	
	public void selectCard() throws InterruptedException{

		logger.debug("onselect");
		PmtbIssuanceReqCard selectedItem = ComponentUtil.getSelectedItem(cardListbox);
	
		cardListbox.clearSelection();
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("custNo", selectedItem.getAmtbAccount().getCustNo());
		map.put("card", selectedItem);
		map.put("mode", NonConfigurableConstants.MODE_EDIT);
	
		forward(Uri.ISSUANCE_REQ_ADD_NEW_CARD, map);
		
	}
	
	

	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);
		cardListbox.setItemRenderer(new ResultRenderer());
		cardModels = new ListModelList();
		cardModels.addListDataListener(this);
		cardListbox.setModel(cardModels);

		init();

	}
	
	
	

	public void init() {

		Map<MstbCreditTermMaster, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMastersWithCOD();
		ComponentUtil.buildCombobox(creditTermBox, creditTermMasters, false);
		totalAmountLabel.setValue("0");
		
	}

	@Override
	public void onSelectAccountName() throws InterruptedException {

		super.onSelectAccountName();
		AmtbAccount topAcct = getSelectedAccount();
		if (topAcct != null) {
			// The credit term value is retrieved and defaulted to the Account’s credit term.
			List<AmtbAcctCredTerm> creditTerms = this.businessHelper.getAccountBusiness().getCreditTerms(topAcct.getAccountNo());

			AmtbAcctCredTerm effectiveCreditTerm = this.businessHelper.getAccountBusiness().getEffectiveCreditTerm(creditTerms);
			if (effectiveCreditTerm != null) {
				ComponentUtil.setSelectedItem(creditTermBox, effectiveCreditTerm.getMstbCreditTermMaster());
			}
			
			//the transaction code is populate based on the account's entity
			AmtbAccount topAcctWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(topAcct);
			Integer entityNo = topAcctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();
			List<FmtbTransactionCode> codes = businessHelper.getFinanceBusiness().getEffectiveManualTxnCodes(entityNo);
			
			Map<FmtbTransactionCode, String> codeMap = Maps.newLinkedHashMap();
			for(FmtbTransactionCode code: codes ){
				codeMap.put(code, code.getDescription());
			}
			ComponentUtil.buildCombobox(deliveryChargeTxnCodeField, codeMap, true);
			
			
		} else {
			if (!creditTermBox.getChildren().isEmpty()) {
				creditTermBox.setSelectedIndex(0);
			}
		}

		cardModels.clear();

	}
	

	public void onSelectDivisionOrDepartment(){
		cardModels.clear();
	}

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String name) {
		return this.businessHelper.getProductBusiness().getActiveAccountList(custNo, name);
	}

	public List<AmtbAccount> populateDivisionAccounts(AmtbAccount account) {
		return this.businessHelper.getPaymentBusiness().searchBillableAccountByParentAccount(account);
	}


	public List<AmtbAccount> populateDepartmentAccounts(AmtbAccount account) {
		return this.businessHelper.getPaymentBusiness().searchBillableAccountByGrandParentAccount(account);
	}
	
	public void addNewCard() throws InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		// before user able to add new card, he have to insert the account no first

		checkAccountNotNull();

		String acctNo = String.valueOf(selectedAccount.getAccountNo());
		map.put("acctNo", acctNo);
		map.put("mode", NonConfigurableConstants.MODE_NEW);
		
		this.forward(Uri.ISSUANCE_REQ_ADD_NEW_CARD, map);
	}

	public void addNewCardIntoCardListBox(PmtbIssuanceReqCard card) throws InterruptedException {

		//check whether need to update or add
		@SuppressWarnings("unchecked")
		List<PmtbIssuanceReqCard> cardList = cardModels.getInnerList();
	
		int updateIndex = cardList.indexOf(card);
		logger.debug("updateIndex: " + updateIndex);
		
		if(updateIndex!=-1){
			cardModels.set(updateIndex, card);
		} else {
			cardModels.add(card);
		}
	}

	public void removeNewCardFromCardListBox() throws InterruptedException {
		logger.debug("removeNewCardFromCardListBox");
		
		List<PmtbIssuanceReqCard> checkedItems = Lists.newArrayList();
		for(Object obj: cardModels.getInnerList()){
			PmtbIssuanceReqCard card = (PmtbIssuanceReqCard) obj;
			if(card.isSelected()){
				checkedItems.add(card);
			}
		}
	
		cardModels.removeAll(checkedItems);
	}

	
	public void checkAll() throws InterruptedException {

		// this will only display the tick in the check box, it will not set waive issuance fee of card to yes or not
		ComponentUtil.toggelCheckAllBox(checkAllBox, cardListbox);

	}

	
	public void checkAllWaiveIssuanceFee() throws InterruptedException {

		// this will only display the tick in the check box, it will not set waive issuance fee of card to yes or not
		ComponentUtil.toggelCheckAllBox(waiveIssuanceFeecCheckAllBox, cardListbox);

	}

	
	@SuppressWarnings("unchecked")
	public void validateAndBind(PmtbIssuanceReq request){
		
		// check whether account is empty
		if (accountNoIntBox.getValue() == null) {
			throw new WrongValueException("Account No field is mandatory");
		}

		// whether the total amount is negative when discount is too much
		BigDecimal totalAmount = calculateTotalAmount();
		if (totalAmount.doubleValue() < 0) {
			throw new WrongValueException("Total amount is negative value");
		}

		BigDecimal deliveryCharge = deliveryChargeField.getValue();
		FmtbTransactionCode txnCode = ComponentUtil.getSelectedItem(deliveryChargeTxnCodeField);
		
		if(deliveryCharge!=null && deliveryCharge.doubleValue()>0){
			
			if(txnCode==null){
				throw new WrongValueException("Delivery Charge Transaction Code cannot be empty when Delivery Charge amount more than zero.");
			}
		}
		
		// create a request
		AmtbAccount acct = selectedAccount;
		request.setAmtbAccount(acct);
		request.setDeliveryCharge(deliveryCharge);
		request.setDeliveryChargeTxnCode(txnCode);
		request.setDiscount(discountField.getValue());
		request.setRequestRemarks(remarksField.getValue());
		request.setTotalAmount(totalAmount);
		
		MstbCreditTermMaster creditTermMaster = ComponentUtil.getSelectedItem(creditTermBox);
		request.setMstbCreditTermMaster(creditTermMaster);
		
		List<PmtbIssuanceReqCard> cardList = Lists.newArrayList(cardModels.iterator());
		
		// there is reason to put the card list into holder instead of pmtbRequestCards, as pmtbRequestCards is a set, 
		// before the card is saved, the REQ_CARD_NO might be null, this would result some of the card being replaced while putting into set
		// depending on hash code and equals method
		request.setHolderCardList(cardList);
	}
	
	
	public void previewMiscInvoice() throws NetException, IOException, SuspendNotAllowedException, InterruptedException {

		displayProcessing();
		
		PmtbIssuanceReq request = new PmtbIssuanceReq();
		validateAndBind(request);
		request.setPmtbIssuanceReqCards(Sets.newHashSet(request.getHolderCardList()));
		BmtbDraftInvHeader draftHeader = (BmtbDraftInvHeader)this.businessHelper.getPrepaidBusiness().createIssuanceInvoice(request, getUserLoginIdAndDomain(), true);
	
		byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(request.getAmtbAccount(), draftHeader.getInvoiceHeaderNo().toString(), true);
		this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, draftHeader);

		AMedia media = new AMedia(NonConfigurableConstants.REPORT_NAME_INV_MISC_PREPAID + ".pdf", "pdf", Constants.FORMAT_PDF, bytes);
		Filedownload.save(media);
		
	}


	public void submit() throws InterruptedException, NetException, IOException {

		
		logger.info("submit the issuance request");
		
		PmtbIssuanceReq request = new PmtbIssuanceReq();
		
		validateAndBind(request);

		if (!ComponentUtil.confirmBox("Do you confirm to submit the request?", "New Issuance Request")) {
			return;
		}

		displayProcessing();
		this.businessHelper.getPrepaidBusiness().createIssuanceRequest(request);
		
		try {
			this.businessHelper.getPrepaidBusiness().generatePrepaidInvoiceFileForEligibleRequest(request);
		} catch (Exception e){
			LoggerUtil.printStackTrace(logger, e);
			logger.debug("Failed to generate report for request " + request.getReqNo());
		}
		
		String msg = "Issuance request submitted successfully.";
		//if the request is not pending but completed without approval
		if (!NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			
			msg = " No approval required.";
			//and its credit term is not COD, then product will has been created. 
			if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT.equals(request.getStatus())){
				int createdProductSize = request.getPmtbIssuanceReqCards().size();
				msg += " As credit term is not COD, total " + createdProductSize +" product(s) were created before payment received.";
			} else {
				msg += " As the credit term is COD, product(s) will only created upon full payment.";
			}
		}

		Messagebox.show(msg, "New Issuance Request", Messagebox.OK, Messagebox.INFORMATION);

		this.back();

	}

	private BigDecimal calculateTotalAmount() {

		BigDecimal totalAmount = new BigDecimal("0.00");

		@SuppressWarnings("unchecked")
		List<PmtbIssuanceReqCard> cardList = cardModels.getInnerList();
		for (PmtbIssuanceReqCard card : cardList) {
			totalAmount = totalAmount.add(card.getCardValue());
			// cash back should not null here as it should handle it with 0 during add card if there is no cash back
			totalAmount = totalAmount.add(card.getCashplus());

			if (!NonConfigurableConstants.getBoolean(card.getWaiveIssuanceFeeFlag())) {
				totalAmount = totalAmount.add(card.getIssuanceFee());
			}
		}

		BigDecimal discount = discountField.getValue();
		BigDecimal deliveryCharge = deliveryChargeField.getValue();

		totalAmount = totalAmount.subtract(discount).add(deliveryCharge);

		return totalAmount;
	}

	public void calculateAndShowTotalAmount() {

		logger.debug("calculateAndShowTotalAmount");

		BigDecimal totalAmount = calculateTotalAmount();
		totalAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(totalAmount));

	}

	// this would be call every time there is add/remove/modification on the list
	public void onChange(ListDataEvent e) {
		calculateAndShowTotalAmount();
	}



	
}
