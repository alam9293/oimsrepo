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

public class ViewPromotionWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewPromotionWindow.class);
	private String custNo, parentCode, code, acctNo;
	@SuppressWarnings("unchecked")
	public ViewPromotionWindow() throws InterruptedException{
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
		Map<String, List<Map<String, Object>>> promotionDetails = this.businessHelper.getAccountBusiness().getPromotionHistoricalDetails(custNo);
		// promotion
		List<Map<String, Object>> promotionList = promotionDetails.get("promotion");
	
		Map<Integer, String> promotionMap = MasterSetup.getPromotionManager().getAllMasters();
		Listbox promotionListBox = (Listbox)this.getFellow("promotionList");
		// clearing any previous search
		promotionListBox.getItems().clear();
		// for each result
		
		if (promotionList != null) {
			if(promotionList.size() > 0)
			{
				for(Map<String, Object> promotion : promotionList) {
					// creating a new row and append it to rows
					Listitem promotionItem = new Listitem();
					// getting the details
					promotionItem.appendChild(newListcell(promotionMap.get(promotion.get("promotion"))));
					
					String effectiveDateFrom = DateUtil.convertDateToStr((Date)promotion.get("effectiveDateFrom"), DateUtil.GLOBAL_DATE_FORMAT);
					promotionItem.appendChild(newListcell(effectiveDateFrom));
					
					String effectiveDateTo = DateUtil.convertDateToStr((Date)promotion.get("effectiveDateTo"), DateUtil.GLOBAL_DATE_FORMAT);
					promotionItem.appendChild(newListcell(effectiveDateTo));
					
					promotionListBox.appendChild(promotionItem);
				}
			}
			else {
				Listitem promotionItem = new Listitem();
				promotionItem.appendChild(newListcell("No Record Found"));
				promotionListBox.appendChild(promotionItem);
			}
		}
		else {
			Listitem promotionItem = new Listitem();
			promotionItem.appendChild(newListcell("No Record Found"));
			promotionListBox.appendChild(promotionItem);
		}
		promotionListBox.setVisible(true);
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
