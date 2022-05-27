	package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Intbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
	
	@SuppressWarnings("unchecked")
	public class EditSpecificProductWindow extends CommonWindow implements AfterCompose {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(EditSpecificProductWindow.class);

	private Set<BigDecimal> productIdSet = new HashSet ();
	String productId="";
	PmtbProduct pmtbProduct = null;
	
	public EditSpecificProductWindow(){
		
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
		for(String productNo : params.keySet()){
	
			if(productNo.indexOf("productId")>=0){
				productIdSet.add(new BigDecimal(params.get(productNo)));
				productId=params.get(productNo);
			}
		}
	}
	
	public void populateData() throws InterruptedException{
		
		try{
			
			pmtbProduct = this.businessHelper.getProductBusiness().getProductById(new BigDecimal(productId));
			
			CapsTextbox cardHolderName = (CapsTextbox) this.getFellow("cardHolderName");
			CapsTextbox position = (CapsTextbox) this.getFellow("position");
			CapsTextbox telephone = (CapsTextbox) this.getFellow("telephone");
			Textbox email = (Textbox) this.getFellow("email");
			CapsTextbox employeeId = (CapsTextbox) this.getFellow("employeeId");
			CapsTextbox mobile = (CapsTextbox) this.getFellow("mobile");
			CapsTextbox nameOnCard = (CapsTextbox) this.getFellow("nameOnCard");
			Checkbox coporateCheckBox=(Checkbox)this.getFellow("coporateCheckBox");
			Listbox smsExpiryFlagListBox = (Listbox )this.getFellow("smsExpiryFlag");
			Listbox smsTopUpFlagListBox = (Listbox )this.getFellow("smsTopUpFlag");
			
			String cardTypeCheck="";
			Row positionRow = (Row)this.getFellow("positionRow");
			Row mobileRow = (Row)this.getFellow("mobileRow");
			Row employeeIdRow = (Row)this.getFellow("employeeIdRow");
			Row nameOnCardRow =(Row)this.getFellow("nameOnCardRow");
			Row cardHolderNameRow =(Row)this.getFellow("cardHolderNameRow");

			Row coporarteCheckRow=(Row)this.getFellow("coporateCheckRow");
			String accountCategory = pmtbProduct.getAmtbAccount().getAccountCategory();
			if(accountCategory.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT) ||
					accountCategory.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
				coporarteCheckRow.setVisible(false);
			else
				coporarteCheckRow.setVisible(true);
			
			// display name on card if product type's name on card properties = Y
			if (NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(pmtbProduct.getPmtbProductType().getNameOnProduct()))
			{
				nameOnCardRow.setVisible(true);
				nameOnCard.setValue(pmtbProduct.getNameOnProduct());
			}
			
			// Init corporate individual
			if (NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(pmtbProduct.getIsIndividualCard()))
			{
				coporateCheckBox.setChecked(true);
				positionRow.setVisible(true);
				mobileRow.setVisible(true);
				employeeIdRow.setVisible(true);
				position.setValue(pmtbProduct.getCardHolderTitle());
				telephone.setValue(pmtbProduct.getCardHolderTel());
				email.setValue(pmtbProduct.getCardHolderEmail());
				employeeId.setValue(pmtbProduct.getEmployeeId());
				mobile.setValue(pmtbProduct.getCardHolderMobile());
			}
			else
			{
				coporateCheckBox.setChecked(false);				
			}

			// Display card holder name/salutation is product type = CC
			cardTypeCheck = pmtbProduct.getPmtbProductType().getProductTypeId();
			if(cardTypeCheck.equalsIgnoreCase(NonConfigurableConstants.CORPORATE_CARD_ID))
			{	
				cardHolderNameRow.setVisible(true);
				cardHolderName.setValue(pmtbProduct.getCardHolderName());
				if (pmtbProduct.getCardHolderSalutation() != null && !"".equalsIgnoreCase(pmtbProduct.getCardHolderSalutation()))
				{
					List<Listitem> listitems = (List<Listitem>) this.getFellow("salutationList").getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getValue();
						if (tempValue.equals(pmtbProduct.getCardHolderSalutation()))
						{
							listitem.setSelected(true);
						}
					}
				}
			}
			else
			{
				// Remove constraint
				cardHolderName.setConstraint("");
			}
			
			
			for(Object smsExpiryItem : smsExpiryFlagListBox.getChildren()){
				if(((Listitem)smsExpiryItem).getValue().equals(pmtbProduct.getSmsExpiryFlag())){
					((Listitem)smsExpiryItem).setSelected(true);
					break;
				}
			}
			for(Object smsTopUpItem : smsTopUpFlagListBox.getChildren()){
				if(((Listitem)smsTopUpItem).getValue().equals(pmtbProduct.getSmsTopupFlag())){
					((Listitem)smsTopUpItem).setSelected(true);
					break;
				}
			}
			
			PmtbProductType pmtbProductType = pmtbProduct.getPmtbProductType();
			if(NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(pmtbProductType.getContactless())){
				((Row)this.getFellow("offlineCountRow")).setVisible(true);
				((Row)this.getFellow("offlineAmountAccumulativeRow")).setVisible(true);
				((Row)this.getFellow("offlineAmountPerTxnRow")).setVisible(true);
				((Row)this.getFellow("embossNameRow")).setVisible(true);
				
				Intbox offlineCount = (Intbox)this.getFellow("offlineCount");
				Decimalbox offlineAmountAccumulative = (Decimalbox)this.getFellow("offlineAmountAccumulative");
				Decimalbox offlineAmountPerTxn = (Decimalbox)this.getFellow("offlineAmountPerTxn");
				Checkbox embossName = (Checkbox)this.getFellow("embossNameOnCard");
				
				offlineCount.setValue(pmtbProduct.getOfflineCount());
				offlineAmountAccumulative.setValue(pmtbProduct.getOfflineAmount());
				offlineAmountPerTxn.setValue(pmtbProduct.getOfflineTxnAmount());
				if(NonConfigurableConstants.BOOLEAN_YES.equals(pmtbProduct.getEmbossNameOnCard()))
					embossName.setChecked(true);
				else
					embossName.setChecked(false);
			}
			else{
				((Row)this.getFellow("offlineCountRow")).setVisible(false);
				((Row)this.getFellow("offlineAmountAccumulativeRow")).setVisible(false);
				((Row)this.getFellow("offlineAmountPerTxnRow")).setVisible(false);
				((Row)this.getFellow("embossNameRow")).setVisible(false);
			}
			
			
		}
		catch (WrongValueException wve)
		{
			throw wve;
		}
		catch(Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}
	
	public void save() throws InterruptedException{
		try
		{
			CapsTextbox cardHolderName = (CapsTextbox) this.getFellow("cardHolderName");
			CapsTextbox position = (CapsTextbox) this.getFellow("position");
			CapsTextbox telephone = (CapsTextbox) this.getFellow("telephone");
			Textbox email = (Textbox) this.getFellow("email");
			CapsTextbox employeeId = (CapsTextbox) this.getFellow("employeeId");
			CapsTextbox mobile = (CapsTextbox) this.getFellow("mobile");
			CapsTextbox nameOnCard = (CapsTextbox) this.getFellow("nameOnCard");
			Checkbox coporateCheckBox=(Checkbox)this.getFellow("coporateCheckBox");
			Listbox smsExpiryFlagListBox = (Listbox )this.getFellow("smsExpiryFlag");
			Listbox smsTopUpFlagListBox = (Listbox )this.getFellow("smsTopUpFlag");
			
			Intbox offlineCount = (Intbox)this.getFellow("offlineCount");
			Decimalbox offlineAmountAccumulative = (Decimalbox)this.getFellow("offlineAmountAccumulative");
			Decimalbox offlineAmountPerTxn = (Decimalbox)this.getFellow("offlineAmountPerTxn");
			Checkbox embossNameOnCard = (Checkbox)this.getFellow("embossNameOnCard");
			
			if(nameOnCard.getParent().isVisible()){
				if(nameOnCard.getValue()==null || nameOnCard.getValue().length()==0)
					throw new WrongValueException(nameOnCard, "* Mandatory field");
			}
		
			if(coporateCheckBox.isChecked())
			{
				//check vital industry
				Boolean checkIndustry = this.businessHelper.getProductBusiness().getCheckProductIndustry(pmtbProduct, null);
		
				if(checkIndustry)
				{
					if(email.getValue().trim().equals("") || employeeId.getValue().trim().equals(""))
					{
						if (!ComponentUtil.confirmBox("Continue without <employee id>/<email address> ?", "Edit Product")) {
							return;
						}
					}
				}					
				
				pmtbProduct.setIsIndividualCard(NonConfigurableConstants.BOOLEAN_YES);
				pmtbProduct.setCardHolderEmail(email.getValue());
				pmtbProduct.setCardHolderMobile(mobile.getValue());
				pmtbProduct.setCardHolderTel(telephone.getValue());
				pmtbProduct.setCardHolderTitle(position.getValue());
				pmtbProduct.setEmployeeId(employeeId.getValue());
			}
			else
			{
				logger.info("");
				pmtbProduct.setIsIndividualCard(NonConfigurableConstants.BOOLEAN_NO);
				pmtbProduct.setCardHolderEmail(null);
				pmtbProduct.setCardHolderMobile(null);
				pmtbProduct.setCardHolderTel(null);
				pmtbProduct.setCardHolderTitle(null);
				pmtbProduct.setEmployeeId(null);
			}
			
			Listbox salutationList=(Listbox)this.getFellow("salutationList");
			pmtbProduct.setCardHolderSalutation((String)salutationList.getSelectedItem().getValue());
			pmtbProduct.setCardHolderName(cardHolderName.getValue());
			
			String oldNameOnProduct = pmtbProduct.getNameOnProduct();
			pmtbProduct.setNameOnProduct(nameOnCard.getValue());
			
			
			pmtbProduct.setSmsExpiryFlag((String)smsExpiryFlagListBox.getSelectedItem().getValue());
			pmtbProduct.setSmsTopupFlag((String)smsTopUpFlagListBox.getSelectedItem().getValue());
			
			PmtbProductType pmtbProductType = pmtbProduct.getPmtbProductType();
			if(NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(pmtbProductType.getContactless())){
				pmtbProduct.setOfflineCount(offlineCount.getValue());
				pmtbProduct.setOfflineAmount(offlineAmountAccumulative.getValue());
				pmtbProduct.setOfflineTxnAmount(offlineAmountPerTxn.getValue());
				String embossNameOnCardFlag = (embossNameOnCard.isChecked()) ? NonConfigurableConstants.BOOLEAN_YES : NonConfigurableConstants.BOOLEAN_NO;
				pmtbProduct.setEmbossNameOnCard(embossNameOnCardFlag);
			}
			else {
				pmtbProduct.setEmbossNameOnCard(NonConfigurableConstants.BOOLEAN_YES);
			}
			
			// Save the updates
			this.businessHelper.getProductBusiness().updateProduct(pmtbProduct, getUserLoginIdAndDomain(), oldNameOnProduct);
			// close the screen
			this.detach();
		}
		catch (WrongValueException wve)
		{
			throw wve;
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	// getting salutation
	public List<Listitem> getSalutations(){ 
	
		List<Listitem> salutationList = new ArrayList<Listitem>();
		salutationList.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterSalutation= ConfigurableConstants.getSalutations();
		for(String masterCode : masterSalutation.keySet()){
			salutationList.add(new Listitem(masterSalutation.get(masterCode), masterCode));
		}
		return salutationList;
	}
	
	public List<Listitem> getSMSFlagList() {
		List<Listitem> smsFlagList = new ArrayList<Listitem>();
		for(Entry<String, String> entry: NonConfigurableConstants.SMS_FLAG.entrySet()){
			smsFlagList.add(new Listitem(entry.getValue(), entry.getKey()));
		}
		return smsFlagList;
	}
	
	public void checkCorporate(){
		
		
		Row positionRow=(Row)this.getFellow("positionRow");
		Row mobileRow=(Row)this.getFellow("mobileRow");
		Row employeeIdRow=(Row)this.getFellow("employeeIdRow");
		Checkbox coporateCheckBox=(Checkbox)this.getFellow("coporateCheckBox");
		if(coporateCheckBox.isChecked()){
			mobileRow.setVisible(true);
			positionRow.setVisible(true);
			employeeIdRow.setVisible(true);
		}
		else{
			mobileRow.setVisible(false);
			positionRow.setVisible(false);
			employeeIdRow.setVisible(false);
		}
	
	}
	
	@Override
	public void refresh() throws InterruptedException {
		populateData();
		//((Textbox)this.getFellow("typeName")).setValue("");
	}

	public void afterCompose() {
	}
	
	public void cancel() throws InterruptedException {
		this.detach();
	}
}


