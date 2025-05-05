package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
	
@SuppressWarnings({"serial","unchecked"})
public class ReplaceProductWindow extends CommonWindow implements AfterCompose{
	
	
	private static Logger logger = Logger.getLogger(ReplaceProductWindow.class);
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal> ();
	private Listbox replacementReasonField;
	private Textbox remarksField;

	private Label reportDateLabel;
	private Label replacementFeeLabel;
	private Listbox newCardNoField;
	private ListModelList cardModels;
	private Listbox cardListbox;
	private Listheader nameOnCardListHeader;
	private Checkbox waiveReplacementFeesField;
	private List<PmtbProduct> selectedProducts;
	private String validityPeriodFlag;
	private String nameOnCardFlag;
	private String prepaidFlag;
	private Button replaceButton;
	
	protected Row expiryDateRow, expiryDateTimeRow;
	protected Listbox expiryHourListBox, expiryMinListBox;
	protected Datebox expiryDateField, expiryDayMonthYearDBField;
	
	PmtbProduct firstProduct;
	
	public class ResultRenderer implements ListitemRenderer {
	    public void render(Listitem row, Object obj) {
	    	final PmtbProduct product = (PmtbProduct) obj;
	    
	    	AmtbAccount acct = product.getAmtbAccount();
	    	AmtbAccount topAcct = businessHelper.getAccountBusiness().getTopLevelAccount(acct);
	    	PmtbProductType productType = product.getPmtbProductType();
		    
	    	row.appendChild(new Listcell(topAcct.getCustNo()));
	    	row.appendChild(new Listcell(topAcct.getAccountName()));
	    	row.appendChild(new Listcell(productType.getName()));
	    	row.appendChild(new Listcell(product.getCardNo()));
	    	row.appendChild(new Listcell(NonConfigurableConstants.PRODUCT_STATUS.get(product.getCurrentStatus())));
	    	row.appendChild(newListcell(product.getIssueDate(), DateUtil.GLOBAL_DATE_FORMAT));
	    	
	   
	    	if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_NO.equals(validityPeriodFlag)){
	    		row.appendChild(newListcell("-"));
		    } else if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY.equals(validityPeriodFlag)){
	    		row.appendChild(newListcell(product.getExpiryDate(), DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
		    } else if(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM.equals(validityPeriodFlag)){
		    	row.appendChild(newListcell(product.getExpiryTime(), DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
		    } else {
		    	row.appendChild(newListcell("Unsupported validity period: " + validityPeriodFlag));
		    }
	    
	    	String suspendDate = "-";
	    	if(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED.equals(product.getCurrentStatus())){
				PmtbProductStatus productStatus=businessHelper.getProductBusiness().getSuspendProductStatus(product);//need to put timestamp comparison
				if (productStatus!=null){
					suspendDate = DateUtil.convertDateToStr(productStatus.getStatusDt(),DateUtil.GLOBAL_DATE_FORMAT);
				}
			}
	    	row.appendChild(newListcell(suspendDate));
	   
	    	//don't need to avoid the cell appended to row when name on card flag is N,
	    	//as when list header is set to hidden, the cell itself will be hidden as well
			Listcell nameOnCardCell=new Listcell();
			CapsTextbox nameTextbox=new CapsTextbox();
			nameTextbox.setStyle("text-transform:uppercase");
			product.setTransientNameOnProduct(product.getNameOnProduct());
			nameTextbox.setValue(product.getTransientNameOnProduct());
			nameTextbox.addEventListener(Events.ON_CHANGE, new EventListener() {
		    	
				public void onEvent(Event e) throws Exception {
					Textbox self = (Textbox)e.getTarget();
					product.setTransientNameOnProduct(self.getValue());
				}
			});
			nameOnCardCell.appendChild(nameTextbox);
			row.appendChild(nameOnCardCell);
	    
			Listcell embossNameOnCardCell = new Listcell();
	    	Checkbox embossNameOnCardCheckBox = new Checkbox();
	    	
	    	product.setTransientEmbossNameOnCard(product.getEmbossNameOnCard());
	    	
	    	embossNameOnCardCheckBox.setChecked(NonConfigurableConstants.getBoolean(product.getTransientEmbossNameOnCard()));
	    	embossNameOnCardCheckBox.addEventListener(Events.ON_CHECK, new EventListener() {
	    	
				public void onEvent(Event e) throws Exception {
					Checkbox self = (Checkbox)e.getTarget();
					product.setTransientEmbossNameOnCard(NonConfigurableConstants.getBooleanFlag(self.isChecked()));

				}
			});
	    	embossNameOnCardCell.appendChild(embossNameOnCardCheckBox);
	    	row.appendChild(embossNameOnCardCell);
			
	    }
			
	}
	

	public void afterCompose() {
		Components.wireVariables(this, this);
		cardListbox.setItemRenderer(new ResultRenderer());
		cardModels = new ListModelList();
		cardListbox.setModel(cardModels);
		init();
	}
	
	
	public void init(){
	
		reportDateLabel.setValue(DateUtil.getStrCurrentDate());
		ComponentUtil.buildListbox(replacementReasonField, ConfigurableConstants.getReplaceReasons(), false);
		// Default the selection to this code
		ComponentUtil.setSelectedItem(replacementReasonField, "FD");
		ComponentUtil.buildListbox(newCardNoField, NonConfigurableConstants.BOOLEAN_YN, false);
		ComponentUtil.setSelectedItem(newCardNoField, NonConfigurableConstants.BOOLEAN_YN_NO);
		
		Map<Integer, String> expiryHourMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 24; i++) {
			expiryHourMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryHourListBox, expiryHourMap, true);
		ComponentUtil.setSelectedIndex(expiryHourListBox, 0);

		Map<Integer, String> expiryMinMap = Maps.newLinkedHashMap();
		for (int i = 0; i < 60; i++) {
			expiryMinMap.put(i, String.valueOf(i));
		}
		ComponentUtil.buildListbox(expiryMinListBox, expiryMinMap, true);
		ComponentUtil.setSelectedIndex(expiryMinListBox, 0);
		
		HashMap<String,Object> params = (HashMap<String,Object>)Executions.getCurrent().getArg();
	  	
		Object productNoSetObj = params.get("productNoSet");
		if(productNoSetObj!=null){
			productIdSet = (Set<BigDecimal>) productNoSetObj;
	  	} else {
	  		for(String productId : params.keySet()){
				if(productId.indexOf("productId")>=0){
					productIdSet.add(new BigDecimal((String)params.get(productId)));
				}
			}
	  	}
		
	  	selectedProducts = this.businessHelper.getProductBusiness().getProductsbyIds(productIdSet);
		
	  	//get the first product so that we can retrieve product type to populate labels
	  	//this is assumption that among selected products, they all have the same product type
	    firstProduct = selectedProducts.get(0);
	  	PmtbProductType firstProductType = firstProduct.getPmtbProductType();
	  	validityPeriodFlag = firstProductType.getValidityPeriod();
		nameOnCardFlag = firstProductType.getNameOnProduct();
		prepaidFlag = firstProductType.getPrepaid();
		
		if(NonConfigurableConstants.getBoolean(prepaidFlag)){
			replaceButton.setLabel("Replace & Generate Receipt");
		} else {
			replaceButton.setLabel("Replace");
		}
		
		
	  	replacementFeeLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(firstProductType.getReplacementFees()));
	  	nameOnCardListHeader.setVisible(NonConfigurableConstants.getBoolean(nameOnCardFlag));
		
	  	String validityPeriod = firstProductType.getValidityPeriod();
		
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

		if(!selectedProducts.isEmpty()){
			cardModels.addAll(selectedProducts);
		}
		
	}
	@Override
	public void refresh() throws InterruptedException {
		
	}

	
	public void replace() throws InterruptedException{
		
		String replacementRemarks= remarksField.getValue();
		
		//let's deal with the expire date
		java.sql.Date expiryDate = null;
		Timestamp expiryTime = null;
		if (expiryDateRow.isVisible()) {
			expiryDate = DateUtil.convertUtilDateToSqlDate(expiryDateField.getValue());
			logger.info("Expiry Date: " + expiryDate);
			
			if (expiryDate != null) {
				if (expiryDate.before(new Date())) {
					throw new WrongValueException("Expiry Date shouldn't be earlier than current month.");
				}
				Date maxExpiryDate = this.businessHelper.getProductBusiness().getValidExpiryDate((HashSet<BigDecimal>) productIdSet);
				if(maxExpiryDate!=null){
					
					if(expiryDate.before(maxExpiryDate)){
						throw new WrongValueException("New expiry date [" + DateUtil.convertDateToStr(expiryDate, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT) +"] cannot be earlier than current product's expiry date [" 
								+ DateUtil.convertDateToStr(maxExpiryDate, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT) +"].");
					}
				}
				
			}
			
			
		} else if (expiryDateTimeRow.isVisible()) {
			Date expiryUptoDay = expiryDayMonthYearDBField.getValue();
			expiryDate = DateUtil.convertUtilDateToSqlDate(expiryUptoDay);
			Integer hour = ComponentUtil.getSelectedItem(expiryHourListBox);
			Integer min = ComponentUtil.getSelectedItem(expiryMinListBox);
			
			if(expiryDate!=null && hour!=null && min!=null){
				
				new RequiredEqualOrLaterThanCurrentDateConstraint().validate(expiryDayMonthYearDBField, expiryUptoDay);

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
				
				Date maxExpiryDateTime = this.businessHelper.getProductBusiness().getValidExpiryDateTime((HashSet<BigDecimal>) productIdSet);
				if(maxExpiryDateTime!=null){
					
					if(expiryTime.before(maxExpiryDateTime)){
						throw new WrongValueException("New expiry date [" + DateUtil.convertDateToStr(expiryTime, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT) +"] cannot be earlier than current product's expiry date [" 
								+ DateUtil.convertDateToStr(maxExpiryDateTime, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT) +"].");
					}
				}
			} else if(expiryDate==null && hour==null && min==null){
				expiryTime =null;
			} else {
				throw new WrongValueException("Either all fields of expiry date and expiry time contain value or all must be empty.");
			}

		}
		
		//calculate the replacement fee
		BigDecimal fee = null;
		boolean isWaiveReplacementFee = waiveReplacementFeesField.isChecked();
		if(isWaiveReplacementFee){
			fee = new BigDecimal("0");
		}
		else{
			fee = new BigDecimal(replacementFeeLabel.getValue());
			if(fee==null || fee.doubleValue()<0){
				throw new WrongValueException("Replacement fees should be postive number.");
			}
		}

		String replacementReason= ComponentUtil.getSelectedItem(replacementReasonField);
		String newCardNoFlag = ComponentUtil.getSelectedItem(newCardNoField);
		boolean isGenerateNewCard = NonConfigurableConstants.getBoolean(newCardNoFlag);
		PmtbProductType firstProductType = firstProduct.getPmtbProductType();
		
		if(!ComponentUtil.confirmBox("Are you sure to replace?", "Product Replacement Confirmation")){
			return;
		}
		
		displayProcessing();
		
		List<PmtbProduct> issueProducts = Lists.newArrayList();
		List<String> replacedCards = Lists.newArrayList();
		List<String> errorCards = Lists.newArrayList();
		

		for(PmtbProduct product: selectedProducts) {
			
			try {
				PmtbProduct newProduct = this.businessHelper.getProductBusiness().replaceProduct(product, firstProductType, expiryDate, expiryTime, isGenerateNewCard, isWaiveReplacementFee, fee, replacementRemarks, replacementReason, CommonWindow.getUserLoginIdAndDomain());
				
				replacedCards.add(product.getCardNo());
				if(isGenerateNewCard){
					issueProducts.add(newProduct);
				}
				
			} catch (Exception e){
				errorCards.add(product.getCardNo() + " (" + e.getMessage() + ")");
				logger.debug("Error on replaced card: " + product.getCardNo());
		  		LoggerUtil.printStackTrace(logger, e);
			}
			
		}
		
		
		if(!replacedCards.isEmpty()){
			
			String msg = "Following Product(s) Replaced: " + Joiner.on(", ").join(replacedCards);
			Messagebox.show(msg, "Card Replacement", Messagebox.OK, Messagebox.INFORMATION);
		}
		
		if(!errorCards.isEmpty()){
			
			String msg = "Error on replacing card no(s):  " + Joiner.on(", ").join(errorCards);
			Messagebox.show(msg, "Card Replacement", Messagebox.OK, Messagebox.INFORMATION);
		}
		
			
		
			
		if(!issueProducts.isEmpty()){
			/* *****************************************
        	 * Populating Card Info Map to display later 
        	 * to inform user that card replaced
        	 * ****************************************/
			Map<String,Map<String,String>> feedbackMap = new LinkedHashMap<String,Map<String,String>>();
			Map<String,String> feedbackDataMap = new LinkedHashMap<String,String>();
			
			for(PmtbProduct issueProduct: issueProducts){
			
	        	feedbackDataMap.put(issueProduct.getCardNo(), issueProduct.getCardNo());
				if(issueProduct.getExpiryDate()!=null){
					feedbackDataMap.put("expiry"+issueProduct.getCardNo(),DateUtil.convertDateToStr( issueProduct.getExpiryDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				}
				feedbackDataMap.put("issue"+issueProduct.getCardNo(), DateUtil.convertDateToStr(issueProduct.getIssueDate(),DateUtil.GLOBAL_DATE_FORMAT));
				feedbackMap.put(issueProduct.getCardNo(),feedbackDataMap);
			}
			
			if(feedbackMap!=null && !feedbackMap.isEmpty()){
    			this.forward(Uri.VIEW_ISSUED_PRODUCTS_REPLACED_NEW,feedbackMap);
    		}
		}
		else {
			this.back();
		}

	
	}
	
	
	public void cancel() throws InterruptedException {
		this.back();
	}

	
		

}