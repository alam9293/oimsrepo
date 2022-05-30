package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
	public class TerminateProductWindow extends CommonWindow {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TerminateProductWindow.class);
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal> ();
	@SuppressWarnings("unchecked")
	public TerminateProductWindow(){
		HashMap<String,String> params = (HashMap<String,String>)Executions.getCurrent().getArg();
	  	for(String productId : params.keySet()){
			if(productId.indexOf("productId")>=0)
			productIdSet.add(new BigDecimal(params.get(productId)));
	  	}
	}
	
	public String getCurrentDate(){
		return DateUtil.getStrCurrentDate();
	}
	
	// getting Termination reason from Master Table , Configurable Constants
	public List<Listitem> getTerminateReasons(){ 
		List<Listitem> reasonList = new ArrayList<Listitem>();
		Map<String, String> masterTerminateResasons= ConfigurableConstants.getProductTerminateReasons();
		for(String masterCode : masterTerminateResasons.keySet()){
			reasonList.add(new Listitem(masterTerminateResasons.get(masterCode), masterCode));
		}
		return reasonList;
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
		try{
		   //int count=0;
			for(String productid : dataMap.keySet()){
				count++;
				Listitem item = new Listitem();		
				item.setValue(productid);
				Map<String,String> productDetails=dataMap.get(productid);
				//item.appendChild(new Listcell(String.valueOf(count)));
				//item.appendChild(new Listcell(productDetails.get("name")));
				item.appendChild(newListcell(productDetails.get("parentAccountNo")));
				item.appendChild(newListcell(productDetails.get("accountName")));
				item.appendChild(newListcell(productDetails.get("productType")));
				item.appendChild(newListcell(productDetails.get("cardNo")));
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
	@Override
	public void refresh() throws InterruptedException {
	}

	public void terminate() throws InterruptedException{
		logger.info("terminate()");
		// rewrote by Yiming 20101102
		Date terminateDate = ((Datebox)this.getFellow("terminateDate")).getValue();
		if(terminateDate==null){
			Messagebox.show("Termination date is a mandatory field", "Product Termination", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(terminateDate.before(new Date()) && DateUtil.isToday(terminateDate)!=true){
			Messagebox.show("Termination Date should not be earlier than today.", "Product Termination", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String terminateRemarks = ((Textbox)this.getFellow("remarks")).getValue();
		if(terminateRemarks==null || terminateRemarks.length()==0){
			Messagebox.show("Remarks field should not be blank", "Product Termination", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(this.businessHelper.getProductBusiness().hasFutureTermination(productIdSet)){
			if(Messagebox.show("One or more of the selected products has future termination. Overwrite?","Product Termination ", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.CANCEL){
				return;
			}
		}
		if(this.businessHelper.getProductBusiness().hasTerminated(productIdSet)){
			Messagebox.show("One or more of the selected products is used, terminated, or recycled.","Product Termination ", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(DateUtil.isToday(terminateDate)){
			terminateDate = new Date();
		}
		String terminateReason = ((Listbox)this.getFellow("terminateReasonList")).getSelectedItem().getValue().toString();
		this.businessHelper.getProductBusiness().terminateProducts(productIdSet, terminateDate, terminateRemarks, terminateReason, getUserLoginIdAndDomain());
		Messagebox.show("Product(s) Terminated.", "Product Termination", Messagebox.OK, Messagebox.INFORMATION);
		this.back();
//		
//		PmtbProductStatus productStatus=new PmtbProductStatus();
//		Textbox remarks=(Textbox) this.getFellow("remarks");
//		String terminateRemarks=(String)remarks.getValue();
//		Date currentDate=new Date();
//		Datebox terminateDate=(Datebox)this.getFellow("terminateDate");
//		Date terminationDate=(Date)terminateDate.getValue();
//		Listbox terminateReasonList=(Listbox) this.getFellow("terminateReasonList");
//		String  terminateReason=(String)terminateReasonList.getSelectedItem().getValue();
//		boolean datecheck=true;		
//		boolean isFutureTermination=false;
//		try{
//			if(terminationDate!=null){
//				if(terminateRemarks==null || terminateRemarks.trim().length()<1){
//					Messagebox.show("Remarks field should not be blank","Error", Messagebox.OK, Messagebox.ERROR);
//				}
//				else{
//					
//					Iterator<BigDecimal> It = productIdSet.iterator();
//					PmtbProduct product=new PmtbProduct();
//					productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON,terminateReason));
//					productStatus.setStatusRemarks(terminateRemarks);
//				
//					boolean todayCheck=DateUtil.isToday(terminationDate);
//					List<PmtbProductStatus> futureTerminateStatusList = new ArrayList<PmtbProductStatus>();
//					List<PmtbProduct> productList = new ArrayList<PmtbProduct>();
//					PmtbProductStatus futureTerminateStatus=new PmtbProductStatus();
//					if(terminationDate.before(currentDate) && todayCheck!=true){
//						Messagebox.show("Termination Date should not be earlier than today.","Error", Messagebox.OK, Messagebox.ERROR);
//						datecheck=false;
//					}	
//				
//					if(datecheck==true){
//					int confirmOverwrite=Messagebox.CANCEL;
//					int confirmMessage=Messagebox.show( "Are you sure to terminate ?","Terminate Product Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
//					if(confirmMessage==1){
//						//Processing Indicator
//						displayProcessing();
//						while (It.hasNext())  {
//						
//							
//							productStatus.setStatusDt(new Timestamp (DateUtil.isToday(terminationDate) ? new Date().getTime() : terminationDate.getTime()));
//							//All the active cards will be selected so status from should be ACTIVE to SUSPEND
//							productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//							//productStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//							//productStatus.setStatusFrom(product.getCurStatus());
//							product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
//							
//							// Check if current status = used, do not allow termination
//							if (NonConfigurableConstants.PRODUCT_STATUS_USED.equalsIgnoreCase(product.getCurrentStatus()))
//							{
//								Messagebox.show("Unable to terminate the product as it is being used already.","Error", Messagebox.OK, Messagebox.ERROR);
//								return;
//							}
//							
//							if (NonConfigurableConstants.PRODUCT_STATUS_TERMINATED.equalsIgnoreCase(product.getCurrentStatus()))
//							{
//								Messagebox.show("The product has already being terminated","Error", Messagebox.OK, Messagebox.ERROR);
//								return;
//							}
//							
//							isFutureTermination=this.businessHelper.getProductBusiness().isFutureTermination(product,currentDate);
//							
//							if(isFutureTermination){
//								confirmOverwrite=Messagebox.show( "This product has future termination, do you want to overwrite?","Terminate Product Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
//							}
//							if(confirmOverwrite==Messagebox.OK || isFutureTermination==false){
//								//Messagebox.show("future termination :"+ isFutureTermination, "Product Termination", Messagebox.OK, Messagebox.INFORMATION);
//								  
//								productStatus.setPmtbProduct(product);
//								if(product.getCurrentStatus()!=null)
//									productStatus.setStatusFrom(product.getCurrentStatus());
//								else
//									productStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//								
//								if(isFutureTermination==false){
//									try{
//										this.businessHelper.getGenericBusiness().save(productStatus,this.getUserLoginId());
//									}catch(Exception exp){ 
//										Messagebox.show("Termination Status of the Card No :"+ product.getCardNo() +" cannot be created.", "Product Termination", Messagebox.OK, Messagebox.INFORMATION);
//									}
//								}  
//								else if(confirmOverwrite==Messagebox.OK ){
//									try{
//										futureTerminateStatus=this.businessHelper.getProductBusiness().getFutureTermination(product,currentDate);
//										futureTerminateStatus.setStatusFrom(productStatus.getStatusFrom());
//										futureTerminateStatus.setStatusRemarks(productStatus.getStatusRemarks());
//										futureTerminateStatus.setStatusDt(productStatus.getStatusDt());
//										futureTerminateStatus.setMstbMasterTable(productStatus.getMstbMasterTable());
//										//this.businessHelper.getGenericBusiness().update(futureTerminateStatus,this.getUserLoginId());
//										futureTerminateStatusList.add(futureTerminateStatus);
//									}catch(Exception exp){
//										Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
//												"Error", Messagebox.OK, Messagebox.ERROR);
//										//Messagebox.show("Terminatiion Status of the Card No :"+ product.getCardNo() +" cannot be created.", "Product Termination", Messagebox.OK, Messagebox.INFORMATION);
//									}
//								}  
//								//start
//								if(todayCheck){
//									//try{
//								  	//		this.businessHelper.getProductBusiness().UpdateFutureSchedules(product,this.getUserLoginId());
//								  	//		
//								   	//}catch(Exception exp){ 
//							        //		exp.printStackTrace();
//							        		//logger.error("New Expiry date of the card cannot updated into product table.");
//							        //	}
//							   		product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//								}
//									try {
//
//										// this.businessHelper.getGenericBusiness().update(product,this.getUserLoginId());
//										productList.add(product);
//										// interface to AS
//										// User confirmed that they will select
//										// the correct reason so that the
//										// interface can send the corresponding
//										// reason
//										// to AS. There is one particular reason
//										// that will send as C instead of the
//										// normal D for the interface mapping
//										// in the master table.
//										// if
//										// (NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(product.getPmtbProductType().getOneTimeUsage()))
//										// this.businessHelper.getProductBusiness().updateProductAPI(product,
//										// this.getUserLoginId(),ConfigurableConstants.getAllMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON,NonConfigurableConstants.AS_TERMINATED_EVOUCHER_REASON_CODE)
//										// );
//										// else
//										// this.businessHelper.getProductBusiness().updateProductAPI(product,
//										// this.getUserLoginId(),ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON,terminateReason)
//										// );
//										this.businessHelper
//												.getProductBusiness()
//												.terminateProducts(
//														productList,
//														futureTerminateStatusList,
//														this.getUserLoginId(),
//														terminateReason);
//									}
//					        		catch(Exception exp){ 
//						        		exp.printStackTrace();
//						        		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
//						    					"Error", Messagebox.OK, Messagebox.ERROR);
//						        		//logger.error("New Expiry date of the card cannot updated into product table.");
//						        	}
//								}
//								   	//end
//						}
//						
//						// Call business to update the records
//						
//						
//						//	Messagebox.show("Product(s) Terminated.", "Product Termination", Messagebox.OK, Messagebox.INFORMATION);
//							this.back();
//						}
//					}
//				 }
//			}
//			else{
//				Messagebox.show("Termination date is a mandatory field", "Product Termination", Messagebox.OK, Messagebox.ERROR);
//			}
//		}catch(Exception e){e.printStackTrace();}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
}


