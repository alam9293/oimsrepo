package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
	public class ViewProductHistoryWindow extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(ViewProductHistoryWindow.class);
	private String productNo="";
	private Listbox resultList;
	
	@SuppressWarnings("unchecked")
	public ViewProductHistoryWindow(){
		//
		
		HashMap<String,String> params = (HashMap<String,String>)Executions.getCurrent().getArg();
	  
		for(String productId : params.keySet()){
	
			if(productId.indexOf("productId")>=0){
				productNo=params.get(productId);
			}
		}
	}
	
	public String getCurrentDate(){
	
		return DateUtil.getStrCurrentDate();
		
	}
	
	public void Renew(){
		
		setVisibleFalseAll();
		logger.info("Start to populate Data");
		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getRenewProductHistory(new BigDecimal(productNo));
		Listbox resultListBox = (Listbox)this.getFellow("resultListRenew");
		resultListBox.setVisible(true);
		//Grid renewGrid=(Grid)this.getFellow("renewProductGrid");
		//renewGrid.setVisible(true);
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		if(dataMap!=null){
			resultListBox.getItems().clear();
			try{
			   int count=0;
				for(String productid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();		
					item.setValue(productid);
					Map<String,String> productDetails=dataMap.get(productid);
					//item.appendChild(new Listcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("renewDate")));
					item.appendChild(newListcell(productDetails.get("currentExpiryDate")));
					item.appendChild(newListcell(productDetails.get("newExpiryDate")));
					item.appendChild(newListcell(productDetails.get("remarks")));
					item.appendChild(newListcell(productDetails.get("createdBy")));
					item.appendChild(newListcell(productDetails.get("createdDate")));
					item.appendChild(newListcell(productDetails.get("updatedBy")));
					item.appendChild(newListcell(productDetails.get("updatedDate")));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();}
		}
	}
	
//	public void CardAssignment(){
//		setVisibleFalseAll();
//		logger.info("Start to populate Data");
//		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getCardAssignmentHistory(new BigDecimal(productNo));
//		Listbox resultListBox = (Listbox)this.getFellow("resultListCardAssignment");
//		resultListBox.setVisible(true);
//		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
//		if(dataMap!=null){
//			resultListBox.getItems().clear();
//			try{
//			   int count=0;
//				for(String issuanceNo : dataMap.keySet()){
//					count++;
//					Listitem item = new Listitem();		
//					item.setValue(issuanceNo);
//					Map<String,String> cardAssignmentHistoryDetails=dataMap.get(issuanceNo);
//					//item.appendChild(new Listcell(String.valueOf(count)));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("cardNo")));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("issuedTo")));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("issuedOn")));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("returnedOn")));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("updatedDt")));
//					item.appendChild(newListcell(cardAssignmentHistoryDetails.get("remarks")));
//					resultListBox.appendChild(item);
//				}
//				//To show the no record found message below the list
//				if(resultListBox.getListfoot()!=null && count>0)
//					resultListBox.removeChild(resultListBox.getListfoot());
//				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
//				resultListBox.setPageSize(10);
//			}catch(Exception e){
//				logger.info("Exception is here");
//				e.printStackTrace();}
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public void CardAssignment(){
		
		setVisibleFalseAll();
		logger.info("Start to populate Data");
		resultList = (Listbox) getFellow("resultListCardAssignment");
		
		// somehow this will fix null bug .. n still work 
		Listitem itemTemplate = null;
		try{
			itemTemplate = (Listitem) resultList.getItems().get(0);
			itemTemplate.detach();
		}catch(Exception e){} 
		
		resultList.setVisible(true);
		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getCardAssignmentHistory(new BigDecimal(productNo));
		
		if (dataMap == null) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
		} else {
			resultList.getItems().clear();
			try{
				int count=0;
				for (String issuanceNo : dataMap.keySet()) {
					count++;
					Listitem item = (Listitem) itemTemplate.clone();
					Map<String,String> cardAssignmentHistoryDetails=dataMap.get(issuanceNo);
					item.setValue(cardAssignmentHistoryDetails);
					List<Listcell> cells = item.getChildren();
					cells.get(0).setLabel(cardAssignmentHistoryDetails.get("cardNo"));
					cells.get(1).setLabel(cardAssignmentHistoryDetails.get("issuedTo"));
					cells.get(2).setLabel(cardAssignmentHistoryDetails.get("issuedOn"));
					cells.get(3).setLabel(cardAssignmentHistoryDetails.get("returnedOn"));
					cells.get(4).setLabel(cardAssignmentHistoryDetails.get("updatedBy"));
					cells.get(5).setLabel(cardAssignmentHistoryDetails.get("updatedDt"));
					cells.get(6).setLabel(cardAssignmentHistoryDetails.get("createdBy"));
					cells.get(7).setLabel(cardAssignmentHistoryDetails.get("createdDt"));
					cells.get(8).setLabel(cardAssignmentHistoryDetails.get("remarks"));
					resultList.appendChild(item);
				}
				if(resultList.getListfoot()!=null && count>0)
					resultList.removeChild(resultList.getListfoot());	
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();}
		}
	}
	
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
		
	public void setVisibleFalseAll(){
	
		Listbox retagResultListBox = (Listbox)this.getFellow("resultListRetag");
		retagResultListBox.setVisible(false);
		Listbox replaceResultListBox = (Listbox)this.getFellow("resultListReplace");
		replaceResultListBox.setVisible(false);
		Listbox renewResultListBox = (Listbox)this.getFellow("resultListRenew");
		renewResultListBox.setVisible(false);
		Listbox updateCreditLimitResultListBox = (Listbox)this.getFellow("resultListCreditLimit");
		updateCreditLimitResultListBox.setVisible(false);
		Listbox updateCardAssignmentResultListBox = (Listbox)this.getFellow("resultListCardAssignment");
		updateCardAssignmentResultListBox.setVisible(false);
	}
	
	public void Replace(){
		
		setVisibleFalseAll();
	   	Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getReplaceProductHistory(new BigDecimal(productNo));
		Listbox resultListBox = (Listbox)this.getFellow("resultListReplace");
		resultListBox.setVisible(true);
		//Grid renewGrid=(Grid)this.getFellow("replaceProductGrid");
		//renewGrid.setVisible(true);
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		if(dataMap!=null){
			resultListBox.getItems().clear();
			try{
			   int count=0;
				for(String productid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();		
					item.setValue(productid);
					Map<String,String> productDetails=dataMap.get(productid);
					//item.appendChild(newListcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get("oldcardNo")));
					item.appendChild(newListcell(productDetails.get("newcardNo")));
					item.appendChild(newListcell(productDetails.get("replacementDate")));
					item.appendChild(newListcell(productDetails.get("currentExpiryDate")));
					item.appendChild(newListcell(productDetails.get("newExpiryDate")));
					item.appendChild(newListcell(productDetails.get("charges")));
					item.appendChild(newListcell(productDetails.get("remarks")));
					item.appendChild(newListcell(productDetails.get("createdBy")));
					item.appendChild(newListcell(productDetails.get("createdDate")));
					item.appendChild(newListcell(productDetails.get("updatedBy")));
					item.appendChild(newListcell(productDetails.get("updatedDate")));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();}
		}
		
	}
	
	public void UpdateCreditLimit(){
		
		setVisibleFalseAll();
	   	Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getCreditLimitHistory(new BigDecimal(productNo));
		Listbox resultListBox = (Listbox)this.getFellow("resultListCreditLimit");
		resultListBox.setVisible(true);
		//Grid renewGrid=(Grid)this.getFellow("updateCreditLimtGrid");
		//renewGrid.setVisible(true);
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		if(dataMap!=null){
			resultListBox.getItems().clear();
			try{
			   int count=0;
				for(String productid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();		
					item.setValue(productid);
					Map<String,String> productDetails=dataMap.get(productid);
					//item.appendChild(newListcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("newCreditLimit")));
					item.appendChild(newListcell(productDetails.get("effectiveDateFrom")));
					item.appendChild(newListcell(productDetails.get("effectiveDateTo")));
					item.appendChild(newListcell(productDetails.get("remarks")));
					item.appendChild(newListcell(productDetails.get("createdBy")));
					item.appendChild(newListcell(productDetails.get("createdDate")));
					item.appendChild(newListcell(productDetails.get("updatedBy")));
					item.appendChild(newListcell(productDetails.get("updatedDate")));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();}
		}
		
	}

	public void Retag(){

		setVisibleFalseAll();
	   	Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getRetagHistory(new BigDecimal(productNo));
		Listbox resultListBox = (Listbox)this.getFellow("resultListRetag");
		resultListBox.setStyle("font-size: 10px");
		resultListBox.setVisible(true);
		//Grid retagGrid=(Grid)this.getFellow("retagGrid");
		//retagGrid.setVisible(true);
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		if(dataMap!=null){
			resultListBox.getItems().clear();
			try{
			   int count=0;
				for(String productid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();		
					item.setStyle("font-size: 10px");
					item.setValue(productid);
					Map<String,String> productDetails=dataMap.get(productid);
					//item.appendChild(newListcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("currentAccountNo")));
					item.appendChild(newListcell(productDetails.get("newAccountNo")));
					item.appendChild(newListcell(productDetails.get("effectiveDate")));
					item.appendChild(newListcell(productDetails.get("remarks")));
					item.appendChild(newListcell(productDetails.get("createdBy")));
					item.appendChild(newListcell(productDetails.get("createdDate")));
					item.appendChild(newListcell(productDetails.get("updatedBy")));
					item.appendChild(newListcell(productDetails.get("updatedDate")));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();}
		}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
		
	public void deleteAssignCard(Listitem item) throws InterruptedException {

		if (Messagebox.show("Are you sure you wish to delete this item?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}
		try {
			boolean checkAllow = businessHelper.getProductBusiness().checkDeleteAssignCard(item);
			
			if(checkAllow)
			{
				businessHelper.getProductBusiness().deleteAssignCard(item);
				item.detach();
				Messagebox.show("Assign has been successfully deleted", "Delete Assign",
						Messagebox.OK, Messagebox.INFORMATION);
			}
			else
			{
				Messagebox.show(NonConfigurableConstants.DELETE_CARD_ASSIGNMENT_FAIL,
						"Error", Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
		resultList = (Listbox) getFellow("resultListCardAssignment");
		resultList.setVisible(false);
	}

}


