package com.cdgtaxi.ibs.portal.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class MasterLoginRegistrationWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MasterLoginRegistrationWindow.class);
	
	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException{
		
		logger.info("");
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
			
			Map<Integer, Map<String, String>> results = this.businessHelper.getAccountBusiness().searchAccounts(custNoString,"" , "A", "");

			if(results.size()>0){
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(Integer custNo : results.keySet()){
					Listitem acct = new Listitem();
					resultListBox.appendChild(acct);
					Map<String, String> acctDetails = results.get(custNo);
					acct.setValue(acctDetails);
					acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
					acct.appendChild(newListcell(acctDetails.get("acctName")));
										
					Listcell plusImageCell = new Listcell();
					Image plusImage = new Image("/images/add.png");
					plusImage.setStyle("cursor: pointer");
					// returns a listitem
					ZScript showInfo = ZScript
							.parseContent("masterLoginRegistrationWindow.plusRow(self.getParent().getParent())");
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
		Listbox acctResultListBox = (Listbox)this.getFellow("acctResultList");
		Boolean checkContinue = true;
		
		for(Object obj : acctResultListBox.getChildren()) {
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
				acctResultListBox.appendChild(acct);
				
				Map<String, String> acctDetails = (Map<String, String>) item.getValue();
				acct.setValue(item.getValue());
				acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				acct.appendChild(newListcell(acctDetails.get("acctName")));
				
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("masterLoginRegistrationWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				acct.appendChild(imageCell);
		
				acctResultListBox.appendChild(acct);
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
	
	public void create() throws InterruptedException{
		try{
			this.displayProcessing();
			String name = ((Textbox)this.getFellow("nameTextBox")).getValue();
			String email = ((Textbox)this.getFellow("emailTextBox")).getValue();
			String telephone = ((Textbox)this.getFellow("telephoneTextBox")).getValue();
			String companyName = ((Textbox)this.getFellow("companyNameTextBox")).getValue();
			String loginId = ((Textbox)this.getFellow("loginIdTextBox")).getValue();
			

//			String msg = "";
//			Map<String, String> prop = (Map) SpringUtil.getBean("webserviceProperties");
//			if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.vitalmaster.disable"))) {
//				Integer seqNoVital = Integer.parseInt(this.businessHelper.getGenericBusiness().getNextSequenceNo(Sequence.CP_MSTR_REG_REQ_NO_SEQUENCE).toString());
//				msg = IBSVitalMasterClient.sendMasterLogin(loginId, seqNoVital.toString());
//			}
//			else {
//				msg = "disabled";
//				logger.info("Vital Master Interface is disabled");
//			}
//			
//			if(msg != null){
			
			if(loginId.trim().isEmpty())
			{
				logger.info("Login Id cannot be blank");
				Messagebox.show("Login Id cannot be blank.","Error", Messagebox.OK, Messagebox.ERROR);
			}
			else if(email.isEmpty() || telephone.isEmpty() || name.isEmpty())
			{
				logger.info("Email / Telephone / Name cannot be blank");
				Messagebox.show("Email / Telephone / Name cannot be blank. ","Error", Messagebox.OK, Messagebox.ERROR);
			}
			else
			{
				// Check if LoginId create before.
				IttbCpMasterLogin masterLogin = this.businessHelper.getPortalBusiness().getMasterLoginById(loginId);
				
				if(masterLogin != null)
				{
					logger.info("Duplicate Login Id already Existed. LoginId : "+loginId);
					Messagebox.show("LoginId Existed. Login Id : "+loginId ,"Error", Messagebox.OK, Messagebox.ERROR);
				}
				else
				{
					IttbCpMasterLogin ittbCpMasterLogin = new IttbCpMasterLogin();
					Integer seqNo = Integer.parseInt(this.businessHelper.getGenericBusiness().getNextSequenceNo(Sequence.MASTER_LOGIN_NO).toString());
					
					ittbCpMasterLogin.setMasterLoginNo(seqNo);
					ittbCpMasterLogin.setName(name);
					ittbCpMasterLogin.setEmail(email);
					ittbCpMasterLogin.setTelephone(telephone);
					ittbCpMasterLogin.setCompanyName(companyName);
					ittbCpMasterLogin.setLoginId(loginId);
		
					this.businessHelper.getGenericBusiness().save(ittbCpMasterLogin, getUserLoginIdAndDomain());	
					
					Listbox acctResultListBox = (Listbox)this.getFellow("acctResultList");
					for(Object obj : acctResultListBox.getChildren()) {
						try{
							Map<String, String> acctDetails = (Map<String, String>)  ((Listitem)obj).getValue();
							IttbCpMasterTagAcct ittbCpMasterTagAcct = new IttbCpMasterTagAcct();
							ittbCpMasterTagAcct.setAccountName(acctDetails.get("acctName"));
							ittbCpMasterTagAcct.setAccountNo(Integer.parseInt(acctDetails.get("acctNo").toString()));
							ittbCpMasterTagAcct.setCustNo(acctDetails.get("custNo").toString());
							ittbCpMasterTagAcct.setAccountCategory(acctDetails.get("acctCategory").toString());
							ittbCpMasterTagAcct.setMasterLoginNo(ittbCpMasterLogin);
							ittbCpMasterTagAcct.setCreatedBy(getUserLoginIdAndDomain());
							ittbCpMasterTagAcct.setCreatedDt(DateUtil.getCurrentTimestamp());
							this.businessHelper.getGenericBusiness().save(ittbCpMasterTagAcct, getUserLoginIdAndDomain());	
						}catch(ClassCastException e) {}
					}
					Messagebox.show("New Master Login created.", "Create Master Login", Messagebox.OK, Messagebox.INFORMATION);
					Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
	//			}
				}
			}
		}
//		catch(VitalMasterLoginRegInterfaceException vmlrie) {
//			Messagebox.show("Interface error when sending to CP. Please contact Administrator", "Master Login Registration", Messagebox.OK, Messagebox.ERROR);
//			logger.error("Error", vmlrie);
//			vmlrie.printStackTrace();
//		}
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
}
