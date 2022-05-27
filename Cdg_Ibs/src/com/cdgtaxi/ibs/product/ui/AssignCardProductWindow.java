package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.Constants;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
	
	public class AssignCardProductWindow extends CommonWindow {
	private static final long serialVersionUID = -5566490003238378622L;
	private static Logger logger = Logger.getLogger(AssignCardProductWindow.class);
	//private PmtbProduct product=new PmtbProduct();
	private String accountNo="";
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal>();
	@SuppressWarnings("unchecked")
	public AssignCardProductWindow(){
			
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
	   	for(String productTypeid : params.keySet()){
			if(productTypeid.indexOf("productId")>=0)
				productIdSet.add(new BigDecimal(params.get(productTypeid)));
			//logger.info("Size of the map :"+params.size());	logger.info("Key 	:"+productTypeid);logger.info("Value 	:"+params.get(productTypeid));	
			if(productTypeid.indexOf("productAccNo")>=0)
				accountNo=params.get(productTypeid);
		}

		//logger.info("Customer No is "+account.getCustNo());
	}

	public void populateData(){
		int count=0;
		
		Map<String, Map<String, String>> dataMap=new HashMap<String, Map<String, String>>();
		dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet, true);
		
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
					item.appendChild(newListcell(productDetails.get("productType")));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("name")));
					item.appendChild(newListcell(productDetails.get("status")));
					item.appendChild(newListcell(productDetails.get("assignDate")));
					item.appendChild(newListcell(productDetails.get("expiryDate")));
					resultListBox.appendChild(item);
				}
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


	public void assignCard(){
		
		Textbox remarks=(Textbox) this.getFellow("remarks");
		String assignRemarks=(String)remarks.getValue();
		String assignedTo = ((Textbox)this.getFellow("assignTo")).getValue();
		String assignHr = ((Listbox)this.getFellow("startTimeHrDDL")).getSelectedItem().getLabel();
		String assignMin = ((Listbox)this.getFellow("startTimeMinDDL")).getSelectedItem().getLabel();
	
		try{
			if(assignRemarks==null || assignRemarks.trim().length()<1){
				Messagebox.show("Remarks Field Cannot be blank.", "Assign Card Product", Messagebox.OK, Messagebox.ERROR);
			} if(assignedTo==null || assignedTo.trim().length()<1){
				Messagebox.show("Assigned To Cannot be blank.", "Assign Card Product", Messagebox.OK, Messagebox.ERROR);	
			}else{
				//logger.info("Account No"+accountNo);
				Datebox assignDatebox=(Datebox) this.getFellow("assignDate");
				Date assignDate=(Date)assignDatebox.getValue();

				Calendar assignCalendar = Calendar.getInstance();
				assignCalendar.setTime(assignDate);
				assignCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(assignHr));
				assignCalendar.set(Calendar.MINUTE, Integer.parseInt(assignMin));
				Date assignCalendarDate = assignCalendar.getTime();
				
				if(assignDate==null){
					Messagebox.show("Assign Date cannot be blank.", "Assign Card Product", Messagebox.OK, Messagebox.ERROR);
				}
				else {
					
					Iterator<BigDecimal> It = productIdSet.iterator();
					
					int confirmMessage=Messagebox.show("Are you sure to assign card?","Assign Card Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
					if(confirmMessage==1){
						//Processing Indicator
						displayProcessing();
						
						List<String> assignedCards = Lists.newArrayList();
						List<String> errorCards = Lists.newArrayList();
						List<String> errorCards2 = Lists.newArrayList();
						Multimap<String, IttbCpCustCardIssuance> assignCardMultiMap = ArrayListMultimap.create();
						
					    try{
					       while (It.hasNext()) {
					    	   PmtbProduct product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
					    	   List<IttbCpCustCardIssuance> checkErrorList = this.businessHelper.getProductBusiness().checkAssignCardDate(assignCalendarDate, product.getCardNo(), product.getProductNo());

					    	   if(product.getIssueDate().compareTo(assignCalendarDate) < 0)
					    	   {
						    	   if(checkErrorList.size() < 1)
						    	   {
						    		   IttbCpCustCardIssuance iccci = new IttbCpCustCardIssuance();
					    			   iccci.setCardNo(product.getCardNo());
					    			   iccci.setProductNo(product.getProductNo());
					    			   iccci.setProductTypeId(product.getPmtbProductType().getProductTypeId());
					    			   iccci.setIssuedTo(assignedTo);
					    			   iccci.setIssuedOn(new Timestamp(assignCalendarDate.getTime()));
					    			   iccci.setCreatedBy(CommonWindow.getUserLoginIdAndDomain());
					    			   iccci.setCreatedDt(new Timestamp(new Date().getTime()));
					    			   iccci.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
					    			   iccci.setUpdatedDt(new Timestamp(new Date().getTime()));
					    			   iccci.setRemarks(assignRemarks);
					    			   assignCardMultiMap.put(""+product.getCardNo(), iccci);
					    			   assignedCards.add(product.getCardNo());
						    	   }
						    	   else
						    	   {
						    		  errorCards.add("["+product.getCardNo() + " Assign To : "+checkErrorList.get(0).getIssuedTo() + "]");
						    	   }
					    	   }
					    	   else
					    		   errorCards2.add("["+product.getCardNo() + " Issue Date: "+product.getIssueDate()+"]");
					       }
					     
					      if(!errorCards.isEmpty() || !errorCards2.isEmpty()) {
								//toConfirm jheneffer on error msg
					    	  	String msg = "";
					    	  	
					    	  	if(!errorCards.isEmpty())
					    	  		msg += "Card(s) already assign to another person :  " + Joiner.on(", ").join(errorCards);
					    	  	if(!errorCards2.isEmpty())
					    	  		msg += "Card(s) Assign Date is earlier than Products Issue Date : " + Joiner.on(", ").join(errorCards2);
								Messagebox.show(msg, "Card Assigned", Messagebox.OK, Messagebox.INFORMATION);
							}
					       if(!assignedCards.isEmpty()){
								
								String msg = "Following Card(s) Assigned: " + Joiner.on(", ").join(assignedCards);
								Messagebox.show(msg, "Card Assigned", Messagebox.OK, Messagebox.INFORMATION);
								
								for(Entry<String, IttbCpCustCardIssuance> value : assignCardMultiMap.entries())	{
									this.businessHelper.getProductBusiness().updateAssignCards(value.getValue(), assignCalendarDate);
								}
							}
							
					       this.back();
					    }catch(Exception e){	
					    	try{
					    		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					        		"Error", Messagebox.OK, Messagebox.ERROR);
					    	}catch(Exception exp){exp.printStackTrace();}
							e.printStackTrace();}
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
		

}


