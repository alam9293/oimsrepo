package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

public class TransferAccountWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TransferAccountWindow.class);
	private List<Listitem> industries = new ArrayList<Listitem>();
	private List<Listitem> salespersons = new ArrayList<Listitem>();
	public TransferAccountWindow(){
		logger.info("TransferAccountWindow()");
		// getting industry
		industries.add((Listitem)ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterIndustry = ConfigurableConstants.getIndustries();
		for(String masterCode : masterIndustry.keySet()){
			this.industries.add(new Listitem(masterIndustry.get(masterCode), masterCode));
		}
		salespersons.add(ComponentUtil.createRequiredDefaultListitem());
		Map<Integer, String> salespeople = MasterSetup.getSalespersonManager().getAllMasters();
		for(Integer salesId : salespeople.keySet()){
			salespersons.add(new Listitem(salespeople.get(salesId), salesId));
		}
	}
	public void updateAccounts(){
		logger.info("updateAccounts()");
		Listitem fromSalesperson = ((Listbox)this.getFellow("fromSalespersonList")).getSelectedItem();
		Listitem toSalesperson = ((Listbox)this.getFellow("toSalespersonList")).getSelectedItem();
		Listbox accounts = (Listbox)this.getFellow("accounts");
		accounts.getItems().clear();
		if(fromSalesperson!=null && fromSalesperson.getValue()!=null && toSalesperson!=null && toSalesperson.getValue()!=null && !fromSalesperson.getValue().equals(toSalesperson.getValue())){
			String industryCode = null;
			Listitem industryItem = ((Listbox)this.getFellow("industryList")).getSelectedItem();
			if(industryItem!=null && industryItem.getValue()!=null && industryItem.getValue() instanceof String && ((String)industryItem.getValue()).length()!=0){
				industryCode = (String)industryItem.getValue();
			}
			Map<Integer, String> accts = this.businessHelper.getAccountBusiness().getAccounts((Integer)fromSalesperson.getValue(), industryCode);
			for(Integer acctNo : accts.keySet()){
				Listitem item = new Listitem(accts.get(acctNo), acctNo);
				accounts.appendChild(item);
			}
			accounts.setDisabled(false);
		}else{
			accounts.setDisabled(true);
		}
	}
	public void submit() throws InterruptedException{
		logger.info("submit()");
		Listitem fromSalesperson = ((Listbox)this.getFellow("fromSalespersonList")).getSelectedItem();
		if(fromSalesperson==null || fromSalesperson.getValue()==null){
			Messagebox.show("Please select the \"From\" salesperson!", "Transfer Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Listitem toSalesperson = ((Listbox)this.getFellow("toSalespersonList")).getSelectedItem();
		if(toSalesperson==null || toSalesperson.getValue()==null){
			Messagebox.show("Please select the \"To\" salesperson!", "Transfer Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Date effectiveDate = ((Datebox)this.getFellow("effectiveDate")).getValue();
		if(effectiveDate.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDate)){
			Messagebox.show("Effective date must be today or later!", "Transfer Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Listbox accounts = (Listbox)this.getFellow("accounts");
		if(accounts.getSelectedItems().isEmpty()){
			Messagebox.show("Please select one or more account.", "Transfer Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Set<Integer> accountNos = new HashSet<Integer>();
		for(Object selectedItem : accounts.getSelectedItems()){
			accountNos.add((Integer)((Listitem)selectedItem).getValue());
		}
		if(this.businessHelper.getAccountBusiness().hasFutureTransferAcct(accountNos)){
			Messagebox.show("One of the account has future transfer of account!", "Transfer Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(Messagebox.show("Confirm transfer?", "Transfer Accounts", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().transferAccount((Integer)fromSalesperson.getValue(), (Integer)toSalesperson.getValue(), effectiveDate, accountNos, this.getUserLoginIdAndDomain());
			Messagebox.show("Accounts transferred", "Transfer Accounts", Messagebox.OK, Messagebox.INFORMATION);
		}
		this.init();
	}
	public List<Listitem> getIndustries(){
		return this.industries;
	}
	public List<Listitem> getSalespersons(){
		return cloneList(this.salespersons);
	}
	public void init(){
		((Listbox)this.getFellow("fromSalespersonList")).setSelectedIndex(0);
		((Listbox)this.getFellow("toSalespersonList")).setSelectedIndex(0);
		((Listbox)this.getFellow("industryList")).setSelectedIndex(0);
		Listbox accounts = (Listbox)this.getFellow("accounts");
		accounts.getItems().clear();
		accounts.setDisabled(true);
		Constraint nullConstraint = null;
		Datebox effectiveDate = (Datebox)this.getFellow("effectiveDate");
		Constraint tempConstraint = effectiveDate.getConstraint();
		effectiveDate.setConstraint(nullConstraint);
		effectiveDate.setValue(null);
		effectiveDate.setConstraint(tempConstraint);
	}
	@Override
	public void refresh() throws InterruptedException {
		
	}
}