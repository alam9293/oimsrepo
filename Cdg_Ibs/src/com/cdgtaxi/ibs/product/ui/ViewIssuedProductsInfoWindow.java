package com.cdgtaxi.ibs.product.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.Constants;
	
public class ViewIssuedProductsInfoWindow extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(ViewIssuedProductsInfoWindow.class);
	HashMap<String,Map<String,String>> dataMap=new HashMap<String,Map<String,String>> ();
	
	@SuppressWarnings("unchecked")
	public ViewIssuedProductsInfoWindow(){
		dataMap = (HashMap<String,Map<String,String>>)Executions.getCurrent().getArg();
	}
	
	public void populate(){
		logger.info("Result list populating  ...");
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
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
					
					if(count==1){
						if(productDetails.get("corpOrPersonal").equals("corp")){
							this.getFellow("division").setVisible(true);
							this.getFellow("department").setVisible(true);
						}
						else if(productDetails.get("corpOrPersonal").equals("personal")){
							this.getFellow("subApplicant").setVisible(true);
						}
					}
					
					item.appendChild(newListcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get(productid)));
					item.appendChild(newListcell(productDetails.get("accountName")));
					item.appendChild(newListcell(productDetails.get("division")));
					item.appendChild(newListcell(productDetails.get("department")));
					item.appendChild(newListcell(productDetails.get("subApplicant")));
					item.appendChild(newListcell(productDetails.get("nameOnCard")));
					item.appendChild(newListcell(productDetails.get("contatct")));
					
					item.appendChild(newListcell(productDetails.get("issue"+productid)));
					if(productDetails.get("expiry"+productid)!=null)
						item.appendChild(newListcell(productDetails.get("expiry"+productid)));
					else
						item.appendChild(newListcell("-"));
					resultListBox.appendChild(item);
				}
				
				//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
					resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(200);
				logger.info("Result list had been populated");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void PopulateReplacement(){
		logger.info("Result list populating  ...");
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
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
					item.appendChild(newListcell(String.valueOf(count)));
					item.appendChild(newListcell(productDetails.get(productid)));
					item.appendChild(newListcell(productDetails.get("issue"+productid)));
					if(productDetails.get("expiry"+productid)!=null)
						item.appendChild(newListcell(productDetails.get("expiry"+productid)));
					else
						item.appendChild(newListcell("-"));
					resultListBox.appendChild(item);
				}
				//To show the no record found message below the list
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(200);
				logger.info("Result list had been populated");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}

	public void cancel_from_replaced() throws InterruptedException {
		this.back().back();
	}
	
	@Override
	public void refresh() throws InterruptedException {
	}
}


