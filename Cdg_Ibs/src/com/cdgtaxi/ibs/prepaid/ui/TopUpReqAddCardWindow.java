package com.cdgtaxi.ibs.prepaid.ui;

import static com.cdgtaxi.ibs.util.ComponentUtil.getSelectedItems;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelSet;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.api.Decimalbox;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonSearchByCardWindow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.ProductUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


@SuppressWarnings("serial")
public class TopUpReqAddCardWindow extends CommonSearchByCardWindow implements AfterCompose, ListDataListener  {

	private ListModelSet promotionModels;
	private Listbox promotionListbox;
	protected AccountLabelMacroComponent accountMC;
	private Label cardNoLabel, nameOnCardLabel, balanceExpiryDateLabel, cardExpiryDateLabel;
	private Label currentValueLabel, currentCashplusLabel, topUpCashplusLabel;
	private Decimalbox topUpValueField;

	protected Listbox expiryHourListBox, expiryMinListBox;
	protected Datebox newBalanceExpiryDateField, newBalanceExpiryDateTimeField;
	protected Row balanceExpiryDateRow, balanceExpiryDateTimeRow;
	
	protected Listbox productTypeField;
	
	private PmtbTopUpReqCard toPopulateCard;

	private AmtbAccount selectedAcct;
	private PmtbProductType selectedProductType;
	
	private String mode;
	
	
	public class ResultRenderer implements ListitemRenderer {
		    public void render(Listitem row, Object obj) {
		
		    	MstbPromotionCashPlus model = (MstbPromotionCashPlus) obj;
		    	row.appendChild(new Listcell(model.getPromoCode()));
		    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCashplus())));
		    	row.setValue(model);
		    }
	}
	

	
	public TopUpReqAddCardWindow() {
		@SuppressWarnings("unchecked")
		Map<String,Object> params = (Map<String,Object>)Executions.getCurrent().getArg();
		Integer acctNo = (Integer) params.get("acctNo");
		String productTypeId = (String) params.get("productTypeId");
		
		selectedAcct = this.businessHelper.getAccountBusiness().getAccount(String.valueOf(acctNo));
		selectedProductType = this.businessHelper.getProductTypeBusiness().getProductType(productTypeId);
		
		mode = (String) params.get("mode");
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			
			logger.debug("edit mode");
			toPopulateCard = (PmtbTopUpReqCard) params.get("card");
		}
		
	}
	
	
	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		super.onCreate(ce);
		
		accountMC.populateDetails(selectedAcct);
		
		Map<Integer, String> expiryHourMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 24; i++) {
			expiryHourMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryHourListBox, expiryHourMap, true);
		ComponentUtil.setSelectedItem(expiryHourListBox, null);

		Map<Integer, String> expiryMinMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 60; i++) {
			expiryMinMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryMinListBox, expiryMinMap, true);
		ComponentUtil.setSelectedItem(expiryMinListBox, null);

		Map<PmtbProductType, String> productTypeMap = Maps.newHashMap();
		productTypeMap.put(selectedProductType, selectedProductType.getName());
		ComponentUtil.buildListbox(productTypeField, productTypeMap, false);
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			
			PmtbProduct product = toPopulateCard.getPmtbProduct();
			
			String cardNo = product.getCardNo();
			cardNoTextBox.setText(cardNo);
			Events.sendEvent(new Event(Events.ON_CHANGE, cardNoTextBox, cardNo));
			
			cardNameComboBox.close();
			ComponentUtil.setSelectedItem(cardNameComboBox, product);

			populateFieldsForEdit();
		}

	}
	
	@SuppressWarnings("unchecked")
	private void calculateTopUpCashback(){
		
		BigDecimal totalCashback = new BigDecimal(0);
		Set<MstbPromotionCashPlus> promotions = promotionModels.getInnerSet();
		for(MstbPromotionCashPlus promotion: promotions){
			totalCashback = totalCashback.add(promotion.getCashplus());
		}
		topUpCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(totalCashback));
	}
	
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		promotionListbox.setItemRenderer(new ResultRenderer());
		promotionModels = new ListModelSet();
		promotionListbox.setModel(promotionModels);
		promotionModels.addListDataListener(this);
		

	}

	@Override
	public void onSelectCardName() throws InterruptedException {
		super.onSelectCardName();
		
		PmtbProduct selectedProduct = getSelectedProduct();
		if(selectedProduct!=null){
			 cardNoLabel.setValue(selectedProduct.getCardNo());
			 nameOnCardLabel.setValue(selectedProduct.getNameOnProduct());
			 balanceExpiryDateLabel.setValue(DateUtil.convertDateToStr(selectedProduct.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
			 
			 String cardExpiryDateTime = DateUtil.convertDateToStr(selectedProduct.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT);
			 cardExpiryDateTime = cardExpiryDateTime + " " + DateUtil.convertDateToStr(selectedProduct.getExpiryTime(), DateUtil.GLOBAL_TIME_FORMAT);
			 cardExpiryDateLabel.setValue(cardExpiryDateTime);
			 
			 currentValueLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCardValue()));
			 currentCashplusLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(selectedProduct.getCashplus()));
		
			 PmtbProductType productType = selectedProduct.getPmtbProductType();
			 String validityPeriod = productType.getValidityPeriod();
			 
		
			//balance EXPIRY defaulted to the top up date + default balance EXPIRY months configure in product type
			//if the calculated balance EXPIRY date more than product existing balance EXPIRY date.
			
			Date existingBalanceExpiryDate = selectedProduct.getBalanceExpiryDate();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, productType.getDefaultBalanceExpMonths());
			Date calculatedBalanceExpiryDate = cal.getTime();
			
			Date defaultBalanceExpiryDate = null;
			if(calculatedBalanceExpiryDate.after(existingBalanceExpiryDate)){
				defaultBalanceExpiryDate = calculatedBalanceExpiryDate;
			} else {
				defaultBalanceExpiryDate = existingBalanceExpiryDate;
			}

			if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)){
				 balanceExpiryDateRow.setVisible(true);
				 balanceExpiryDateTimeRow.setVisible(false);
				 newBalanceExpiryDateField.setValue(defaultBalanceExpiryDate);
				 newBalanceExpiryDateTimeField.setValue(null);
				 ComponentUtil.setSelectedItem(expiryHourListBox, null);
				 ComponentUtil.setSelectedItem(expiryMinListBox, null);
				 
			 } else if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)){
				 balanceExpiryDateRow.setVisible(false);
				 balanceExpiryDateTimeRow.setVisible(true);
				 newBalanceExpiryDateField.setValue(null);
				 newBalanceExpiryDateTimeField.setValue(defaultBalanceExpiryDate);
				 ComponentUtil.setSelectedItem(expiryHourListBox, new Integer(23));
				 ComponentUtil.setSelectedItem(expiryMinListBox, new Integer(59));
			 }
		} else {
			ComponentUtil.reset(cardNoLabel, nameOnCardLabel,balanceExpiryDateLabel, cardExpiryDateLabel, currentValueLabel, currentCashplusLabel);
		}
		
		
		

	}
	
	
	public void addCard() throws Exception{
		
		checkProductNotNull();
		PmtbProduct selectedProduct = getSelectedProduct();
		
		Date newBalExpDate= null;
		if (balanceExpiryDateRow.isVisible()) {
			newBalExpDate = newBalanceExpiryDateField.getValue();
			
			if(newBalExpDate!=null){
				newBalExpDate = DateUtil.convertDateTo2359Hours(newBalExpDate);
				newBalExpDate = DateUtil.getLastUtilDateOfMonth(newBalExpDate);
			}
			
		} else if(balanceExpiryDateTimeRow.isVisible()){
			
			newBalExpDate = newBalanceExpiryDateTimeField.getValue();
			Integer hour = ComponentUtil.getSelectedItem(expiryHourListBox);
			Integer min = ComponentUtil.getSelectedItem(expiryMinListBox);
			
			if(newBalExpDate!=null && hour!=null && min!=null){
				
				Calendar expiryCal = Calendar.getInstance();
				expiryCal.setTime(newBalExpDate);
				expiryCal.set(Calendar.HOUR_OF_DAY, hour);
				expiryCal.set(Calendar.MINUTE, min);
				expiryCal.set(Calendar.SECOND, 59);
				expiryCal.set(Calendar.MILLISECOND, 999);
				newBalExpDate = expiryCal.getTime();
			
			} else if(newBalExpDate==null && hour==null && min==null){
				//do nothing
			} else {
				throw new WrongValueException("Both expiry date and expiry time must have value if either one is not empty.");
			}
		}
		
		if(newBalExpDate!=null){
			ProductUtil.validateBalanceExpiryDateNoPast(newBalExpDate);
			ProductUtil.validateNoEarlierThanCurrentBalanceExpiryDate(newBalExpDate, selectedProduct.getBalanceExpiryDate());
		}
		
		PmtbTopUpReqCard reqCard = new PmtbTopUpReqCard();
		
		PmtbProductType productType = selectedProduct.getPmtbProductType();
		//the reason to set request card no with current time stamp is to make the added request card being unique in card models, 
		//so that whenever the card is selected and removed, proper card will be retrieved to delete using equals and hash method
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			reqCard.setReqCardNo(toPopulateCard.getReqCardNo());
		} else {
			reqCard.setReqCardNo(new BigDecimal(new Date().getTime()));
		}
		
		reqCard.setPmtbProduct(selectedProduct);
		reqCard.setTopUpValue(topUpValueField.getValue());
		reqCard.setNewBalanceExpiryDate(newBalExpDate);
	

		
		reqCard.setTopUpCashplus(StringUtil.stringToBigDecimal(topUpCashplusLabel.getValue()));
		
		//find top up fee of the card
		BigDecimal topUpFee = productType.getTopUpFee();
		
		if(topUpFee==null){
			logger.debug("top up fee is null");
			topUpFee = new BigDecimal("0.00");
		}
		reqCard.setTopUpFee(topUpFee);
		
		@SuppressWarnings("unchecked")
		Set<MstbPromotionCashPlus> selectedPromotions = Sets.newHashSet(promotionModels.iterator());
		reqCard.setMstbPromotionCashPluses(selectedPromotions);
		
		//by default is to waive the top up fee
		reqCard.setWaiveTopUpFeeFlag(NonConfigurableConstants.BOOLEAN_YN_YES);
		
		CommonWindow window = this.back();
		if(window instanceof NewTopUpReqWindow){
			((NewTopUpReqWindow)window).addNewCardIntoCardListBox(reqCard);
		}
	
	}
	
	public void cancel() throws InterruptedException{
		this.back();
	}
	
	
	public void promptAddPromotionWindow() throws InterruptedException{
		this.forward(Uri.ADD_PROMOTION_CASHPLUS, null);
	}
	
	
	public void deletePromotion() throws InterruptedException{
		logger.debug("deletePromotion");
		List<MstbPromotionCashPlus> selectedItems = getSelectedItems(promotionListbox);
		
		promotionModels.removeAll(selectedItems);
		
		
	}
	
	public void addPromotionIntoPromotionsListBox(List<MstbPromotionCashPlus> selectedItems) throws InterruptedException { 
		for(Object item: selectedItems){
			promotionModels.add(item);
		}
		
	
	}


	@Override
	public List<PmtbProduct> searchProductsByCardNoAndName(String cardNo, String cardName) {
		
		return this.businessHelper.getPrepaidBusiness().getTopUpableProducts(selectedAcct.getAccountNo(), selectedProductType.getProductTypeId(), cardNo, cardName);
	}


	public void onChange(ListDataEvent arg0) {
		calculateTopUpCashback();
		
	}
	
	private void populateFieldsForEdit(){
		
		
		if(toPopulateCard!=null){
			
			PmtbProduct product = toPopulateCard.getPmtbProduct();
			PmtbProductType productType = product.getPmtbProductType();
			String validityPeriod = productType.getValidityPeriod();
			
			logger.debug("start populating edit fields");
			
			topUpValueField.setRawValue(toPopulateCard.getTopUpValue());
			
			Date balanceExpiryDate = toPopulateCard.getNewBalanceExpiryDate();
			
			if(balanceExpiryDate!=null){
				if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriod)){
					newBalanceExpiryDateField.setRawValue(balanceExpiryDate);
					
				} else if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriod)){
				
					newBalanceExpiryDateTimeField.setRawValue(balanceExpiryDate);
					Calendar expiryCalendar = Calendar.getInstance();
					expiryCalendar.setTime(balanceExpiryDate);
					
					Integer hour = expiryCalendar.get(Calendar.HOUR_OF_DAY);
					Integer minute = expiryCalendar.get(Calendar.MINUTE);
				 
					ComponentUtil.setSelectedItem(expiryHourListBox, hour);
					ComponentUtil.setSelectedItem(expiryMinListBox, minute);
				 
				}
			}

			promotionModels.clear();
			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = toPopulateCard.getMstbPromotionCashPluses();
			
			if(mstbPromotionCashPluses!=null){
				promotionModels.addAll(mstbPromotionCashPluses);
			}
		
		}
	
	}
	
	


	
	
	

	
	
}
