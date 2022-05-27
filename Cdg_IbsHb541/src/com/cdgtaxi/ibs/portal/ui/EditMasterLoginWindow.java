package com.cdgtaxi.ibs.portal.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class EditMasterLoginWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditMasterLoginWindow.class);
	private Integer masterLoginNo; 
	private IttbCpMasterLogin ittbCpMasterLogin;
	private Listbox acctResultList;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		masterLoginNo = (Integer)map.get("masterLoginNo");
		
		ittbCpMasterLogin = this.businessHelper.getPortalBusiness().getMasterLogin(masterLoginNo); 
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{

		Label lastUpdatedBy=(Label) this.getFellow("lastUpdatedBy");
		Label lastUpdatedDate=(Label) this.getFellow("lastUpdatedDate");
		Label createdBy=(Label) this.getFellow("createdBy");
		Label createdDate=(Label) this.getFellow("createdDate");
		Label statusField = (Label) this.getFellow("statusField");
		Label retrievedDate = (Label) this.getFellow("retrievedDateField");
		Label retrievalDesc = (Label) this.getFellow("retrievalDescField");
		
		if(ittbCpMasterLogin != null) {
			
			((Textbox)this.getFellow("nameTextBox")).setValue(ittbCpMasterLogin.getName());
			((Textbox)this.getFellow("emailTextBox")).setValue(ittbCpMasterLogin.getEmail());
			((Textbox)this.getFellow("telephoneTextBox")).setValue(ittbCpMasterLogin.getTelephone());
			((Textbox)this.getFellow("companyNameTextBox")).setValue(ittbCpMasterLogin.getCompanyName());
			((Textbox)this.getFellow("loginIdTextBox")).setValue(ittbCpMasterLogin.getLoginId());
			
			String stringStatus = "Active";
			String stringRetrievedDate = "-";
			String stringRetrievedDesc = "-";

//			retrievedDate.setValue("01/01/2020");;
//			retrievalDesc.setValue("Retrieval Description");;
//			statusField.setValue("Active");
			
			if(ittbCpMasterLogin.getRetrievedFlag() != null && ittbCpMasterLogin.getRetrievedFlag().equalsIgnoreCase("D"))
			{
				stringStatus = "Disabled";
				((Button)this.getFellow("enableId")).setVisible(true);
				((Button)this.getFellow("disableId")).setVisible(false);
			}
			else
			{
				((Button)this.getFellow("enableId")).setVisible(false);
				((Button)this.getFellow("disableId")).setVisible(true);
			}
			if(ittbCpMasterLogin.getRetrievalDesc() != null)
				stringRetrievedDesc = ittbCpMasterLogin.getRetrievalDesc();
			if(ittbCpMasterLogin.getRetrievedDate() != null)
				stringRetrievedDate = DateUtil.convertTimestampToStr(ittbCpMasterLogin.getRetrievedDate(), DateUtil.GLOBAL_DATE_FORMAT);
				
			retrievedDate.setValue(stringRetrievedDate);
			retrievalDesc.setValue(stringRetrievedDesc);
			statusField.setValue(stringStatus);
			
			lastUpdatedBy.setValue(ittbCpMasterLogin.getUpdatedBy());
			lastUpdatedDate.setValue(DateUtil.convertTimestampToStr(ittbCpMasterLogin.getUpdatedDt(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
			createdBy.setValue(ittbCpMasterLogin.getCreatedBy());
			createdDate.setValue(DateUtil.convertTimestampToStr(ittbCpMasterLogin.getCreatedDt(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
			
			if(!ittbCpMasterLogin.getIttbCpMasterTagAcct().isEmpty()) {
				
				acctResultList.setMold(Constants.LISTBOX_MOLD_DEFAULT);
				
				for(IttbCpMasterTagAcct tagAcct : ittbCpMasterLogin.getIttbCpMasterTagAcct()) 
				{
					Listitem acct = new Listitem();
					acctResultList.appendChild(acct);
					Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();;
					acctDetails2.put("custNo", tagAcct.getCustNo().toString());
					acctDetails2.put("acctName", tagAcct.getAccountName());
					acctDetails2.put("acctNo", tagAcct.getAccountNo().toString());
					acctDetails2.put("acctCategory", tagAcct.getAccountCategory().toString());
					acct.setValue(acctDetails2);
					acct.appendChild(newListcell(new Integer(tagAcct.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
					acct.appendChild(newListcell(tagAcct.getAccountName()));
					
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("editMasterLoginWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					acct.appendChild(imageCell);
			
					acctResultList.appendChild(acct);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException{
		
		String custNoString = ((Intbox)this.getFellow("accountNoIntBox")).getText();
		try{
			// if data exist
			if(custNoString!=null && custNoString.length()!=0){
				// try parsing it to integer
				int custNo = Integer.parseInt(custNoString);
				// if account number is lesser than 0 = invalid. setting string to null
				if(custNo < 0){
					Messagebox.show("Invalid number for account no. Continuing without account no", "Search Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
					custNoString = null;
				}
			}
		}catch(NumberFormatException nfe){
			// Shouldn't happen
			Messagebox.show("Invalid format for account no. Continuing without account no", "Search Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			custNoString = null;
		}
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		try{
			
			Map<Integer, Map<String, String>> results = this.businessHelper.getAccountBusiness().searchAccounts(custNoString, "", "A", "");

			if(results.size()>0){
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(Integer custNo : results.keySet()){
					Listitem acct = new Listitem();
					resultListBox.appendChild(acct);
					Map<String, String> acctDetails = results.get(custNo);
					Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();;
					acctDetails2.put("custNo", acctDetails.get("custNo"));
					acctDetails2.put("acctName", acctDetails.get("acctName"));
					acctDetails2.put("acctNo", acctDetails.get("acctNo"));
					acctDetails2.put("acctCategory", acctDetails.get("acctCategory"));
					acct.setValue(acctDetails2);
					acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
					acct.appendChild(newListcell(acctDetails.get("acctName")));
										
					Listcell plusImageCell = new Listcell();
					Image plusImage = new Image("/images/add.png");
					plusImage.setStyle("cursor: pointer");
					// returns a listitem
					ZScript showInfo = ZScript
							.parseContent("editMasterLoginWindow.plusRow(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					plusImage.addEventHandler("onClick", pdEvent);
					plusImageCell.appendChild(plusImage);
					acct.appendChild(plusImageCell);
					
					resultListBox.appendChild(acct);
				}
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				}
			}
			
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void plusRow(Listitem item) throws InterruptedException {
		try{
		Boolean checkContinue = true;
		for(Object obj : acctResultList.getChildren()) {
			try{
				Listitem listacct = (Listitem) obj;
				
				if(listacct.getValue().equals(item.getValue())) {
					checkContinue = false;
					Messagebox.show("Have already added this account no.", 
							"Error", Messagebox.OK, Messagebox.ERROR);
					break;
				}
			}catch(ClassCastException e) {}
		}
		
			if(checkContinue){
				Listitem acct = new Listitem();
				acctResultList.appendChild(acct);
				
				Map<String, String> acctDetails = (Map<String, String>) item.getValue();
				Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();;
				acctDetails2.put("custNo", acctDetails.get("custNo"));
				acctDetails2.put("acctName", acctDetails.get("acctName"));
				acctDetails2.put("acctNo", acctDetails.get("acctNo"));
				acctDetails2.put("acctCategory", acctDetails.get("acctCategory"));
				acct.setValue(acctDetails2);
				acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				acct.appendChild(newListcell(acctDetails.get("acctName")));
				
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("editMasterLoginWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				acct.appendChild(imageCell);
		
				acctResultList.appendChild(acct);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Listitem item) throws InterruptedException {
		item.detach();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
	
	public void save() throws InterruptedException{
		try{
			this.displayProcessing();
			String name = ((Textbox)this.getFellow("nameTextBox")).getValue();
			String email = ((Textbox)this.getFellow("emailTextBox")).getValue();
			String telephone = ((Textbox)this.getFellow("telephoneTextBox")).getValue();
			String companyName = ((Textbox)this.getFellow("companyNameTextBox")).getValue();
			String loginId = ((Textbox)this.getFellow("loginIdTextBox")).getValue();
			
			ittbCpMasterLogin.setMasterLoginNo(masterLoginNo);
			ittbCpMasterLogin.setName(name);
			ittbCpMasterLogin.setEmail(email);
			ittbCpMasterLogin.setTelephone(telephone);
			ittbCpMasterLogin.setCompanyName(companyName);
			ittbCpMasterLogin.setLoginId(loginId);

			this.businessHelper.getGenericBusiness().update(ittbCpMasterLogin, getUserLoginIdAndDomain());	

			//get FullList of before
			List<IttbCpMasterTagAcct> ittbCpMasterTagAcctBeforeList = this.businessHelper.getPortalBusiness().getMasterLoginTagAcct(masterLoginNo);
			List<IttbCpMasterTagAcct> ittbCpMasterTagAcctAfterList = new ArrayList<IttbCpMasterTagAcct>();
			List<IttbCpMasterTagAcct> ittbCpMasterTagAcctDeleteList = new ArrayList<IttbCpMasterTagAcct>();
			for(Object obj : acctResultList.getChildren()) {
				try{
					Map<String, String> acctDetails = (Map<String, String>)  ((Listitem)obj).getValue();
					IttbCpMasterTagAcct ittbCpMasterTagAcct = new IttbCpMasterTagAcct();
					
					ittbCpMasterTagAcct = new IttbCpMasterTagAcct();
					ittbCpMasterTagAcct.setAccountName(acctDetails.get("acctName"));
					ittbCpMasterTagAcct.setCustNo(acctDetails.get("custNo").toString());
					ittbCpMasterTagAcct.setAccountNo(Integer.parseInt(acctDetails.get("acctNo").toString()));
					ittbCpMasterTagAcct.setMasterLoginNo(ittbCpMasterLogin);
					ittbCpMasterTagAcct.setAccountCategory(acctDetails.get("acctCategory"));
					ittbCpMasterTagAcct.setCreatedBy(getUserLoginIdAndDomain());
					ittbCpMasterTagAcct.setCreatedDt(DateUtil.getCurrentTimestamp());
					
					ittbCpMasterTagAcctAfterList.add(ittbCpMasterTagAcct);
				}catch(ClassCastException e) {}
			}
			
			//go Thru Before
			//if inside before but not in after ==delete
			//if inside both = update
			for(IttbCpMasterTagAcct beforeAcct : ittbCpMasterTagAcctBeforeList) {
				
				boolean gotInBefore = false;
				for(IttbCpMasterTagAcct afterAcct : ittbCpMasterTagAcctAfterList) {
					if(afterAcct.getAccountNo().equals(beforeAcct.getAccountNo())) 
					{
						gotInBefore = true;
						break;
					}
				}
				
				if(gotInBefore) {
					this.businessHelper.getGenericBusiness().update(beforeAcct, getUserLoginIdAndDomain());	
				}
				else
					ittbCpMasterTagAcctDeleteList.add(beforeAcct);
				
			}
			//go thru after
			//if inside after but not in before == add 
			for(IttbCpMasterTagAcct afterAcct : ittbCpMasterTagAcctAfterList) {
				
				Boolean gotInAfter = false;
				for(IttbCpMasterTagAcct beforeAcct : ittbCpMasterTagAcctBeforeList) {
					if(beforeAcct.getAccountNo().equals(afterAcct.getAccountNo())) 
					{
						gotInAfter = true;
						break;
					}
				}
				
				if(!gotInAfter)
					this.businessHelper.getGenericBusiness().save(afterAcct, getUserLoginIdAndDomain());	
			}

			if(ittbCpMasterTagAcctDeleteList.size()>0) {
				for(IttbCpMasterTagAcct delAcct : ittbCpMasterTagAcctDeleteList)
					this.businessHelper.getGenericBusiness().delete(delAcct);	
			}
			
			Messagebox.show("Master Login Updated", "Update Master Login", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchAccount() throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer customerNo = ((Intbox)this.getFellow("accountNoIntBox")).getValue();
		
		if (customerNo == null)
		{
			return;
		}
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(customerNo.toString().equals(selectedAccount.getCustNo())) return;
		}
		
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();

		try{
	
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(customerNo.toString(), "");
			if (accounts != null && !accounts.isEmpty())
			{
				for(AmtbAccount account : accounts)
				{
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if(accounts.size()==1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
			}
			else
			{
				//Clear list for every new search
				accountNameComboBox.getChildren().clear();
				((Intbox)this.getFellow("accountNoIntBox")).setValue(null);
				// Set focus back to accountText
				((Intbox)this.getFellow("accountNoIntBox")).setFocus(true);
				Messagebox.show("There is no such account in the system", 
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
			
		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
			
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1)
				accountNameComboBox.setSelectedIndex(0);
			
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchAccountName(String name) throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		if (name == null || "".equals(name) || (name.length()<3))
		{
			return;
		}
		
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try{
			
			
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(null, name);
			if (accounts != null && !accounts.isEmpty())
			{
				for(AmtbAccount account : accounts)
				{
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if(accounts.size()==1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
				else
					accountNameComboBox.open();
			}
			
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void disable() throws InterruptedException{
		
		if(ittbCpMasterLogin.getRetrievedFlag() != null && ittbCpMasterLogin.getRetrievedFlag().equalsIgnoreCase("D")){
			Messagebox.show("Unable to disabled! Already Disabled", "Disable", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		else
		{
			ittbCpMasterLogin.setRetrievedFlag("D");
			ittbCpMasterLogin.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
			ittbCpMasterLogin.setUpdatedBy(getUserLoginIdAndDomain());
			this.businessHelper.getGenericBusiness().update(ittbCpMasterLogin);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		}
	}
	public void enable() throws InterruptedException{
		
		if(ittbCpMasterLogin.getRetrievedFlag() == null && !ittbCpMasterLogin.getRetrievedFlag().equalsIgnoreCase("D")){
			Messagebox.show("Unable to Enable! Already Enabled", "Enabled", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		else
		{
			ittbCpMasterLogin.setRetrievedFlag(null);
			ittbCpMasterLogin.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
			ittbCpMasterLogin.setUpdatedBy(getUserLoginIdAndDomain());
			this.businessHelper.getGenericBusiness().update(ittbCpMasterLogin);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		}
	}
}
