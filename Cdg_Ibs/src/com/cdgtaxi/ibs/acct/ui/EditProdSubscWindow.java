package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
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
import com.cdgtaxi.ibs.util.ComponentUtil;

public class EditProdSubscWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditProdSubscWindow.class);
	private String custNo, productTypeId;
	private List<Listitem> productDiscounts = new ArrayList<Listitem>();
	private List<Listitem> rewards = new ArrayList<Listitem>();
	private List<Listitem> subscriptions = new ArrayList<Listitem>();
	private List<Listitem> issuances = new ArrayList<Listitem>();
	private List<Listitem> productTypesItems = new ArrayList<Listitem>();
	@SuppressWarnings("unchecked")
	public EditProdSubscWindow() throws InterruptedException{
		logger.info("EditProdSubscWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		productTypeId = map.get("productTypeId");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Product Subscription", Messagebox.OK, Messagebox.ERROR);
		}
		if(productTypeId==null || productTypeId.trim().length()==0){
			Messagebox.show("No product type found!", "Edit Product Subscription", Messagebox.OK, Messagebox.ERROR);
		}
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
		if(productTypeId==null || productTypeId.trim().length()==0){
			this.back();
			return;
		}
		Map<String, String> productType = this.businessHelper.getAccountBusiness().getProductSubscription(custNo, productTypeId);
		((Label)this.getFellow("productType")).setValue(productType.get("productType").toString());
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
		((Label)this.getFellow("replaceFee")).setValue(productType.get("replaceFee"));
		if(productType.get("rewards")!=null){
			Listbox rewardsList = (Listbox)this.getFellow("rewardsList");
			for(Object item : rewardsList.getItems()){
				Listitem listitem = (Listitem)item;
				if(listitem.getValue().toString().equals(productType.get("rewards"))){
					listitem.setSelected(true);
					break;
				}
			}
		}
		if(productType.get("productDiscount")!=null){
			Listbox prodDiscountList = (Listbox)this.getFellow("prodDiscountList");
			for(Object item : prodDiscountList.getItems()){
				Listitem listitem = (Listitem)item;
				if(listitem.getValue().toString().equals(productType.get("productDiscount"))){
					listitem.setSelected(true);
					break;
				}
			}
		}
		if(productType.get("subscriptionFee")!=null){
			Listbox subscriptionList = (Listbox)this.getFellow("subscriptionList");
			for(Object item : subscriptionList.getItems()){
				Listitem listitem = (Listitem)item;
				if(listitem.getValue().toString().equals(productType.get("subscriptionFee"))){
					listitem.setSelected(true);
					break;
				}
			}
		}
		if(productType.get("issuanceFee")!=null){
			Listbox issuanceList = (Listbox)this.getFellow("issuanceList");
			for(Object item : issuanceList.getItems()){
				Listitem listitem = (Listitem)item;
				if(listitem.getValue().toString().equals(productType.get("issuanceFee"))){
					listitem.setSelected(true);
					break;
				}
			}
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
	public List<Listitem> getIssuances(){
		return this.issuances;
	}
	public List<Listitem> getProductTypes(){
		return this.productTypesItems;
	}
	public void save() throws InterruptedException{
		if(Messagebox.show("Save Subscription?", "Edit Product Subscription", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			Integer productDiscountId, rewardId, subscriptionId, issuanceId;
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

			//check if got any pending approval
			if(this.businessHelper.getAccountBusiness().hasPendApproveSubscription(custNo, productTypeId)) {
				Messagebox.show("There is currently a subscription for this Product Type that is pending for approval.", "Edit Product Subscription", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
//			this.businessHelper.getAccountBusiness().updateProductSubscription(custNo, productTypeId, productDiscountId, rewardId, subscriptionId, issuanceId);
			this.businessHelper.getAccountBusiness().updateProductSubscriptionApproval(custNo, productTypeId, productDiscountId, rewardId, subscriptionId, issuanceId);
			Messagebox.show("Subscription sent for approval", "Edit Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
	}
}