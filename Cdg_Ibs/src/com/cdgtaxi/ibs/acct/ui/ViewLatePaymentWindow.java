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

public class ViewLatePaymentWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewLatePaymentWindow.class);
	private String custNo, parentCode, code, acctNo;
	@SuppressWarnings("unchecked")
	public ViewLatePaymentWindow() throws InterruptedException{
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
		Map<String, List<Map<String, Object>>> latePaymentMap = this.businessHelper.getAccountBusiness().getLatePaymentHistoricalDetails(custNo);
		// billing
		List<Map<String, Object>> latePaymentList = latePaymentMap.get("latePymt");
		Map<Integer, String> latePaymentsMap = MasterSetup.getLatePaymentManager().getAllMasters();
		Listbox latePaymentListBox = (Listbox)this.getFellow("latePaymentList");
		// clearing any previous search
		latePaymentListBox.getItems().clear();
		// for each result
		
		if (latePaymentList != null) {
			if(latePaymentList.size() > 0)
			{
				for(Map<String, Object> latePayment : latePaymentList) {
					// creating a new row and append it to rows
					Listitem latePaymentItem = new Listitem();
					// getting the details
					latePaymentItem.appendChild(newListcell(latePaymentsMap.get(latePayment.get("latePymt"))));
					String effectiveDate = DateUtil.convertDateToStr((Date)latePayment.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
					latePaymentItem.appendChild(newListcell(effectiveDate));
					
					latePaymentListBox.appendChild(latePaymentItem);
				}
			}
			else {
				Listitem latePaymentItem = new Listitem();
				latePaymentItem.appendChild(newListcell("No Record Found"));
				latePaymentListBox.appendChild(latePaymentItem);
			}
		}
		else {
			Listitem latePaymentItem = new Listitem();
			latePaymentItem.appendChild(newListcell("No Record Found"));
			latePaymentListBox.appendChild(latePaymentItem);
		}
		latePaymentListBox.setVisible(true);
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
