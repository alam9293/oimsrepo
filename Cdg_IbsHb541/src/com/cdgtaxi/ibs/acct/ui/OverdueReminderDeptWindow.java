package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.google.common.base.Splitter;


public class OverdueReminderDeptWindow extends CommonInvoiceOverdueReminderWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private static Logger logger = Logger.getLogger(OverdueReminderDeptWindow.class);
	private String custNo, parentCode, code;
	@SuppressWarnings("unchecked")
	public OverdueReminderDeptWindow() throws InterruptedException{
		logger.info("OverdueReminderDeptWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Overdue Invoice Reminder Details Department", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = params.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Overdue Invoice Reminder Details Department", Messagebox.OK, Messagebox.ERROR);
		}
		// parent code
		parentCode = params.get("parentCode");
		if(parentCode==null || parentCode.trim().length()==0){
			Messagebox.show("No parent code found!", "Overdue Invoice Reminder Details Department", Messagebox.OK, Messagebox.ERROR);
		}
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		
	}
	public void init() throws InterruptedException{
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		if(code==null || code.trim().length()==0){
			this.back();
			return;
		}
		if(parentCode==null || parentCode.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DEPARTMENT_LEVEL, parentCode, code);
		
		Listbox overdueReminder = (Listbox)this.getFellow("overdueReminder");
		for(Object overdueReminderFlag : overdueReminder.getChildren()){
			if(((Listitem)overdueReminderFlag).getValue().equals(details.get("overdueReminder"))){
				((Listitem)overdueReminderFlag).setSelected(true);
				break;
			}
		}
		((Textbox)this.getFellow("reminderEmail")).setValue((String)details.get("reminderEmail"));
	}
	public void submitOverdueReminder() throws InterruptedException, WrongValueException{
		String overdueInvoiceReminder = (String)((Listbox)this.getFellow("overdueReminder")).getSelectedItem().getValue();
		String wholeEmailForCheck =  ((Textbox)this.getFellow("reminderEmail")).getValue();
		String wholeEmail = "";
		String regex = NonConfigurableConstants.EMAIL_REGEX;
		Pattern pattern = Pattern.compile(regex);
		int i = 0;
		
		if(!wholeEmailForCheck.trim().equals("")) //if email not blank, check for valid;
		{
			Iterable<String> result = Splitter.on(';').split(wholeEmailForCheck);
			for(String s: result){
				Matcher matcher = pattern.matcher(s.trim());
	
				if(!matcher.matches())
				{
					Messagebox.show("The Email is invalid, "+s, "Email Invalid", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				else {
					if(i!=0)
						wholeEmail+= ";"+s.trim();
					else
						wholeEmail += s.trim();
				}
				i++;
			}
		}
		
		try {
			super.invoiceOverdueReminder(wholeEmail, overdueInvoiceReminder, custNo,  parentCode, code);
			Messagebox.show("Update Overdue Invoice Reminder Details.", "Overdue Invoice Reminder Details Department", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}catch (WrongValueException wve){
			throw wve;
		}catch (Exception e) {
			logger.error("Error", e);
			Messagebox.show("Unable to update! Please try again later", "Overdue Invoice Reminder Details Department", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public List<Listitem> getBooleanList() {
		return cloneList(booleanList);
	}
	public void setBooleanList(List<Listitem> booleanList) {
		this.booleanList = booleanList;
	}
}