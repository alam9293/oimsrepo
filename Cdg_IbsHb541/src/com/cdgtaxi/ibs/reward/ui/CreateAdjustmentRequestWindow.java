package com.cdgtaxi.ibs.reward.ui;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InsufficientRewardPointsException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.forms.SearchAdjustmentRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class CreateAdjustmentRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateAdjustmentRequestWindow.class);
	
	private Intbox accountNoIntBox, ptsIB;
	private Combobox accountNameComboBox;
	private Listbox requestTypeLB, adjustPtsFromLB, reasonLB;
	private CapsTextbox remarksTB;
	
	public void afterCompose(){
		Components.wireVariables(this, this);
		
		Set<Entry<String, String>> requestTypes = NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPES.entrySet();
		for(Entry<String, String> requestType : requestTypes){
			requestTypeLB.appendItem(requestType.getValue(), requestType.getKey());
		}
		if(!requestTypeLB.getChildren().isEmpty()) requestTypeLB.setSelectedIndex(0);
		this.onSelectRequestType();
		
		Set<Entry<String, String>> values = NonConfigurableConstants.REWARDS_REDEEM_FROM.entrySet();
		for(Entry<String, String> value : values){
			adjustPtsFromLB.appendItem(value.getValue(), value.getKey());
		}
		if(!adjustPtsFromLB.getChildren().isEmpty()) adjustPtsFromLB.setSelectedIndex(0);
		
		Set<Entry<String, String>> reasons = ConfigurableConstants.getMasters(ConfigurableConstants.REWARDS_ADJUSTMENT_REASON).entrySet();
		for(Entry<String, String> value : reasons){
			reasonLB.appendItem(value.getValue(), value.getKey());
		}
		if(!reasonLB.getChildren().isEmpty()) reasonLB.setSelectedIndex(0);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
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
	
	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");

		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info(name);
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			try{
				// removing the last brackets () and making sure it is integer for customer number.
				if(name.lastIndexOf("(")!=-1 && name.lastIndexOf(")")!=-1){// have ( and )
					// now extracting the string between the brackets
					String custNo = name.substring(name.lastIndexOf("(")+1, name.lastIndexOf(")"));
					logger.info("custNo = " + custNo);
					// now making sure it's in integer.
					Integer.parseInt(custNo);
					// now trimming the customer no away
					name = name.substring(0, name.lastIndexOf("(")).trim();
					logger.info("name = " + name);
				}
			}catch(NumberFormatException nfe){
				logger.warn("Exception on converting customer. Skipping trimming");
				logger.error(nfe);
			}
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void create() throws InterruptedException{
		try{
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1){
				Comboitem comboItem = accountNameComboBox.getItemAtIndex(0);
				if(comboItem.getLabel().replaceAll("  ", " ").equals(accountNameComboBox.getText())){
					accountNameComboBox.setSelectedIndex(0);
				}
			}
			if(accountNameComboBox.getSelectedItem()==null) {
				throw new WrongValueException(accountNameComboBox, "* Mandatory field");
			}
			
			AmtbAccount selectedAcct = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			Integer pts = ptsIB.getValue();
			String requestType = (String)requestTypeLB.getSelectedItem().getValue();
			String adjustPtsFrom = (String)adjustPtsFromLB.getSelectedItem().getValue();
			if(adjustPtsFromLB.isDisabled()) adjustPtsFrom = NonConfigurableConstants.REWARDS_REDEEM_FROM_CURR;
			String reason = (String)reasonLB.getSelectedItem().getValue();
			String remarks = remarksTB.getValue();
			
			AmtbAcctStatus acctStatus = this.businessHelper.getRewardBusiness().getAccountLatestStatus(selectedAcct.getCustNo());
			if(acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) ||
					acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED) ||
					acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED)){
				Messagebox.show("Selected account is either suspended, closed or terminated which is not allowed for rewards adjustment",
						"Rewards Adjustment Request", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			SearchAdjustmentRequestForm form = new SearchAdjustmentRequestForm();
			form.customerNo = selectedAcct.getCustNo();
			form.requestStatus = NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING;
			if(this.businessHelper.getRewardBusiness().searchAdjustmentRequest(form).isEmpty() == false){
				Messagebox.show("Selected account has a pending request therefore not allowed to create another request",
						"Rewards Adjustment Request", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			this.displayProcessing();
			
			Serializable requestNo = this.businessHelper.getRewardBusiness().createAdjustmentRequest(
					selectedAcct, pts, requestType, adjustPtsFrom, reason, remarks, getUserLoginIdAndDomain());
			
			Messagebox.show("Rewards adjustment request created (Request No: "+requestNo+").", "Rewards Adjustment Request", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(InsufficientRewardPointsException irpe){
			Messagebox.show("Points is not sufficient for adjustment",
					"Rewards Adjustment Request", Messagebox.OK, Messagebox.ERROR);
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
	
	public void onSelectRequestType(){
		if(requestTypeLB.getSelectedItem().getValue().toString().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPE_DEDUCT))
			adjustPtsFromLB.setDisabled(false);
		else
			adjustPtsFromLB.setDisabled(true);
	}
	
	@Override
	public void refresh() {

	}
}
