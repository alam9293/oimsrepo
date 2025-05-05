package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductRenew;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
@SuppressWarnings("unchecked")
public class RenewProductWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 875116398490972964L;
	private static Logger logger = Logger.getLogger(RenewProductWindow.class);
	private Set<BigDecimal> productIdSet = new HashSet ();
	private String validityPeriod;
	
	public RenewProductWindow(){
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
		validityPeriod = params.get("validityPeriod");
	 	for(String productTypeid : params.keySet()){
			if(productTypeid.indexOf("productId")>=0)
				productIdSet.add(new BigDecimal(params.get(productTypeid)));
		}
	}

	public void afterCompose() {
		if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM)){
			((Datebox)this.getFellow("newexpiryDate")).setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			((Label)this.getFellow("expiryTimeLabel")).setVisible(true);
			((Hbox)this.getFellow("expiryTimeValueHbox")).setVisible(true);
		}
	}
	
	//Renew Description List
	public List<Listitem> getDurationTypeList(){
		ArrayList<Listitem> descriptionTypeList = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE.keySet()){
			descriptionTypeList.add(new Listitem(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE.get(key), key));
		}
		return descriptionTypeList;	
	}
	
	// Duration Length List
	public List<Listitem> getDurationLengthList(){
		ArrayList<Listitem> descriptionTypeList = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.PRODUCT_RENEW_DURATION_LENGTH.keySet()){
			descriptionTypeList.add(new Listitem(NonConfigurableConstants.PRODUCT_RENEW_DURATION_LENGTH.get(key), key));
		}
	return descriptionTypeList;
	}
	
	public void checkDurationType(){
		Listbox durationLengthList = (Listbox)this.getFellow("durationLengthList");
		Datebox expiry=(Datebox)this.getFellow("newexpiryDate");
		Listbox expiryHourLB=(Listbox) this.getFellow("expiryHourLB");
		Listbox expiryMinLB=(Listbox) this.getFellow("expiryMinLB");
		Listbox durationTypeList=(Listbox) this.getFellow("durationTypeList");
		
		String durationType=(String)durationTypeList.getSelectedItem().getValue();
		if(durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
			durationLengthList.setDisabled(false);
			expiry.setDisabled(true);
			expiryHourLB.setDisabled(true);
			expiryMinLB.setDisabled(true);
		}
		else{
			durationLengthList.setDisabled(true);
			expiry.setDisabled(false);
			expiryHourLB.setDisabled(false);
			expiryMinLB.setDisabled(false);
		}
	}
	
	public void populateData(){
			
		//Start Code
		int count=0;
//		int countItem=1; //To retrive the product 1000 at a time. Hibernate select something in(?) cannot provide  more than 1000
//		int maxCount=productIdSet.size();
//		int countId=1;
//		//Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		Map<String, Map<String, String>> dataMap=new HashMap<String, Map<String, String>>();
//		Map<String, Map<String, String>> tempdataMap=new HashMap<String, Map<String, String>>();;
//		Set<BigDecimal> productIdSetTemp = new HashSet<BigDecimal> ();
//		
//		if(productIdSet.size()>1000){
//			Set<BigDecimal> productIdSetAll = new HashSet<BigDecimal> ();
//			Iterator<BigDecimal> it = productIdSet.iterator();
//			while (it.hasNext()) {
//				productIdSetAll.add((BigDecimal)it.next());
//			}
//			while(countItem*1000<=maxCount){
//							
//				it = productIdSetAll.iterator();
//				while (it.hasNext()) {
//			        // Get element
//					productIdSetTemp.add((BigDecimal)(it.next()));
//			    	it.remove();
//					if(countId==1000) break;
//			    	countId++;
//			    }
//				countId=1;
//				countItem++;
//				if(productIdSetTemp.size()>0){
//					tempdataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetTemp);
//					dataMap.putAll(tempdataMap);
//				}
//				productIdSetTemp = new HashSet<BigDecimal> ();
//			}	
//		}else
			dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		
		//Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		
		resultListBox.getItems().clear();
		if(dataMap!=null){
			try{
			   //int count=0;
				for(String produtid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();
					item.setValue(produtid);
					Map<String,String> productDetails=dataMap.get(produtid);
					//item.appendChild(new Listcell(String.valueOf(count)));
					//item.appendChild(new Listcell(productDetails.get("name")));
					item.appendChild(newListcell(productDetails.get("parentAccountNo")));
					item.appendChild(newListcell(productDetails.get("accountName")));
					item.appendChild(newListcell(productDetails.get("productType")));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					//item.appendChild(newListcell(productDetails.get("status")));
					item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("issueDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
					
					if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM))
						item.appendChild(newListcell(productDetails.get("expiryDateUpToDay") +" "+ productDetails.get("expiryTime")));
					else
						item.appendChild(newListcell(productDetails.get("expiryDate")));
					
					item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("suspendDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){e.printStackTrace();}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
	
	public void renew(){
		
		PmtbProductRenew productRenew=new PmtbProductRenew();
		Listbox durationTypeList=(Listbox) this.getFellow("durationTypeList");
		String durationType=(String)durationTypeList.getSelectedItem().getValue();
		Textbox remarks=(Textbox) this.getFellow("remarks");
		String renewRemarks=(String)remarks.getValue();
		
		Date currentDate=new Date();
		Datebox newExpiry=null;
		Date newExpiryDate=null;
		String durationLength="";
		Timestamp newExpiryTime=null;
		
		int months=0;
		if(durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
			Listbox durationLengthList=(Listbox) this.getFellow("durationLengthList");
			durationLength=(String)durationLengthList.getSelectedItem().getValue();
			try{
				months=Integer.parseInt(durationLength);
				//currentDate=DateUtil.getLastUtilDateOfMonth(currentDate);
				newExpiryDate=DateUtil.getLastUtilDateOfMonth(currentDate);
				//newExpiryDate=DateUtil.addMonthsToDate(months, currentDate);
			}catch(NumberFormatException nfe){nfe.printStackTrace();}
		}
		else{
			newExpiry=(Datebox)this.getFellow("newexpiryDate");
			newExpiryDate=(Date)newExpiry.getValue();
			
			if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY))
				newExpiryDate=DateUtil.getLastUtilDateOfMonth(newExpiryDate);
		}
		
		try{
			//if(durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
			if(newExpiryDate==null) Messagebox.show("New Expiry Date should not be blank or null.","Error", Messagebox.OK, Messagebox.ERROR);	
			//newExpiryDate.after(currentDate) ||
			else {
			//	if(durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
			
				if(renewRemarks==null ||renewRemarks.trim().length()<1){
						Messagebox.show("Remarks field should not be blank","Error", Messagebox.OK, Messagebox.ERROR);
				}
				else{
					productRenew.setRemarks(renewRemarks);
					productRenew.setRenewDate(new java.sql.Date (currentDate.getTime()));
					productRenew.setDurationType(durationType);
					Date checkValidExpiryDate=this.businessHelper.getProductBusiness().getValidExpiryDate((HashSet) productIdSet);
					boolean checkDate=true;
					if(!durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
						if(newExpiryDate.compareTo(checkValidExpiryDate) <= 0){
							checkDate=false;
							Messagebox.show("New Expriy Date cannot not be equal or earlier than the Current Expiry Date.","Error", Messagebox.OK, Messagebox.ERROR);
							//+DateUtil.convertUtilDateToStr(checkValidExpiryDate, DateUtil.GLOBAL_DATE_FORMAT)
						}
					}
					
					if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM)){
						Integer hour = new Integer(((Listbox)this.getFellow("expiryHourLB")).getSelectedItem().getLabel());
						Integer min = new Integer(((Listbox)this.getFellow("expiryMinLB")).getSelectedItem().getLabel());
						Calendar expiryCalendar = Calendar.getInstance();
						expiryCalendar.setTime(newExpiryDate);
						expiryCalendar.set(Calendar.HOUR_OF_DAY, hour);
						expiryCalendar.set(Calendar.MINUTE, min);
						expiryCalendar.set(Calendar.SECOND, 0);
						expiryCalendar.set(Calendar.MILLISECOND, 0);
						newExpiryTime = new Timestamp(expiryCalendar.getTimeInMillis());
					}
					
					if(checkDate)
					{
						PmtbProduct product=new PmtbProduct();
						Iterator<BigDecimal> It = productIdSet.iterator();
						int confirmMessage=Messagebox.show( "Are you sure to renew?","Renew Product Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
						if(confirmMessage==1){
							//Processing Indicator
							displayProcessing();
							while (It.hasNext()) {
								productRenew.setDurationLength("0");
							   	product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
							   	if(durationType.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_RENEW_DURATION_TYPE_DURATION)){
									productRenew.setDurationLength(durationLength);
									newExpiryDate=DateUtil.addMonthsToDate(months, (Date)product.getExpiryDate());
									newExpiryDate = DateUtil.getLastUtilDateOfMonth(newExpiryDate);
									if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM)){
										Calendar expiryCalendar = Calendar.getInstance();
										expiryCalendar.setTimeInMillis(product.getExpiryTime().getTime());
										expiryCalendar.add(Calendar.MONTH, months);
									
										int lastDate = expiryCalendar.getActualMaximum(Calendar.DATE);
										expiryCalendar.set(Calendar.DATE, lastDate);
										
										newExpiryTime = new Timestamp(expiryCalendar.getTimeInMillis());
									}
								}
							   	
							   	//creditLimit.setPmtbProduct(product);
							   	try{
							   		productRenew.setNewExpDate(new java.sql.Date(newExpiryDate.getTime()));
							   		productRenew.setPmtbProduct(product);
							   		productRenew.setCurrentExpDate(product.getExpiryDate());
							   		
							   		if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM)){
							   			productRenew.setCurrentExpTime(product.getExpiryTime());
							   			productRenew.setNewExpTime(newExpiryTime);
							   		}
							   		
							   		boolean checkSavingReplaceProduct=this.businessHelper.getGenericBusiness().save(productRenew,getUserLoginIdAndDomain())!=null ? true : false;
							   		if(!checkSavingReplaceProduct)	
							   			Messagebox.show("Card No :"+ product.getCardNo() +" cannot be renewed.", "Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
							   		try{
							   			product.setExpiryDate(new java.sql.Date(newExpiryDate.getTime()));
							   			if(validityPeriod.equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM))
							   				product.setExpiryTime(newExpiryTime);
							   			product.setSmsExpirySent(NonConfigurableConstants.BOOLEAN_NO);
							       		this.businessHelper.getGenericBusiness().update(product,getUserLoginIdAndDomain());
							       		this.businessHelper.getProductBusiness().updateProductAPIActive(product, getUserLoginIdAndDomain());
							       		//TODO need to interface to AS that product's expiry date had been changed.
							       		
							       		//send virtual email		
							       		if(product.getPmtbProductType().getVirtualProduct().equalsIgnoreCase("Y"))
							       			this.businessHelper.getProductBusiness().renewVirtualEmail(product, currentDate);
							       	}
							   		catch(Exception exp){ 
							   			exp.printStackTrace();
							   			Messagebox.show("Expiry Date of Card No :"+ product.getCardNo() +" cannot be updated.", "Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
							   		}
							   	}
							   	catch(Exception e){ 
							   		e.printStackTrace();
							   	}
							}
							Messagebox.show("Product(s) Renewed.","Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
							this.back();
						}
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	
	public void cancel() throws InterruptedException {
		this.back();
	}

}


