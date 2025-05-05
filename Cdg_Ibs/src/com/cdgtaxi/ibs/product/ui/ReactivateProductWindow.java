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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
public class ReactivateProductWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReactivateProductWindow.class);
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal> ();
	@SuppressWarnings("unchecked")
	public ReactivateProductWindow(){
		HashMap<String,String> params = (HashMap<String,String>)Executions.getCurrent().getArg();
		for(String productId : params.keySet()){
			if(productId.indexOf("productId")>=0)
			productIdSet.add(new BigDecimal(params.get(productId)));
		}
	}
	
	public void getCurrentDate(){
		Datebox reactivateDate=(Datebox)this.getFellow("reactivateDate");
		Date today=new Date();
		reactivateDate.setValue(today);
	}

	/**
	 *  getting Replacement reason from Master Table , Configurable Constants
	 * @return
	 */
	public List<Listitem> getReactivateReasons(){ 
		List<Listitem> reasonList = new ArrayList<Listitem>();
		Map<String, String> masterReactivateResasons= ConfigurableConstants.getProductReactivateReasons();
		for(String masterCode : masterReactivateResasons.keySet()){
			reasonList.add(new Listitem(masterReactivateResasons.get(masterCode), masterCode));
			logger.info("Master code"+masterCode);
		}
		return reasonList;
	}
	
	public void populateData(){
		
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
//					logger.info("Trace1");
//					tempdataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetTemp);
//					dataMap.putAll(tempdataMap);
//					logger.info("Trace1 Finished");
//				}
//				productIdSetTemp = new HashSet<BigDecimal> ();
//			}
//			if(productIdSetAll.size()!=0){
//				logger.info("Trace2");
//				tempdataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetAll);
//				dataMap.putAll(tempdataMap);
//				logger.info("DATA MAP SIZE"+dataMap.size());
//				logger.info("Trace2 Finished");
//			}
//		
//		}else
			dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		
		 logger.info("Data Map Size"+dataMap.size());
			
//		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		try{
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
		// TODO Auto-generated method stub
	}
	
	public void reactivate() throws InterruptedException{
		logger.info("reactivate");
		// extracting all inputs
		String reactivateReason = (String)((Listbox)this.getFellow("reactivateReasonList")).getSelectedItem().getValue();
		Date reactivateDate = ((Datebox)this.getFellow("reactivateDate")).getValue();
		String reactivateHr = ((Listbox)this.getFellow("startTimeHrDDL")).getSelectedItem().getLabel();
		String reactivateMin = ((Listbox)this.getFellow("startTimeMinDDL")).getSelectedItem().getLabel();
		String reactivateRemarks = ((Textbox)this.getFellow("remarks")).getValue();
		Calendar reactivateCalendar = null;
		reactivateCalendar = Calendar.getInstance();
		reactivateCalendar.setTime(reactivateDate);
		reactivateCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reactivateHr));
		reactivateCalendar.set(Calendar.MINUTE, Integer.parseInt(reactivateMin));
		List<BigDecimal> productIds = new ArrayList<BigDecimal>(productIdSet);
		// now checking for current date
		if(reactivateCalendar.before(Calendar.getInstance())) {
			// comment out to follow the standard way of doing. If use this method, users might ask to change for other screens e.g. create trip screen
//			if(Messagebox.show("The selected reactivation date is before current time. Use current time instead?", "Product Reactivation", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.OK){
//				reactivateCalendar = Calendar.getInstance();
//			} else {
//				return;
//			}
			Messagebox.show("The selected reactivation date is before current time!", "Product Reactivation", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if(this.businessHelper.getProductBusiness().isFutureTerminationByRange(new ArrayList<BigDecimal>(productIds), DateUtil.getCurrentUtilDate(), reactivateCalendar.getTime())){
			Messagebox.show("One/more products are terminated before the start of reactivation!", "Product Reactivation", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(this.businessHelper.getProductBusiness().hasStatus(new ArrayList<BigDecimal>(productIds), reactivateCalendar.getTime())){
			Messagebox.show("One/more products has status on reactivation date!", "Product Reactivation", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(Messagebox.show("Reactivate Products?", "Product Suspension", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			try {
				this.businessHelper.getProductBusiness().reactivateProduct(
						productIds,
						new Timestamp(reactivateCalendar.getTimeInMillis()),
						ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_REACTIVATE_REASON, reactivateReason),
						reactivateRemarks,
						getUserLoginIdAndDomain());
			} catch (Exception e) {
				Messagebox.show("Unable to reactivate product(s)! Please try again later", "Product Reactivation", Messagebox.OK, Messagebox.ERROR);
				logger.error("Error", e);
				e.printStackTrace();
			}
			Messagebox.show("Product(s) Reactivated.", "Product Reactivation", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		// changed to above for multiple suspension for evouchers
//		PmtbProductStatus productStatus=new PmtbProductStatus();
//		Textbox remarks=(Textbox) this.getFellow("remarks");
//		String reactivateRemarks=(String)remarks.getValue();
//		Date currentDate=new Date();
//		Datebox reactivateDate=(Datebox)this.getFellow("reactivateDate");
//		Date reactivationDate=(Date)reactivateDate.getValue();
//		boolean isToday=true;
//		Listbox reactivateReasonList=(Listbox) this.getFellow("reactivateReasonList");
//		String reactivateReason=(String)reactivateReasonList.getSelectedItem().getValue();
//		boolean datecheck=true;		
//		try{
//			if(reactivateDate!=null){
//				isToday=DateUtil.isToday(reactivationDate);
//				if(reactivateRemarks==null ||reactivateRemarks.trim().length()<1){
//					Messagebox.show("Remarks field should not be blank","Error", Messagebox.OK, Messagebox.ERROR);
//				}else{
//					Iterator<BigDecimal> It = productIdSet.iterator();
//					//logger.info("productIdSet"+productIdSet.size());
//					PmtbProduct product=new PmtbProduct();
//					productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_REACTIVATE_REASON,reactivateReason));
//					productStatus.setStatusRemarks(reactivateRemarks);
//					if(reactivationDate.before(currentDate) && isToday==false){
//						Messagebox.show("Reactivation Date should not be earlier than today.","Error", Messagebox.OK, Messagebox.ERROR);
//						datecheck=false;
//					}	
//					if(datecheck==true){
//						int confirmMessage=Messagebox.show( "Are you sure to reactivate ?","Reactivate Product Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
//						if(confirmMessage==Messagebox.OK){
//							//Processing Indicator
//							displayProcessing();
//							int testCount=1;
//							while (It.hasNext()){
//								productStatus.setStatusDt(new Timestamp (reactivationDate.getTime()));
//								//All the active cards will be selected so status from should be ACTIVE to SUSPEND
//								productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//								productStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//								product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
//								//logger.info("CHECK REACTIVATE"+testCount+""+product.getCardNo());
//								testCount++;
//								productStatus.setPmtbProduct(product);
//								PmtbProductStatus futureReactivate=(PmtbProductStatus)this.businessHelper.getProductBusiness().getFutureReactivateSchedule(product);
//								
//								if(isToday && futureReactivate ==null ){
//									boolean checkSavingProductStatus=this.businessHelper.getGenericBusiness().save(productStatus,this.getUserLoginId())!=null ? true : false;
//									if(!checkSavingProductStatus)	
//										Messagebox.show("Status of the Card No :"+ product.getCardNo() +" cannot be reactivated.", "Product Reactivation", Messagebox.OK, Messagebox.INFORMATION);
//								}
//								else if(isToday && futureReactivate !=null ){
//									
//									futureReactivate.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_REACTIVATE_REASON,reactivateReason));
//									futureReactivate.setStatusRemarks(reactivateRemarks);
//									futureReactivate.setStatusDt(new Timestamp (reactivationDate.getTime()));
//									futureReactivate.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//									futureReactivate.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//									futureReactivate.setPmtbProduct(product);
//									try{
//										this.businessHelper.getGenericBusiness().update(futureReactivate,this.getUserLoginId());
//									}
//									catch(Exception e){
//										Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
//												"Error", Messagebox.OK, Messagebox.ERROR);
//										e.printStackTrace();
//									}
//								}
//								
//								else if(isToday==false && futureReactivate!=null){
//									
//									futureReactivate.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_REACTIVATE_REASON,reactivateReason));
//									futureReactivate.setStatusRemarks(reactivateRemarks);
//									futureReactivate.setStatusDt(new Timestamp (reactivationDate.getTime()));
//									futureReactivate.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//									futureReactivate.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//									futureReactivate.setPmtbProduct(product);
//									try{
//										this.businessHelper.getGenericBusiness().update(futureReactivate,this.getUserLoginId());
//									}
//									catch(Exception e){
//										Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
//												"Error", Messagebox.OK, Messagebox.ERROR);
//										e.printStackTrace();
//									}
//								}
//								else if(isToday==false && futureReactivate==null){
//									boolean checkSavingProductStatus=this.businessHelper.getGenericBusiness().save(productStatus,this.getUserLoginId())!=null ? true : false;
//									if(!checkSavingProductStatus)	
//										Messagebox.show("Status of the Card No :"+ product.getCardNo() +" cannot be reactivated.", "Product Reactivation", Messagebox.OK, Messagebox.INFORMATION);
//								}
//								if(isToday){
//									
//									product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//									try{
//										this.businessHelper.getGenericBusiness().update(product,this.getUserLoginId());
//										this.businessHelper.getProductBusiness().updateProductAPIActive(product, this.getUserLoginId());
//										//TODO need to interface to AS that product had been active again. //
//									
//									}
//									catch(Exception e){
//										Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
//												"Error", Messagebox.OK, Messagebox.ERROR);
//										e.printStackTrace();
//									}
//								}
//							}
//							Messagebox.show("Product(s) Reactivated.", "Product Reactivation", Messagebox.OK, Messagebox.INFORMATION);
//							this.back();
//						}
//					}
//				 }
//			}else{
//				Messagebox.show("Reactivation date is a mandatory field", "Product Suspension", Messagebox.OK, Messagebox.INFORMATION);
//			}
//		}catch(Exception e){e.printStackTrace();}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
	
	public static void main(String[] args){
		Set<BigDecimal> productIdSetAll = new HashSet<BigDecimal> ();
			productIdSetAll.add(new BigDecimal(1234));
			System.out.println("SIZE"+productIdSetAll.size());	
			productIdSetAll.add(new BigDecimal(1234));
			System.out.println("SIZE"+productIdSetAll.size());	
			
			Iterator<BigDecimal>it = productIdSetAll.iterator();
			while (it.hasNext()) {
		        // Get element
				System.out.println("TEST"+it.next());
				it.remove();
			}
			System.out.println("SIZE"+productIdSetAll.size());	
	
	}
}