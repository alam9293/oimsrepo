package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.util.DateUtil;

public class ViewTransfersAcctWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewTransfersAcctWindow.class);
	public ViewTransfersAcctWindow(){
		logger.info("ViewTransfersAcctWindow()");
	}
	public void init(){
		logger.info("init()");
		Listbox requestsBox = (Listbox)this.getFellow("requests");
		requestsBox.getItems().clear();
		Map<Integer, Map<String, String>> requests = this.businessHelper.getAccountBusiness().getAllAcctTransferReqs();
		for(Integer requestNo : requests.keySet()){
			Map<String, String> request = requests.get(requestNo);
			Listitem requestItem = new Listitem();
			requestItem.appendChild(newListcell(request.get("fromSalesperson")));
			requestItem.appendChild(newListcell(request.get("toSalesperson")));
			requestItem.appendChild(newListcell(DateUtil.convertStrToTimestamp(request.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
			requestItem.appendChild(newListcell(request.get("count")));
			requestItem.setValue(requestNo);
			requestsBox.appendChild(requestItem);
		}
	}
	public void selectRequest(Listitem selectedItem) throws InterruptedException{
		logger.info("selectRequest(Listitem selectedItem)");
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("requestNo", (Integer)selectedItem.getValue());
		this.forward(Uri.VIEW_TRANSFER_ACCOUNT_REQ, params);
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
}