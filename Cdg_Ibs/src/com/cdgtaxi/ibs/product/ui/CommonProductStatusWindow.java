	package com.cdgtaxi.ibs.product.ui;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.Constants;

	@SuppressWarnings("unchecked")
	public class CommonProductStatusWindow extends CommonWindow {

	private static final long serialVersionUID = -5393882808364848293L;
	private static Logger logger = Logger.getLogger(RenewProductWindow.class);
	@SuppressWarnings("unused")
	private AmtbAccount account= new AmtbAccount();
	String productId="";

	public CommonProductStatusWindow(){
		
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
		for(String productNo : params.keySet()){
			if(productNo.indexOf("productId")>=0){
				productId=params.get(productNo);	
			}
		}
	}
		
	
	public void populateData(){
				
	   	Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getStatusHistory(new BigDecimal(productId));
		Listbox resultListBox = (Listbox)this.getFellow("resultListProductStatus");
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
					//item.appendChild(new Listcell(String.valueOf(count)));
					item.appendChild(new Listcell(productDetails.get("cardNo")));
					item.appendChild(new Listcell(productDetails.get("statusFrom")));
					item.appendChild(new Listcell(productDetails.get("statusTo")));
					item.appendChild(new Listcell(productDetails.get("statusDate")));
					item.appendChild(new Listcell(productDetails.get("reason")));
					item.appendChild(new Listcell(productDetails.get("remarks")));
					item.appendChild(new Listcell(productDetails.get("createdBy")));
					item.appendChild(new Listcell(productDetails.get("createdDate")));
					item.appendChild(new Listcell(productDetails.get("updatedBy")));
					item.appendChild(new Listcell(productDetails.get("updatedDate")));
					resultListBox.appendChild(item);
				}
					//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){
				logger.info("Exception is here");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		populateData();
		//((Textbox)this.getFellow("typeName")).setValue("");
	}
}


