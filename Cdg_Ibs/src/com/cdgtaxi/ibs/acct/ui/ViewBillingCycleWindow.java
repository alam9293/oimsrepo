package com.cdgtaxi.ibs.acct.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;

public class ViewBillingCycleWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewBillingCycleWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ViewBillingCycleWindow() throws InterruptedException{
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
		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getBillingCycleHistoricalDetails(custNo);
		// billing
		List<Map<String, Object>> billing = billingDetails.get("billing");
		
		Map<Integer, String> volumeDiscounts = MasterSetup.getVolumeDiscountManager().getAllMasters();
		Map<Integer, String> adminFees = MasterSetup.getAdminFeeManager().getAllMasters();
		// previous billing cycle, volume discount and admin fee
		String billingCycle = "-", volumeDiscountName = "-", adminFeeName = "-";
		
		Listbox billingCycleListBox = (Listbox)this.getFellow("billingCycle");
		// clearing any previous search
		billingCycleListBox.getItems().clear();
		// for each result
		
		if (billing != null) {
			if(billing.size() > 0)
			{
				for(Map<String, Object> bill : billing) {
					// creating a new row and append it to rows
					Listitem billingCycleItem = new Listitem();
					// getting the details
					if(bill.get("billingCycle")!=null)
						billingCycle = NonConfigurableConstants.BILLING_CYCLES.get(bill.get("billingCycle"));
				    
					billingCycleItem.appendChild(newListcell(billingCycle));
					
					if(bill.get("volumeDiscount")!=null)
						volumeDiscountName = volumeDiscounts.get(bill.get("volumeDiscount"));
					else
						volumeDiscountName = "-";
					
					billingCycleItem.appendChild(newListcell(volumeDiscountName));
					
					if(bill.get("adminFee")!=null)
						adminFeeName = adminFees.get(bill.get("adminFee"));
					
					billingCycleItem.appendChild(newListcell(adminFeeName));
					
					String effectiveDate = DateUtil.convertDateToStr((Date)bill.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT);
					billingCycleItem.appendChild(newListcell(effectiveDate));
					
					billingCycleListBox.appendChild(billingCycleItem);
				}
			}
			else {
				Listitem billingCycleItem = new Listitem();
				billingCycleItem.appendChild(newListcell("No Record Found"));
				billingCycleListBox.appendChild(billingCycleItem);
			}
		}
		else {
			Listitem billingCycleItem = new Listitem();
			billingCycleItem.appendChild(newListcell("No Record Found"));
			billingCycleListBox.appendChild(billingCycleItem);
		}
		billingCycleListBox.setVisible(true);
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
}
