	package com.cdgtaxi.ibs.product.ui;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
	public class ViewProductIssuanceHistoryWindow extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(ViewProductIssuanceHistoryWindow.class);
	private String accountNo="";
	private String productType;
	@SuppressWarnings("unchecked")
	public ViewProductIssuanceHistoryWindow(){
		
		HashMap<String,String> params = (HashMap<String,String>)Executions.getCurrent().getArg();

		accountNo = params.get("accountId");
	  	productType = params.get("productType");
	}
	
	public void populateData(){
		
	   	Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductIssuanceHistory(accountNo, productType);
		Listbox resultListBox = (Listbox)this.getFellow("productIssuedList");
		resultListBox.setVisible(true);
		
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
					item.appendChild(new Listcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("cardType")));
					item.appendChild(newListcell(productDetails.get("nameOnCard")));
					item.appendChild(newListcell(productDetails.get("creditLimit"), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(productDetails.get("issueDate")));
					item.appendChild(newListcell(productDetails.get("expiryDate")));
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

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		 populateData();
		 logger.info("??");
		
	}
}


