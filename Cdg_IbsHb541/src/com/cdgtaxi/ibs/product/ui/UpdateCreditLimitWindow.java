package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Decimalbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductCreditLimit;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
public class UpdateCreditLimitWindow extends CommonWindow {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateCreditLimitWindow.class);
	private String accountNo="";
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal> ();
	
	@SuppressWarnings("unchecked")
	public UpdateCreditLimitWindow(){
		HashMap<String,String> params = (HashMap<String,String>)Executions.getCurrent().getArg();
	   	for(String productTypeid : params.keySet()){
	   		if(productTypeid.indexOf("productId")>=0)
	   			productIdSet.add(new BigDecimal(params.get(productTypeid)));
		}
	}

	//Review Type List
	public List<Listitem> getReviewType(){
	
		ArrayList<Listitem> descriptionTypeList = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.REVIEW_TYPES.keySet()){
			//logger.info("@@@@@@@@@@@@@@@DURATION TYPE"+NonConfigurableConstants.REVIEW_TYPES.get(key));
			descriptionTypeList.add(new Listitem(NonConfigurableConstants.REVIEW_TYPES.get(key), key));
		}
	return descriptionTypeList;
	}
	
	public void reviewType(){
		
		Datebox effectiveToDatebox=(Datebox) this.getFellow("effectiveDateTo");
		Listbox reviewTypeList=(Listbox)this.getFellow("reviewTypeList");
		Label toDateLabel = (Label)this.getFellow("dateToLabel");
		String reviewType=(String)reviewTypeList.getSelectedItem().getValue();
		if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
			effectiveToDatebox.setDisabled(false);
			toDateLabel.setClass("fieldLabel required");
		}
		else
		{
			effectiveToDatebox.setDisabled(true);
			toDateLabel.setClass("fieldLabel");
		}
	}
	
	public void populateData(){
			
		int count=0;
//		int countItem=1; //To retrive the product 1000 at a time. Hibernate select something in(?) cannot provide  more than 1000
//		int maxCount=productIdSet.size();
//		int countId=1;
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
					
					// Remove the "," in the string for credit balance and credit limit
					if (productDetails.get("creditLimit") != null &&  !"".equals(productDetails.get("creditLimit")))
						item.appendChild(newListcell(new BigDecimal(productDetails.get("creditLimit").replaceAll(",", "")), StringUtil.GLOBAL_DECIMAL_FORMAT));
					else
					{
						Listcell tempCell = new Listcell();
						tempCell.setLabel("-");
						tempCell.setValue(new BigDecimal(0));
						item.appendChild(tempCell);
					}
					if (productDetails.get("creditBalance") != null &&  !"".equals(productDetails.get("creditBalance")))
						item.appendChild(newListcell(new BigDecimal(productDetails.get("creditBalance").replaceAll(",", "")), StringUtil.GLOBAL_DECIMAL_FORMAT));
					else
					{
						Listcell tempCell = new Listcell();
						tempCell.setLabel("-");
						tempCell.setValue(new BigDecimal(0));
						item.appendChild(tempCell);
					}
					item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("issueDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
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
	}
	
	public void update(){
		Textbox remarks=(Textbox) this.getFellow("remarks");
		String updateRemarks=(String)remarks.getValue();
		boolean isToday=false;
		//boolean check=false;
		Listbox reviewTypeList=(Listbox)this.getFellow("reviewTypeList");
		String reviewType=(String)reviewTypeList.getSelectedItem().getValue();
		try{
			if(updateRemarks==null || updateRemarks.trim().length()<1){
				Messagebox.show("Remarks Field Cannot be blank.", "Update Credit Limit ", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				logger.info("Retag Account No = "+accountNo);
				Datebox effectiveFromDatebox=(Datebox) this.getFellow("effectiveDateFrom");
				Datebox effectiveToDatebox=(Datebox) this.getFellow("effectiveDateTo");
				Date effectiveDateFrom=(Date)effectiveFromDatebox.getValue();
				Date effectiveDateTo=(Date)effectiveToDatebox.getValue();
				Decimalbox CreditLimit=(Decimalbox)this.getFellow("newCreditLimit");
				BigDecimal newCreditLimit=(BigDecimal) CreditLimit.getValue();
				//Neeed to find the possible credit limit for each account here.
				Date currentDate=DateUtil.getCurrentDate();
				if(effectiveDateFrom!=null){
					isToday=DateUtil.isToday(effectiveDateFrom);
					effectiveDateFrom=DateUtil.convertDateTo0000Hours(effectiveDateFrom);
					if(isToday)
						effectiveDateFrom=currentDate;
					if(effectiveDateTo!=null)
						effectiveDateTo=DateUtil.convertDateTo2359Hours(effectiveDateTo);
				}
				
				if(effectiveDateFrom==null)
					Messagebox.show("Effective Date From cannot be blank.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
				else if(effectiveDateFrom.before(currentDate) && isToday==false ){
					Messagebox.show("Effective Date From should not be earlier than Today.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
				}
				else if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY) && effectiveDateTo==null){
					Messagebox.show("Effective Date To should not be blank.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
				}
			//	else if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY) && (DateUtil.convertDateToStr(effectiveDateFrom, DateUtil.GLOBAL_DATE_FORMAT).equalsIgnoreCase(DateUtil.convertDateToStr(effectiveDateTo, DateUtil.GLOBAL_DATE_FORMAT)))){
			//		Messagebox.show("Effective Date From should not be equal to Effective Date To.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
			//	}
				else if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY) && effectiveDateTo.before(effectiveDateFrom)){
					Messagebox.show("Effective Date To should not be earlier than Effective Date From.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
				}
				else if(newCreditLimit==null){
					Messagebox.show("New Credit Limit should not be blank.", "Update Credit Limit", Messagebox.OK, Messagebox.ERROR);
					//newCreditLimit=new BigDecimal("0");
				}
				
//				else if(effectiveDateTo.after(effectiveDateFrom) && (reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY))){
//					Messagebox.show("Effective Date To should not be earlier than Today.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
//				}
//				else if(effectiveDateTo.before(effectiveDateFrom) || effectiveDateTo.compareTo(effectiveDateFrom)==0){
//					Messagebox.show("Effective Date To should not be earlier than  or equal to Effective Date From.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
//				}
				else{
					
					logger.info("reviewType = "+ reviewType);
					if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY) && effectiveDateTo==null){
						Messagebox.show("Effective Date To should not be blank.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
						logger.info("NonConfigurableConstants.REVIEW_TYPE_TEMPORARY"+NonConfigurableConstants.REVIEW_TYPE_TEMPORARY);
						return;
					}
					logger.info("Effective Start Date = "+effectiveDateFrom);
					logger.info("Effective End Date = "+ effectiveDateTo);
					logger.info("Remarks = "+updateRemarks);
					logger.info("New Credit Limit = "+newCreditLimit);
					logger.info("Review Type = "+reviewType);
					PmtbProductCreditLimit creditLimit=new PmtbProductCreditLimit();
					creditLimit.setCreditLimitType(reviewType);
					if(isToday)
						creditLimit.setEffectiveDtFrom(new Timestamp(currentDate.getTime()));
					else
						creditLimit.setEffectiveDtFrom(new Timestamp(effectiveDateFrom.getTime()));
					if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
						effectiveDateTo=DateUtil.convertDateTo2359Hours(effectiveDateTo);
						creditLimit.setEffectiveDtTo(new Timestamp(effectiveDateTo.getTime()));
					}	
					BigDecimal maxUpdatableCreditLimit=(BigDecimal) this.businessHelper.getProductBusiness().getProductMaxUpdatableCreditLimit((HashSet<BigDecimal>)productIdSet);
//					if(maxUpdatableCreditLimit==null){
//						logger.info("MAX UPDATABLE CREDIT LIMIT IS NULL");
//					}
					
					if(newCreditLimit.compareTo(maxUpdatableCreditLimit)<=0 || maxUpdatableCreditLimit==null){
						creditLimit.setNewCreditLimit(newCreditLimit);
						creditLimit.setRemarks(updateRemarks);
						boolean checkDuplicateSchedule=false;
						
						PmtbProduct product=new PmtbProduct();
						Iterator<BigDecimal> It = productIdSet.iterator();
						boolean overwriteExistingCL = false;
						boolean hasPendingCreditReview=false;
						int confirmMessage=Messagebox.show( "Update credit limit?","Update Credit Limit Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
						if(confirmMessage==1){
							//Processing Indicator
							displayProcessing();
							while (It.hasNext()) {
								product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
								hasPendingCreditReview=this.businessHelper.getProductBusiness().hasPendingCreditReview(product);
								if(hasPendingCreditReview){
									Messagebox.show("Cannot update for Card No."+product.getCardNo()+". Parent Account is suspended for Credit Review. ", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
								}else{
								
									checkDuplicateSchedule=this.businessHelper.getProductBusiness().isDuplicateScheduleCreditLimit(reviewType,effectiveDateFrom,product.getProductNo());
									if(checkDuplicateSchedule){
										Messagebox.show("Cannot schedule for Card No."+product.getCardNo()+".There had been a future schedule with the same effective date.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
										//return;
									}
									else{
										creditLimit.setPmtbProduct(product);
								        //effective date from is earlier than or equal to today but effective date to is future, then update the effective date to 
								        PmtbProductCreditLimit futureCreditLimitSchedule=(PmtbProductCreditLimit)this.businessHelper.getProductBusiness().getFutureCreditLimitScheduleHalfWay(product);
								        //both effective date  and effective date to are in the future, then update the whole records.
								        PmtbProductCreditLimit futureCreditLimitToUpdate=(PmtbProductCreditLimit)this.businessHelper.getProductBusiness().getFutureCreditLimitScheduleTotallyFuture(product);
								        if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_PERMANENT)){
								        	try{
									        	this.businessHelper.getGenericBusiness().save(creditLimit,getUserLoginIdAndDomain());
									        }catch(Exception e){
									        	Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
									        			"Error", Messagebox.OK, Messagebox.ERROR);
									        	e.printStackTrace();
									        }
								       	}
								        else{
								        	// this is for temp credit limit that is half way effective.
								           	if(futureCreditLimitSchedule!=null){
								           		if(Messagebox.show( "Existing Temporary Credit Limit. Overwrite?","Update Credit Limit Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION) == Messagebox.CANCEL){
								           			return;
								           		}
									       		try{
									       			// cancelling the temp credit limit
									       			futureCreditLimitSchedule.setEffectiveDtTo(new Timestamp(currentDate.getTime()));
									       			if(product.getCreditLimit().doubleValue() < product.getTempCreditLimit().doubleValue()){
									       				product.setCreditBalance(product.getCreditBalance().subtract(product.getTempCreditLimit().subtract(product.getCreditLimit())));
									       			}
									       			
									       			product.setTempCreditLimit(null);
									       			this.businessHelper.getGenericBusiness().update(futureCreditLimitSchedule,getUserLoginIdAndDomain());
									       			this.businessHelper.getGenericBusiness().update(product,getUserLoginIdAndDomain());
										       	}catch(Exception e){
										        	Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
										        		"Error", Messagebox.OK, Messagebox.ERROR);
										        	e.printStackTrace();
										        }
										        try{
										        	this.businessHelper.getGenericBusiness().save(creditLimit,getUserLoginIdAndDomain());
										        }
										        catch(Exception e){
										        	Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
										        			"Error", Messagebox.OK, Messagebox.ERROR);
										        e.printStackTrace();
										        }
									        } else if(futureCreditLimitToUpdate!=null) {
									        	if(!overwriteExistingCL){
									        		if(Messagebox.show( "Existing Temporary Credit Limit. Overwrite?","Update Credit Limit Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION) == Messagebox.CANCEL){
									           			return;
									           		}else{
									           			overwriteExistingCL = true;
									           		}
									        	}
									        			//futureCreditLimitSchedule.setCreditLimitType(reviewType);
									        			futureCreditLimitToUpdate.setEffectiveDtFrom(new Timestamp(effectiveDateFrom.getTime()));
														if(reviewType.equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY))
															futureCreditLimitToUpdate.setEffectiveDtTo(new Timestamp(effectiveDateTo.getTime()));
														futureCreditLimitToUpdate.setNewCreditLimit(newCreditLimit);
														futureCreditLimitToUpdate.setRemarks(updateRemarks);
													try{
														this.businessHelper.getGenericBusiness().update(futureCreditLimitToUpdate,getUserLoginIdAndDomain());
										        		
										        	}
										        	catch(Exception e){
										        		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
										        				"Error", Messagebox.OK, Messagebox.ERROR);
										        		e.printStackTrace();
										        	}
									        
									        	}
									        	else{
									        		try{
										        		this.businessHelper.getGenericBusiness().save(creditLimit,getUserLoginIdAndDomain());
										        		
										        	}
										        	catch(Exception e){
										        		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
										        				"Error", Messagebox.OK, Messagebox.ERROR);
										        		e.printStackTrace();
										        	}
									        	}
								        	}
								        	
								        	if(isToday){
								        		BigDecimal previousCreditLimit, futureCreditLimit;
								        		previousCreditLimit = product.getTempCreditLimit()!=null && product.getTempCreditLimit().doubleValue() > product.getCreditLimit().doubleValue() ? product.getTempCreditLimit() : product.getCreditLimit();
								        		futureCreditLimit = reviewType.equals(NonConfigurableConstants.REVIEW_TYPE_PERMANENT) ? (product.getTempCreditLimit()!=null && product.getTempCreditLimit().doubleValue() > newCreditLimit.doubleValue() ? product.getTempCreditLimit() : newCreditLimit) : (newCreditLimit.doubleValue() > product.getCreditLimit().doubleValue() ? newCreditLimit : product.getCreditLimit());
								        		
								        		BigDecimal newbalance=product.getCreditBalance();
								        		BigDecimal balanceToIncrease=futureCreditLimit.subtract(previousCreditLimit);
								        		logger.info("newbalance = "+newbalance);
								        		logger.info("newCreditLimit = "+newCreditLimit);
								        		logger.info("product.getCreditLimit() = "+product.getCreditLimit());
								        		logger.info("balanceToIncrease = "+balanceToIncrease);
								        		logger.info("newbalance = "+(newbalance.add(balanceToIncrease)));
								        		newbalance=(newbalance.add(balanceToIncrease));
								        		//product.setCreditBalance(product.getCreditBalance().add(newCreditLimit.subtract(product.getCreditLimit())));
								        	
								        		product.setCreditBalance(newbalance);
								        		if(reviewType.equals(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
								        			product.setTempCreditLimit(newCreditLimit);
								        		}else{
								        			product.setCreditLimit(newCreditLimit);
								        		}
								        		try{
								        			this.businessHelper.getGenericBusiness().update(product, getUserLoginIdAndDomain());
								        			if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE))
								        				this.businessHelper.getProductBusiness().updateProductAPIActive(product, getUserLoginIdAndDomain());
								        			else
								        			{
								        				// Need to retrieve the product current status and remarks
									    				   PmtbProductStatus latestProductStatus = this.businessHelper.getProductBusiness().getLatestProductStatus(product.getCardNo(), DateUtil.getCurrentTimestamp());
								        				this.businessHelper.getProductBusiness().updateProductAPI(product, getUserLoginIdAndDomain(), latestProductStatus.getMstbMasterTable());
								        			}
									        	
									        	}
									        	catch(Exception e){
									        		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
									        				"Error", Messagebox.OK, Messagebox.ERROR);
									        		e.printStackTrace();
									        	}
								        	}
								        }
									}
								
								}
								if(!checkDuplicateSchedule && !hasPendingCreditReview){
									Messagebox.show("Credit Limit updated.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
									this.back();
								}
								else{
									if(productIdSet.size()>1)
										Messagebox.show("Some cards cannot update credit limit.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
										// (Duplicate Schedule date)
								}
								
							}
						}
						else
							Messagebox.show("New Credit Limit cannot be greater than parent Credit Limit.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
					}
				}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
}


