package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class AddProdSubscWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddProdSubscWindow.class);
	private String custNo;
	private List<Map<String, Object>> productTypes;
	private List<Listitem> productDiscounts = new ArrayList<Listitem>();
	private List<Listitem> rewards = new ArrayList<Listitem>();
	private List<Listitem> subscriptions = new ArrayList<Listitem>();
	private List<Listitem> issuances = new ArrayList<Listitem>();
	private List<Listitem> productTypesItems = new ArrayList<Listitem>();
	@SuppressWarnings("unchecked")
	public AddProdSubscWindow() throws InterruptedException{
		logger.info("AddProdSubscWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Add Product Subscription", Messagebox.OK, Messagebox.ERROR);
		}
		productTypes = this.businessHelper.getAccountBusiness().getUnsubscribedProductTypes(custNo);
		Map<Integer, String> productDiscountsMap = MasterSetup.getProductDiscountManager().getAllMasters();
		productDiscounts.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer productDiscountId : productDiscountsMap.keySet()){
			productDiscounts.add(new Listitem(productDiscountsMap.get(productDiscountId), productDiscountId));
		}
		Map<Integer, String> rewardsMap = MasterSetup.getRewardsManager().getAllMasters();
		rewards.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer rewardId : rewardsMap.keySet()){
			rewards.add(new Listitem(rewardsMap.get(rewardId), rewardId));
		}
		Map<Integer, String> subscriptionMap = MasterSetup.getSubscriptionManager().getAllMasters();
		subscriptions.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer subscriptionId : subscriptionMap.keySet()){
			subscriptions.add(new Listitem(subscriptionMap.get(subscriptionId), subscriptionId));
		}
		Map<Integer, String> issuanceMap = MasterSetup.getIssuanceManager().getAllMasters();
		issuances.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer issuanceId : issuanceMap.keySet()){
			issuances.add(new Listitem(issuanceMap.get(issuanceId), issuanceId));
		}
		for(Map<String, Object> productType : productTypes){
			productTypesItems.add(new Listitem((String)productType.get("productType"), productType.get("productTypeId")));
		}
	
		Collections.sort(productTypesItems, new Comparator<Listitem>() {
		     public int compare(final Listitem  object1, final Listitem object2) {
		         return (object1.getLabel().compareTo(object2.getLabel()));
		     }
		 });
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		if(productTypesItems.isEmpty()){
			Messagebox.show("This account has subscribed to all available product types!", "Add Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			Listbox prodTypeList = (Listbox)this.getFellow("prodTypeList");
			prodTypeList.setSelectedIndex(0);
			this.selectProductType(prodTypeList.getSelectedItem());
		}
	}
	public List<Listitem> getProductDiscounts(){
		return this.productDiscounts;
	}
	public List<Listitem> getRewards(){
		return this.rewards;
	}
	public List<Listitem> getSubscriptions(){
		return this.subscriptions;
	}
	public List<Listitem> getIssuance(){
		return this.issuances;
	}
	public List<Listitem> getProductTypes(){
		return this.productTypesItems;
	}
	public void selectProductType(Listitem selectedItem){
		String productTypeId = (String)selectedItem.getValue();
		for(Map<String, Object> productType : productTypes){
			if(productType.get("productTypeId").equals(productTypeId)){
				((Label)this.getFellow("digits")).setValue(productType.get("digits").toString());
				((Label)this.getFellow("bin")).setValue(productType.get("bin").toString());
				((Label)this.getFellow("subBin")).setValue(productType.get("subBin").toString());
				((Label)this.getFellow("issuable")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("issuable")));
				((Label)this.getFellow("nameOnCard")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("nameOnCard")));
				((Label)this.getFellow("batch")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("batch")));
				((Label)this.getFellow("issueType")).setValue(NonConfigurableConstants.ISSUE_TYPE.get(productType.get("issueType")));
				((Label)this.getFellow("fixedValue")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("fixedValue")));
				((Label)this.getFellow("creditLimit")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("creditLimit")));
				((Label)this.getFellow("luhn")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("luhn")));
				((Label)this.getFellow("defaultStatus")).setValue(NonConfigurableConstants.PRODUCT_STATUS.get(productType.get("defaultStatus")));
				((Label)this.getFellow("negativeFile")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("negativeFile")));
				((Label)this.getFellow("otu")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("otu")));
				((Label)this.getFellow("validity")).setValue(NonConfigurableConstants.BOOLEAN.get(productType.get("validity")));
				((Label)this.getFellow("defaultExpiry")).setValue(productType.get("defaultExpiry").toString());
				((Label)this.getFellow("replaceFee")).setValue(StringUtil.bigDecimalToString((BigDecimal)productType.get("replaceFee"), StringUtil.GLOBAL_DECIMAL_FORMAT));
				break;
			}
		}
	}

	public void addSubscription() throws InterruptedException, CniiInterfaceException{
		if(Messagebox.show("Add Subscription?", "Add Product Subscription", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			Integer productDiscountId, rewardId, subscriptionId, issuanceId;
			String productTypeId = (String)((Listbox)this.getFellow("prodTypeList")).getSelectedItem().getValue();
			Listitem productDiscount = ((Listbox)this.getFellow("prodDiscountList")).getSelectedItem();
			if(productDiscount.getValue() instanceof Integer){
				productDiscountId = (Integer)productDiscount.getValue();
			}else{
				productDiscountId = null;
			}
			Listitem rewards = ((Listbox)this.getFellow("rewardsList")).getSelectedItem();
			if(rewards.getValue() instanceof Integer){
				rewardId = (Integer)rewards.getValue();
			}else{  
				rewardId = null;
			}
			Listitem subscription = ((Listbox)this.getFellow("subscriptionList")).getSelectedItem();
			if(subscription.getValue() instanceof Integer){
				subscriptionId = (Integer)subscription.getValue();
			}else{
				subscriptionId = null;
			}
			Listitem issuance = ((Listbox)this.getFellow("issuanceList")).getSelectedItem();
			if(issuance.getValue() instanceof Integer){
				issuanceId = (Integer)issuance.getValue();
			}else{
				issuanceId = null;
			}
			// additional check for external cards.
			String negativeFile = ((Label)this.getFellow("negativeFile")).getValue();
			if(negativeFile.equals(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_YES))){
				logger.info("This is a external card. Doing additional checks now");
				if(this.businessHelper.getAccountBusiness().hasExternalCardSubscription(productTypeId)){
					Messagebox.show("Unable to subscribe product type. Account with external card subscription exists!", "Add Product Subscription", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			//check if got any pending approval
			if(this.businessHelper.getAccountBusiness().hasPendApproveSubscription(custNo, productTypeId)) {
				Messagebox.show("There is currently a subscription for this Product Type that is pending for approval.", "Add Product Subscription", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
//			this.businessHelper.getAccountBusiness().addProductSubscription(custNo, productTypeId, productDiscountId, rewardId, subscriptionId, issuanceId);
			this.businessHelper.getAccountBusiness().addProductSubscriptionApproval(custNo, productTypeId, productDiscountId, rewardId, subscriptionId, issuanceId);
			Messagebox.show("Adding Subscription sent for approval", "Add Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
	}
}