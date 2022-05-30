package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.util.DateUtil;

public class ViewEarlyPaymentWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewEarlyPaymentWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ViewEarlyPaymentWindow() throws InterruptedException{
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
		Map<String, List<Map<String, Object>>> earlyPaymentMap = this.businessHelper.getAccountBusiness().getEarlyPaymentHistoricalDetails(custNo);
		// early payment
		List<Map<String, Object>> earlyPaymentList = earlyPaymentMap.get("earlyPymt");
		Map<Integer, String> earlyPaymentsMap = MasterSetup.getEarlyPaymentManager().getAllMasters();
		
		Listbox earlyPaymentListBox = (Listbox)this.getFellow("earlyPaymentList");
		// clearing any previous search
		earlyPaymentListBox.getItems().clear();
		// for each result
		
		if (earlyPaymentList != null) {
			if(earlyPaymentList.size() > 0)
			{
				for(Map<String, Object> earlyPayment : earlyPaymentList) {
					// creating a new row and append it to rows
					Listitem earlyPaymentItem = new Listitem();
					// getting the details
					
					if(earlyPayment.get("earlyPymt")!=null){
						earlyPaymentItem.appendChild(newListcell(earlyPaymentsMap.get(earlyPayment.get("earlyPymt"))));
					}else{
						earlyPaymentItem.appendChild(newListcell("-"));
					}
					
					String effectiveDate = DateUtil.convertDateToStr((Date)earlyPayment.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
					earlyPaymentItem.appendChild(newListcell(effectiveDate));
					
					earlyPaymentListBox.appendChild(earlyPaymentItem);
				}
			}
			else {
				Listitem earlyPaymentItem = new Listitem();
				earlyPaymentItem.appendChild(newListcell("No Record Found"));
				earlyPaymentListBox.appendChild(earlyPaymentItem);
			}
		}
		else {
			Listitem earlyPaymentItem = new Listitem();
			earlyPaymentItem.appendChild(newListcell("No Record Found"));
			earlyPaymentListBox.appendChild(earlyPaymentItem);
		}

		earlyPaymentListBox.setVisible(true);
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
}
