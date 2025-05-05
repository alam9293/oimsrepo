package com.cdgtaxi.ibs.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.business.BusinessHelper;


public class AccountSearchUtil  {
	
	private static Logger logger = Logger.getLogger(AccountSearchUtil.class);
	private static BusinessHelper businessHelper = (BusinessHelper)SpringUtil.getBean("businessHelper");
	private static final long serialVersionUID = -1642510476488242492L;

	public static void populateDepartmentName(Listbox departmentList,String accNo){
		
		Map<String,Map<String,String>> data=AccountSearchUtil.businessHelper.getProductBusiness().getDepartmentInfo(accNo);
		departmentList.getItems().clear();
		if(data!=null){
			LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				datalist.put(accountNo, list.get("departmentName"));
			}
			ArrayList<Listitem>  listItem=new ArrayList<Listitem>();
			//
			Listitem blankitem=new Listitem();
	        blankitem.setValue(null);
			blankitem.setLabel("-");
			listItem.add(blankitem);
			//
			for(String key : datalist.keySet()){
				Listitem item=new Listitem();
				item.setValue(key);
				item.setLabel(datalist.get(key));
				listItem.add(item);          //new Comboitem(data.get(key)));
			}
			departmentList.getItems().addAll(listItem);
			departmentList.setVisible(true);
		}
		//else departmentList.setVisible(false);
		else{
			try{
				// No required - confirmed by user
				//Messagebox.show("There is no Department.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
			}catch(Exception e){e.printStackTrace();}
		}
	}

	public static void populateDivisionOrSubApplicantName(Listbox divOrSubApplList, String accNo){
		populateDivisionOrSubApplicantName(divOrSubApplList, accNo, "old");
	}
	public static void populateDivisionOrSubApplicantNameRetag(Listbox divOrSubApplList, String accNo, String type){
		populateDivisionOrSubApplicantName(divOrSubApplList, accNo, type);
	}
	public static void populateDivisionOrSubApplicantName(Listbox divOrSubApplList, String accNo, String type){
		
		Map<String,Map<String,String>> data=AccountSearchUtil.businessHelper.getProductBusiness().getDivOrSubApplInfo(accNo, type);
		LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
		divOrSubApplList.getItems().clear();
		if(data!=null){
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				datalist.put(accountNo, list.get("divOrSubApplName"));
			}
			
			if(type.equals("new"))
				datalist = sortByAcctNameValue(datalist);
			
			divOrSubApplList.setVisible(true);
			ArrayList<Listitem>  listItem=new ArrayList<Listitem>();
			Listitem blankitem=new Listitem();
	        blankitem.setValue(null);
			blankitem.setLabel("-");
			listItem.add(blankitem);
			for(String key : datalist.keySet()){
				Listitem item=new Listitem();
	           // item.setId(key);
				item.setValue(key);
				item.setLabel(datalist.get(key));
				listItem.add(item);       
			}
			divOrSubApplList.getItems().addAll(listItem);
			if (divOrSubApplList.getItemCount() > 0)
				divOrSubApplList.focus();
		}
		else{
			try{
				// No required - confirmed by user
				//Messagebox.show("There is no Division or Sub Applicant.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
			}catch(Exception e){e.printStackTrace();}
		}
		//else divOrSubApplList.setVisible(false);
	}
	
	private static LinkedHashMap<String, String> sortByAcctNameValue(Map<String, String> unsortMap) {
	    List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(unsortMap.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
	    	 public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
	  	       return (o1.getValue()).compareTo(o2.getValue());
	        }
	    });

	    /// Loop the sorted list and put it into a new insertion order Map
	    /// LinkedHashMap
	    LinkedHashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
	    for (Map.Entry<String, String> entry : list) {
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }

	    return sortedMap;
	}
	
	public static void populateAccountNameCbo(Combobox nameListCombo,String accNo, String name)throws InterruptedException {
			
		Map<String,String> data=new LinkedHashMap<String,String>();
		logger.info("populateAccountNameCbo");
		data=(LinkedHashMap<String,String>)AccountSearchUtil.businessHelper.getProductBusiness().getAccountNoAndName(accNo,name);
		if(data!=null){
			//logger.info("size"+data.size());
			ArrayList<Comboitem>  myitem=new ArrayList<Comboitem>();
			for(String key : data.keySet()){
				Comboitem item=new Comboitem();
				//item.setId(key);
				item.setValue(key);
				item.setLabel(data.get(key));
				myitem.add(item);         
			}
			nameListCombo.getItems().addAll(myitem);
		}
	}
	//Start Code, Active Acount Search For Issue Product
	
	public static void populateActiveDepartmentName(Listbox departmentList,String accNo){
			
		Map<String,Map<String,String>> data=AccountSearchUtil.businessHelper.getProductBusiness().getActiveDepartmentInfoByDivisionAcctNo(accNo);
		departmentList.getItems().clear();
		departmentList.setVisible(false);
		if(data!=null){
			LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				datalist.put(accountNo, list.get("departmentName"));
			}
			ArrayList<Listitem>  listItem=new ArrayList<Listitem>();
				//
			Listitem blankitem=new Listitem();
		       blankitem.setValue(null);
			blankitem.setLabel("-");
			listItem.add(blankitem);
			//
			for(String key : datalist.keySet()){
				Listitem item=new Listitem();
				item.setValue(key);
				item.setLabel(datalist.get(key));
				listItem.add(item);          //new Comboitem(data.get(key)));
			}
			departmentList.getItems().addAll(listItem);
			departmentList.setVisible(true);
		}
		//else departmentList.setVisible(false);
		else{
			try{
				//Messagebox.show("There is no Department.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
			}catch(Exception e){e.printStackTrace();}
		}
	}

	@SuppressWarnings("unchecked")
	public static void populateActiveDivisionOrSubApplicantName(Listbox divOrSubApplList, String accNo){
			
		logger.info("POPULATE ACTIVE DIVISION OR SUB APPLICANTS");
		Map<String,Map<String,String>> data=AccountSearchUtil.businessHelper.getProductBusiness().getActiveDivOrSubApplInfo(accNo);
		LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
		divOrSubApplList.getChildren().clear();
		if(data!=null){
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				datalist.put(accountNo, list.get("divOrSubApplName"));
			}
			divOrSubApplList.setVisible(true);
			
			Listitem blankitem=new Listitem();
	        blankitem.setValue(null);
			blankitem.setLabel("-");
			divOrSubApplList.appendChild(blankitem);

			for(String key : datalist.keySet()){
				Listitem item=new Listitem();
	           // item.setId(key);
				item.setValue(key);
				item.setLabel(datalist.get(key));
				divOrSubApplList.appendChild(item);
			}

			if (divOrSubApplList.getItemCount() > 0)
				divOrSubApplList.focus();
		}
		else{
			try{
				logger.info("No Div");
				//Messagebox.show("There is no Division or Sub Applicant.", "Create Product", Messagebox.OK, Messagebox.INFORMATION);
			}catch(Exception e){e.printStackTrace();}
		}
		//else divOrSubApplList.setVisible(false);
	}
		
	public static void populateActiveAccountNameCbo(Combobox nameListCombo,String accNo, String name)throws InterruptedException {
			
		logger.info("POPULATE ACCOUNT NAMES");
		Map<String,String> data=new LinkedHashMap<String,String>();
		data=(LinkedHashMap<String,String>)AccountSearchUtil.businessHelper.getProductBusiness().getActiveAccountNoAndName(accNo,name);
		nameListCombo.getChildren().clear();
		if(data!=null){
			for(String key : data.keySet()){
				Comboitem item=new Comboitem();
				//item.setId(key);
				item.setValue(key);
				item.setLabel(data.get(key));
				nameListCombo.appendChild(item);
			}
		}
	}
		
}
