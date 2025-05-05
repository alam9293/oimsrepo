package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class AddAcctTypeWindow extends CommonWindow {
	private static final long serialVersionUID = -8720888522063528660L;
	private static Logger logger = Logger.getLogger(AddAcctTypeWindow.class);
	public void save() throws InterruptedException{
		logger.info("save");
		// getting the account template
		String selectedCategory = (String)((Listbox)this.getFellow("acctTypeList")).getSelectedItem().getValue();
		String typeName = ((Textbox)this.getFellow("typeName")).getValue().toUpperCase();
		if(this.businessHelper.getAccountTypeBusiness().hasAccountType(selectedCategory, typeName)){
			Messagebox.show("Account Type Name exist!", "Add Account Type", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(this.businessHelper.getAccountTypeBusiness().createAccountType(selectedCategory, typeName)){
			Messagebox.show("Account Type created", "Add Account Type", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			Messagebox.show("Account Type not created!", "Manage Account Type", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void cancel() throws InterruptedException{
		logger.info("cancel");
		this.back();
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh");
		((Textbox)this.getFellow("typeName")).setValue("");
	}
	public List<Listitem> getAccountTemplates(){
		ArrayList<Listitem> accountTemplates = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.ACCOUNT_TEMPLATES.keySet()){
			accountTemplates.add(new Listitem(NonConfigurableConstants.ACCOUNT_TEMPLATES.get(key), key));
		}
		return accountTemplates;
	}
}
