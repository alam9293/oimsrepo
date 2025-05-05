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


public class EInvoiceEmailDeptWindow extends CommonEInvoiceEmailWindow {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(EInvoiceEmailDeptWindow.class);
	private String custNo, code, parentCode;
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> attachmentSelectionList = new ArrayList<Listitem>();
	private List<Listitem> pageSelectionList = new ArrayList<Listitem>();
	
	@SuppressWarnings("unchecked")
	public EInvoiceEmailDeptWindow() throws InterruptedException {
		logger.info("EInvoiceEmailDeptWindow()");
		Map<String, String> params = Executions.getCurrent().getArg();
		custNo = params.get("custNo");
		
		if(custNo == null || custNo.trim().length() == 0) {
			Messagebox.show("No Account Number!", "EInvoice Email Details Department", Messagebox.OK, Messagebox.ERROR);
		}
		
		code = params.get("code");
		
		if(code == null || code.trim().length() == 0) {
			Messagebox.show("No account code found!", "EInvoice Email Details Department", Messagebox.OK, Messagebox.ERROR);
		}
		
		parentCode = params.get("parentCode");
		
		if(parentCode == null || parentCode.trim().length() == 0){
			Messagebox.show("No parent code found!", "EInvoice Email Details Department", Messagebox.OK, Messagebox.ERROR);
		}

		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		
		attachmentSelectionList.add(new Listitem(NonConfigurableConstants.E_INVOICE_ATTACHMENT_SELECTION.get(NonConfigurableConstants.E_INVOICE_EMAIL_ATTACHMENT_ONE), NonConfigurableConstants.E_INVOICE_EMAIL_ATTACHMENT_ONE));
		attachmentSelectionList.add(new Listitem(NonConfigurableConstants.E_INVOICE_ATTACHMENT_SELECTION.get(NonConfigurableConstants.E_INVOICE_EMAIL_ATTACHMENT_MULTIPLE), NonConfigurableConstants.E_INVOICE_EMAIL_ATTACHMENT_MULTIPLE));

		pageSelectionList.add(new Listitem(NonConfigurableConstants.E_INVOICE_PAGE_SELECTION.get(NonConfigurableConstants.E_INVOICE_EMAIL_PAGE_ALL), NonConfigurableConstants.E_INVOICE_EMAIL_PAGE_ALL));
		pageSelectionList.add(new Listitem(NonConfigurableConstants.E_INVOICE_PAGE_SELECTION.get(NonConfigurableConstants.E_INVOICE_EMAIL_PAGE_NO_REPORT), NonConfigurableConstants.E_INVOICE_EMAIL_PAGE_NO_REPORT));
	}
	
	public void init() throws InterruptedException {
		if(custNo == null || custNo.trim().length() == 0) {
			this.back();
			return;
		}
		
		if(code == null || code.trim().length() == 0) {
			this.back();
			return;
		}
		
		if(parentCode == null || parentCode.trim().length() == 0) {
			this.back();
			return;
		}
		
		Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DEPARTMENT_LEVEL, parentCode, code);
		
		Listbox eInvoiceEmailFlag = (Listbox)this.getFellow("eInvoiceEmailFlag");
		for(Object eInvoiceEmailFlagObject : eInvoiceEmailFlag.getChildren()){
			if(((Listitem)eInvoiceEmailFlagObject).getValue().equals(details.get("eInvoiceEmailFlag"))){
				((Listitem)eInvoiceEmailFlagObject).setSelected(true);
				break;
			}
		}
		
		((Textbox)this.getFellow("eInvoiceEmail")).setValue((String)details.get("eInvoiceEmail"));
		((Textbox)this.getFellow("eInvoiceEmailSubject")).setValue((String)details.get("eInvoiceEmailSubject"));
		
		Listbox eInvoiceEmailZipFlag = (Listbox)this.getFellow("eInvoiceEmailZipFlag");
		for(Object eInvoiceEmailZipFlagObject : eInvoiceEmailZipFlag.getChildren()){
			if(((Listitem)eInvoiceEmailZipFlagObject).getValue().equals(details.get("eInvoiceEmailZipFlag"))){
				((Listitem)eInvoiceEmailZipFlagObject).setSelected(true);
				break;
			}
		}
		
		Listbox eInvoiceEmailAttachment = (Listbox)this.getFellow("eInvoiceEmailAttachment");
		for(Object eInvoiceEmailAttachmentFlag : eInvoiceEmailAttachment.getChildren()){
			if(((Listitem)eInvoiceEmailAttachmentFlag).getValue().equals(details.get("eInvoiceEmailAttachment"))){
				((Listitem)eInvoiceEmailAttachmentFlag).setSelected(true);
				break;
			}
		}
		
		Listbox eInvoiceEmailPage = (Listbox)this.getFellow("eInvoiceEmailPage");
		for(Object eInvoiceEmailPageFlag : eInvoiceEmailPage.getChildren()){
			if(((Listitem)eInvoiceEmailPageFlag).getValue().equals(details.get("eInvoiceEmailPage"))){
				((Listitem)eInvoiceEmailPageFlag).setSelected(true);
				break;
			}
		}
	}
	
	public void submitEInvoiceEmail() throws InterruptedException, WrongValueException{
		
		String eInvoiceEmailFlag = (String)((Listbox)this.getFellow("eInvoiceEmailFlag")).getSelectedItem().getValue();
		String eInvoiceEmail =  ((Textbox)this.getFellow("eInvoiceEmail")).getValue();
		String eInvoiceEmailSubject =  ((Textbox)this.getFellow("eInvoiceEmailSubject")).getValue();
		String eInvoiceEmailZipFlag = (String)((Listbox)this.getFellow("eInvoiceEmailZipFlag")).getSelectedItem().getValue();
		String eInvoiceEmailAttachment = (String)((Listbox)this.getFellow("eInvoiceEmailAttachment")).getSelectedItem().getValue();
		String eInvoiceEmailPage = (String)((Listbox)this.getFellow("eInvoiceEmailPage")).getSelectedItem().getValue();
		
		String eInvoiceEmailValidated = "";
		
		String regex = NonConfigurableConstants.EMAIL_REGEX;
		Pattern pattern = Pattern.compile(regex);
		int i = 0;
		
		if(!eInvoiceEmail.trim().equals("")) {
			Iterable<String> result = Splitter.on(';').split(eInvoiceEmail);
			
			for(String s: result) {
				Matcher matcher = pattern.matcher(s.trim());
	
				if(!matcher.matches()) {
					Messagebox.show("The Email is invalid, "+ s, "Email Invalid", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				} else {
					if(i!=0) {
						eInvoiceEmailValidated += ";" + s.trim();
					} else {
						eInvoiceEmailValidated += s.trim();
					}
				}
				i++;
			}
		}
		
		try {
			super.eInvoiceEmail(eInvoiceEmailFlag, eInvoiceEmailValidated, eInvoiceEmailSubject, eInvoiceEmailZipFlag, eInvoiceEmailAttachment, eInvoiceEmailPage, custNo, parentCode, code);
			Messagebox.show("Update E Invoice Email Details.", "E Invoice Email Department", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}catch (WrongValueException wve) {
			throw wve;
		}catch (Exception e) {
			logger.error("Error", e);
			Messagebox.show("Unable to update! Please try again later", "E Invoice Email Department", Messagebox.OK, Messagebox.ERROR);
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

	public List<Listitem> getAttachmentSelectionList() {
		return cloneList(attachmentSelectionList);
	}

	public void setAttachmentSelectionList(List<Listitem> attachmentSelectionList) {
		this.attachmentSelectionList = attachmentSelectionList;
	}

	public List<Listitem> getPageSelectionList() {
		return cloneList(pageSelectionList);
	}

	public void setPageSelectionList(List<Listitem> pageSelectionList) {
		this.pageSelectionList = pageSelectionList;
	}

}
