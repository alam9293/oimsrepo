package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.util.DateUtil;

public class ViewCreditTermWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewCreditTermWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ViewCreditTermWindow() throws InterruptedException{
		logger.info("ViewStatusWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		this.setWidth("75%");
		this.setMinwidth(100);
		this.setSizable(false);
		this.setClosable(true);
	}
	public void init(){
		logger.info("init()");
		Map<String, List<Map<String, Object>>> creditTermDetails = this.businessHelper.getAccountBusiness().getCreditTermHistoricalDetails(custNo);
		// credit term
		List<Map<String, Object>> creditTerms = creditTermDetails.get("creditTerm");
	
		String prevCreditTerm = "-";
		Listbox creditTermListBox = (Listbox)this.getFellow("creditTermList");
		Map<Integer, String> creditTermsMap = MasterSetup.getCreditTermManager().getAllMasters();
		// clearing any previous search
		creditTermListBox.getItems().clear();
		if (creditTerms != null) {
			if(creditTerms.size() > 0)
			{
				for(Map<String, Object> creditTerm : creditTerms) {
					// creating a new row and append it to rows
					Listitem creditTermItem = new Listitem();
					// getting the details
					if(creditTerm.get("creditTerm")!=null){
						prevCreditTerm = creditTermsMap.get(creditTerm.get("creditTerm"));
					}
					creditTermItem.appendChild(newListcell(prevCreditTerm));
				
					String effectiveDate = DateUtil.convertDateToStr((Date)creditTerm.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
					creditTermItem.appendChild(newListcell(effectiveDate));
					
					creditTermListBox.appendChild(creditTermItem);
				}
			}
			else {
				Listitem creditTermItem = new Listitem();
				creditTermItem.appendChild(newListcell("No Record Found"));
				creditTermListBox.appendChild(creditTermItem);
			}
		}
		else {
			Listitem creditTermItem = new Listitem();
			creditTermItem.appendChild(newListcell("No Record Found"));
			creditTermListBox.appendChild(creditTermItem);
		}

		creditTermListBox.setVisible(true);
	}
	public void deleteStatus(Integer statusNo) throws InterruptedException{
		logger.info("deleteStatus(Integer statusNo)");
		if(Messagebox.show("Delete status?", "View Status History", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().deleteStatus(statusNo);
			Messagebox.show("Status Deleted", "View Status History", Messagebox.OK, Messagebox.QUESTION);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
}
